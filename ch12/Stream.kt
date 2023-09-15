sealed class Stream<out A> {
    abstract fun isEmpty(): Boolean
    abstract fun head(): Result<A>
    abstract fun tail(): Result<Stream<A>>
    abstract fun takeAtMost(n: Int): Stream<A>
    abstract fun takeWhile(p: (A) -> Boolean): Stream<A>
    abstract fun <B> foldRight(z: Lazy<B>,
                               f: (A) -> (Lazy<B>) -> B): B

    private object Empty: Stream<Nothing>() {
        override fun head(): Result<Nothing> = Result()
        override fun tail(): Result<Nothing> = Result()
        override fun isEmpty(): Boolean = true
        override fun takeAtMost(n: Int): Stream<Nothing> = this
        override fun takeWhile(p: (Nothing) -> Boolean): Stream<Nothing> = this
        override fun <B> foldRight(z: Lazy<B>,
                                   f: (Nothing) -> (Lazy<B>) -> B): B = z()
    }

    private class Cons<out A> (internal val hd: Lazy<A>,
                                internal val tl: Lazy<Stream<A>>): Stream<A>() {
        override fun head(): Result<A> = Result(hd())
        override fun tail(): Result<Stream<A>> = Result(tl())
        override fun isEmpty(): Boolean = false
        override fun takeAtMost(n: Int): Stream<A> = when {
            n > 0 -> cons(hd, Lazy { tl().takeAtMost(n - 1) })
            else -> Empty
        }
        override fun takeWhile(p: (A) -> Boolean): Stream<A> = when {
            p(hd()) -> cons(hd, Lazy { tl().takeWhile(p) })
            else -> Empty
        }
        override fun <B> foldRight(z: Lazy<B>,
                                   f: (A) -> (Lazy<B>) -> B): B =
                                       f(hd())(Lazy { tl().foldRight(z, f) })
    }

    companion object {
        fun <A> cons(hd: Lazy<A>, tl: Lazy<Stream<A>>): Stream<A> = Cons(hd, tl)
        operator fun <A> invoke(): Stream<A> = Empty
        fun <A> repeat(f: () -> A): Stream<A> =
            cons(Lazy { f() }, Lazy { repeat(f) })
        fun from(i: Int): Stream<Int> = iterate(i) { it + 1 }
        fun <A> iterate(seed: A, f: (A) -> A): Stream<A> =
            cons(Lazy { seed }, Lazy { iterate(f(seed), f) })
        fun <A> iterate(seed: Lazy<A>, f: (A) -> A): Stream<A> =
            cons(seed, Lazy { iterate(f(seed()), f) })
        tailrec fun <A> dropWhile(stream: Stream<A>, p: (A) -> Boolean): Stream<A> =
            when (stream) {
                is Empty -> stream
                is Cons -> when {
                    p(stream.hd()) -> dropWhile(stream.tl(), p)
                    else -> stream
                }
            }
        tailrec fun <A> exists(stream: Stream<A>, p: (A) -> Boolean): Boolean =
            when (stream) {
                Empty -> false
                is Cons -> when {
                    p(stream.hd()) -> true
                    else -> exists(stream.tl(), p)
                }
            }
        fun <A, S> unfold(z: S, f: (S) -> Result<Pair<A, S>>): Stream<A> = f(z).map {
            (a, s) -> Stream.cons(Lazy { a }, Lazy { unfold(s, f) })
        }.getOrElse(Stream.Empty)
        fun <A> fill(n: Int, elem: Lazy<A>): Stream<A> {
            tailrec
            fun <A> fill(acc: Stream<A>, n: Int, elem: Lazy<A>): Stream<A> =
                when {
                    n <= 0 -> acc
                    else -> fill(Cons(elem, Lazy { acc }), n - 1, elem)
                }
            return fill(Empty, n, elem)
        }
    }

    tailrec fun <A> dropAtMost(n: Int, stream: Stream<A>): Stream<A> = when {
        n > 0 -> when (stream) {
            is Empty -> stream
            is Cons -> dropAtMost(n - 1, stream.tl())
        }
        else -> stream
    }

    fun dropAtMost(n: Int): Stream<A> = dropAtMost(n, this)

    fun <A> toList(stream: Stream<A>) : List<A> {
        tailrec
        fun <A> toList(list: List<A>, stream: Stream<A>) : List<A> =
            when (stream) {
                Empty -> list
                is Cons -> toList(list.cons(stream.hd()), stream.tl())
            }
        return toList(List(), stream).reverse()
    }

    fun toList(): List<A> = toList(this)

    fun dropWhile(p: (A) -> Boolean): Stream<A> = dropWhile(this, p)

    fun exists(p: (A) -> Boolean): Boolean = exists(this, p)

    fun takeWhildViaFoldRight(p: (A) -> Boolean): Stream<A> =
        foldRight(Lazy { Empty }, { a ->
            { b: Lazy<Stream<A>> -> 
                if (p(a))
                    cons(Lazy { a }, b)
                else
                    Empty
            }
        })

    fun headSafeViaFoldRight(): Result<A> =
        foldRight(Lazy { Result<A>() }, { a -> { Result(a) } })

    fun <B> map(f: (A) -> B): Stream<B> =
        foldRight(Lazy { Empty }, { a ->
            { b: Lazy<Stream<B>> ->
                cons(Lazy { f(a) }, b)
            }
        })

    fun append(stream2: Lazy<Stream<@UnsafeVariance A>>): Stream<A> =
        this.foldRight(stream2) { a: A ->
            { b: Lazy<Stream<A>> ->
                Stream.cons(Lazy { a }, b)
            }
        }

    fun <B> flatMap(f: (A) -> Stream<B>): Stream<B> =
        foldRight(Lazy { Empty as Stream<B> }, { a ->
            { b: Lazy<Stream<B>> ->
                f(a).append(b)
            }
        })

    fun find(p: (A) -> Boolean): Result<A> = filter(p).head()

    fun from(n: Int): Stream<Int> = unfold(n) { x -> Result(Pair(x, x + 1)) }

    fun filter(p: (A) -> Boolean): Stream<A> =
        dropWhile { x -> !p(x) }.let { stream ->
            when (stream) {
                is Empty -> stream
                is Cons -> cons(stream.hd, Lazy { stream.tl().filter(p) })
            }
        }
}