// 9.6 스트림 처리하기

// 연습문제 9-11

fun <A> repeat(f: () -> A): Stream<A> =
    cons(Lazy { f() }, Lazy { repeat(f) })



// 연습문제 9-12

// Stream
fun takeAtMost(n: Int): Stream<A>

// Empty
override fun takeAtMost(n: Int): Stream<Nothing> = this

// Cons
override fun takeAtMost(n: Int): Stream<A> = when {
    n > 0 -> cons(hd, Lazy { tl().takeAtMost(n - 1) })
    else -> Empty
}



// 연습문제 9-13

// Stream
fun dropAtMost(n: Int): Stream<A>

// Empty
override fun dropAtMost(n: Int): Stream<Nothing> = this

// Cons
override fun dropAtMost(n: Int): Stream<A> = when {
    n > 0 -> tl().dropAtMost(n - 1)
    else -> this
}



// 연습문제 9-14

fun main(args: Array<String>) {
    val stream = Stream.repeat(::random).dropAtMost(60000).takeAtMost(60000)
    stream.head().forEach(::println)
}

val rnd = Random()

fun random(): Int {
    val rnd = rnd.nextInt()
    println("evaluating $rnd")
    return rnd
}


// StackOverflowException


// Stream
tailrec fun <A> dropAtMost(n: Int, stream: Stream<A>): Stream<A> = when {
    n > 0 -> when (stream) {
        is Empty -> stream
        is Cons -> dropAtMost(n - 1, stream.tl())
    }
    else -> stream
}

// Stream
fun dropAtMost(n: Int): Stream<A> = dropAtMost(n, this)



// 연습문제 9-15

fun main(args: Array<String>) {
    val stream = Stream.from(0).dropAtMost(60000).takeAtMost(60000)
    println(stream.toList())
}


// Stream
fun <A> toList(stream: Stream<A>) : List<A> {
    tailrec
    fun <A> toList(list: List<A>, stream: Stream<A>) : List<A> =
        when (stream) {
            Empty -> list
            is Cons -> toList(list.cons(stream.hd()), stream.tl())
        }
    return toList(List(), stream).reverse()
}

// Stream
fun toList(): List<A> = toList(this)



// 연습문제 9-16

// companion object
fun <A> iterate(seed: A, f: (A) -> A): Stream<A> =
    cons(Lazy { seed }, Lazy { iterate(f(seed), f) })

// companion object
fun from(i: Int): Stream<Int> = iterate(i) { it + 1 }


fun inc(i: Int): Int = (i + 1).let {
    println("generating $it")
    it
}


fun main(args: Array<String>) {
    fun inc(i: Int): Int = (i + 1).let {
        println("generating $it")
        it
    }

    val list = Stream.iterate(0, ::inc)
                     .takeAtMost(60000)
                     .dropAtMost(10000)
                     .takeAtMost(10)
                     .toList()
    println(list)
}


fun <A> iterate(seed: Lazy<A>, f: (A) -> A): Stream<A> =
    cons(seed, Lazy { iterate(f(seed()), f) })



// 연습문제 9-17

// Stream
abstract fun takeWhile(p: (A) -> Boolean): Stream<A>

// Cons
override fun takeWhile(p: (A) -> Boolean): Stream<A> = when {
    p(hd()) -> cons(hd, Lazy { tl().takeWhile(p) })
    else -> Empty
}

// Empty
override fun takeWhile(p: (Nothing) -> Boolean): Stream<Nothing> = this



// 연습문제 9-18

fun dropWhile(p: (A) -> Boolean): Stream<A>

// companion object
tailrec fun <A> dropWhile(stream: Stream<A>, p: (A) -> Boolean): Stream<A> =
    when (stream) {
        is Empty -> stream
        is Cons -> when {
            p(stream.hd()) -> dropWhile(stream.tl(), p)
            else -> stream
        }
    }

// Stream
fun dropWhile(p: (A) -> Boolean): Stream<A> = dropWhile(this, p)



// 연습문제 9-19

// List
fun exists(p(A) -> Boolean): Boolean =
    when (this) {
        Nil -> false
        is Cons -> p(head) || tail.exists(p)
    }

// Stream
fun exists(p: (A) -> Boolean): Boolean = p(hd()) || tl().exists(p)

// companion object
tailrec fun <A> exists(stream: Stream<A>, p: (A) -> Boolean): Boolean =
    when (stream) {
        Empty -> false
        is Cons -> when {
            p(stream.hd()) -> true
            else -> exists(stream.tl(), p)
        }
    }

// Stream
fun exists(p: (A) -> Boolean): Boolean = exists(this, p)