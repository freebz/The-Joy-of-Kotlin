import java.util.concurrent.ExecutorService
import java.util.concurrent.ExecutionException

sealed class List<out A> {
    abstract val length: Int
    abstract fun isEmpty(): Boolean
    abstract fun setHead(a: @UnsafeVariance A): List<A>
    abstract fun <B> foldLeft(acc: B, p: (B) -> Boolean, f: (B) -> (A) -> B): B
    abstract fun <B> foldLeft(identity: B, zero: B,
                              f: (B) -> (A) -> B): Pair<B, List<A>>

    fun cons(a: @UnsafeVariance A): List<A> = Cons(a, this)
    fun drop(n: Int): List<A> = drop(this, n)
    fun dropWhile(p: (A) -> Boolean): List<A> = dropWhile(this, p)
    fun concat(list: List<@UnsafeVariance A>): List<A> = concat(this, list)
    fun init(): List<A> = reverse().drop(1).reverse()
    fun <B> foldRight(identity: B, f: (A) -> (B) -> B): B =
        foldRight(this, identity, f)
    fun <B> foldLeft(identity: B, f: (B) -> (A) -> B): B =
        foldLeft(identity, this, f)
    fun length(): Int = foldLeft(0) { { _ -> it + 1 } }
    fun reverse(): List<A> = foldLeft(List.invoke()) { acc -> { acc.cons(it) } }
    fun <B> foldRightViaFoldLeft(identity: B, f: (A) -> (B) -> B): B =
        this.reverse().foldLeft(identity) { acc -> { y -> f(y)(acc) } }
    fun <B> coFoldRight(identity: B, f: (A) -> (B) -> B): B =
        coFoldRight(identity, this.reverse(), identity, f)
    fun <A> concatViaFoldRight(list1: List<A>, list2: List<A>): List<A> =
        foldRight(list1, list2) { x -> { acc -> Cons(x, acc) } }
    fun <A> concatViaFoldLeft(list1: List<A>, list2: List<A>): List<A> =
        list1.reverse().foldLeft(list2) { acc -> acc::cons }
    fun <B> map(f: (A) -> B): List<B> = coFoldRight(Nil) { h -> { acc: List<B> -> Cons(f(h), acc) } }
    fun <B> flatMap(f: (A) -> List<B>): List<B> = flatten(map(f))
    fun filter(p: (A) -> Boolean): List<A> = flatMap { a -> if (p(a)) List(a) else Nil }
    fun lastSafe(): Result<A> =
        foldLeft(Result()) { _: Result<A> -> { y: A -> Result(y) } }
    fun headSafe(): Result<A> = when (this) {
        Nil -> Result()
        is Cons -> Result(head)
    }
    fun <A1, A2> unzip(f: (A) -> Pair<A1, A2>): Pair<List<A1>, List<A2>> =
        this.coFoldRight(Pair(Nil, Nil)) { a ->
            { listPair: Pair<List<A1>, List<A2>> ->
                f(a).let {
                    Pair(listPair.first.cons(it.first),
                        listPair.second.cons(it.second))
                }
            }
        }
    fun getAtViaFoldLeft(index: Int): Result<A> =
        Pair(Result.failure<A>("Index out of bound"), index).let {
            if (index < 0 || index >= length())
                it
            else
                foldLeft(it) { ta ->
                    { a ->
                        if (ta.second < 0)
                            ta
                        else
                            Pair(Result(a), ta.second - 1)
                    }
                }
        }.first
    fun getAt(index: Int): Result<A> {
        val p: (Pair<Result<A>, Int>) -> Boolean = { it.second < 0 }
        return Pair<Result<A>, Int>(Result.failure("Index out of bound"), index)
            .let { identity ->
                if (index < 0 || index >= length())
                    identity
                else
                    foldLeft(identity, p) { ta: Pair<Result<A>, Int> ->
                        { a: A ->
                            if (p(ta))
                                ta
                            else
                                Pair(Result(a), ta.second - 1)
                        }
                    }
            }.first
    }
    fun splitAt(index: Int): Pair<List<A>, List<A>> {
        data class Pair<out A>(val first: List<A>, val second: Int) {
            override fun equals(other: Any?): Boolean = when {
                other == null -> false
                other.javaClass == this.javaClass ->
                    (other as Pair<A>).second == second
                else -> false
            }
            override fun hashCode(): Int =
                first.hashCode() + second.hashCode()
        }
        return when {
            index <= 0 -> Pair(Nil, this)
            index >= length -> Pair(this, Nil)
            else -> {
                val identity = Pair(Nil as List<A>, -1)
                val zero = Pair(this, index)
                val (pair, list) = this.foldLeft(identity, zero) { acc ->
                    { e -> Pair(acc.first.cons(e), acc.second + 1) }
                }
                Pair(pair.first.reverse(), list)
            }
        }
    }
    fun startsWith(sub: List<@UnsafeVariance A>): Boolean {
        tailrec fun startsWith(list: List<A>, sub: List<A>): Boolean =
            when (sub) {
                Nil -> true
                is Cons -> when (list) {
                    Nil -> false
                    is Cons ->
                        if (list.head == sub.head)
                            startsWith(list.tail, sub.tail)
                        else
                            false
                }
            }
        return startsWith(this, sub)
    }
    fun hasSubList(sub: List<@UnsafeVariance A>): Boolean {
        tailrec
        fun <A> hasSubList(list: List<A>, sub: List<A>): Boolean =
            when (list) {
                Nil -> sub.isEmpty()
                is Cons ->
                    if (list.startsWith(sub))
                        true
                    else
                        hasSubList(list.tail, sub)
            }
        return hasSubList(this, sub)
    }
    fun <B> groupBy(f: (A) -> B): Map<B, List<A>> =
        reverse().foldLeft(mapOf()) { mt: Map<B, List<A>> ->
            { t ->
                f(t).let {
                    mt + (it to (mt.getOrDefault(it, Nil)).cons(t))
                }
            }
        }
    fun exists(p: (A) -> Boolean): Boolean =
        foldLeft(false, true) { x -> { y: A -> x || p(y) } }.first
    fun forAll(p: (A) -> Boolean): Boolean = !exists { !p(it) }
    fun splitListAt(index: Int): List<List<A>> {
        tailrec fun splitListAt(acc: List<A>, list: List<A>, i: Int): List<List<A>> =
            when (list) {
                Nil -> List(list.reverse(), acc)
                is Cons ->
                    if (i == 0)
                        List(list.reverse(), acc)
                    else
                        splitListAt(acc.cons(list.head), list.tail, i - 1)
            }
        return when {
            index < 0        -> splitListAt(0)
            index > length() -> splitListAt(length())
            else             ->
                       splitListAt(Nil, this.reverse(), this.length() - index)
        }
    }
    fun divide(depth: Int): List<List<A>> {
        tailrec
        fun divide(list: List<List<A>>, depth: Int): List<List<A>> =
            when (list) {
                Nil -> list // dead code
                is Cons ->
                    if (list.head.length() < 2 || depth < 1)
                        list
                    else
                        divide(list.flatMap { x ->
                            x.splitListAt(x.length() / 2)
                        }, depth - 1)
            }
        return if (this.isEmpty())
            List(this)
        else
            divide(List(this), depth)
    }
    fun <B> parFoldLeft(es: ExecutorService,
                        identity: B,
                        f: (B) -> (A) -> B,
                        m: (B) -> (B) -> B): Result<B> =
        try {
            val result: List<B> = divide(1024).map { list: List<A> ->
                es.submit<B> { list.foldLeft(identity, f) }
            }.map<B> { fb ->
                try {
                    fb.get()
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                } catch (e: ExecutionException) {
                    throw RuntimeException(e)
                }
            }
            Result(result.foldLeft(identity, m))
        } catch (e: Exception) {
            Result.failure(e)
        }
    fun <B> parMap(es: ExecutorService, g: (A) -> B): Result<List<B>> =
        try {
            val result = this.map { x ->
                es.submit<B> { g(x) }
            }.map<B> { fb ->
                try {
                    fb.get()
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                } catch (e: ExecutionException) {
                    throw RuntimeException(e)
                }
            }
            Result(result)
        } catch (e: Exception) {
            Result.failure(e)
        }

    internal object Nil : List<Nothing>() {
        override val length = 0
        override fun isEmpty() = true
        override fun toString(): String = "[NIL]"
        override fun setHead(a: Nothing): List<Nothing> =
            throw IllegalStateException("setHead called on an empty list")
        override fun <B> foldLeft(identity: B,
                                  p: (B) -> Boolean,
                                  f: (B) -> (Nothing) -> B): B = identity
        override
        fun <B> foldLeft(identity: B, zero: B, f: (B) -> (Nothing) -> B):
            Pair<B, List<Nothing>> = Pair(identity, Nil)
    }

    internal class Cons<A>(internal val head: A,
                          internal val tail: List<A>) : List<A>() {
        override val length = tail.length + 1
        override fun isEmpty() = false
        override fun toString(): String = "[${toString("", this)}NIL]"
        private tailrec fun toString(acc: String, list: List<A>): String =
            when (list) {
                is Nil -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }
        override fun setHead(a: A): List<A> = tail.cons(a)
        override fun <B> foldLeft(identity: B,
                                  p: (B) -> Boolean,
                                  f: (B) -> (A) -> B): B {
            fun foldLeft(acc: B,
                         p: (B) -> Boolean,
                         list: List<A>): B = when (list) {
                Nil -> acc
                is Cons ->
                    if (p(acc))
                        acc
                    else
                        foldLeft(f(acc)(list.head), p, list.tail)
            }
            return foldLeft(identity, p, this)
        }
        override fun <B> foldLeft(identity: B, zero: B, f: (B) -> (A) -> B):
            Pair<B, List<A>> {
                fun <B> foldLeft(acc: B, zero: B, list: List<A>, f: (B) -> (A) -> B):
                    Pair<B, List<A>> =
                        when (list) {
                            Nil -> Pair(acc, list)
                            is Cons ->
                                if (acc == zero)
                                    Pair(acc, list)
                                else
                                    foldLeft(f(acc)(list.head), zero, list.tail, f)
                        }
            return foldLeft(identity, zero, this, f)        
        }
    }

    companion object {
        operator
        fun <A> invoke(vararg az: A): List<A> = //
            az.foldRight(Nil as List<A>) { a: A, list: List<A> ->
                Cons(a, list)
            }
        
        tailrec fun <A> drop(list: List<A>, n: Int): List<A> = when (list) {
            Nil -> list
            is Cons -> if (n <= 0) list else drop(list.tail, n - 1)
        }

        private
        tailrec fun <A> dropWhile(list: List<A>, p: (A) -> Boolean): List<A> = when (list) {
            Nil -> list
            is Cons -> if (p(list.head)) dropWhile(list.tail, p) else list
        }

        fun <A> concat(list1: List<A>, list2: List<A>): List<A> = when (list1) {
            Nil -> list2
            is Cons -> concat(list1.tail, list2).cons(list1.head)
        }

        fun <A, B> foldRight(list: List<A>,
                             identity: B,
                             f: (A) -> (B) -> B): B =
            when (list) {
                List.Nil -> identity
                is List.Cons -> f(list.head)(foldRight(list.tail, identity, f))
            }

        tailrec fun <A, B> foldLeft(acc: B, list: List<A>, f: (B) -> (A) -> B): B =
            when (list) {
                List.Nil -> acc
                is List.Cons -> foldLeft(f(acc)(list.head), list.tail, f)
            }

        private tailrec fun <A, B> coFoldRight(acc: B,
                                               list: List<A>,
                                               identity: B,
                                               f: (A) -> (B) -> B): B =
            when (list) {
                List.Nil -> acc
                is List.Cons ->
                coFoldRight(f(list.head)(acc), list.tail, identity, f)
            }

        fun <A> flatten(list: List<List<A>>): List<A> = list.coFoldRight(Nil) { x -> x::concat }
    }
}


// packages

fun sum(list: List<Int>): Int = list.foldLeft(0, { acc -> { y -> acc + y } })

fun product(list: List<Double>): Double = list.foldLeft(1.0, { acc -> { y -> acc * y } })

fun <A> flattenResult(list: List<Result<A>>): List<A> =
    list.flatMap { ra -> ra.map { List(it) }.getOrElse(List()) }

fun <A, B> traverse(list: List<A>, f: (A) -> Result<B>): Result<List<B>> =
    list.foldRight(Result(List())) { x ->
        { y: Result<List<B>> ->
            map2(f(x), y) { a -> { b: List<B> -> b.cons(a) } }
        }
    }

fun <A> sequence(list: List<Result<A>>): Result<List<A>> =
    traverse(list, { x: Result<A> -> x })

fun <A> sequence2(list: List<Result<A>>): Result<List<A>> =
    traverse(list.filter{ !it.isEmpty() }, { x: Result<A> -> x })

fun <A, B, C> zipWith(list1: List<A>,
                      list2: List<B>,
                      f: (A) -> (B) -> C): List<C> {
    tailrec
    fun zipWith(acc: List<C>,
                list1: List<A>,
                list2: List<B>): List<C> = when (list1) {
        List.Nil -> acc
        is List.Cons -> when (list2) {
            List.Nil -> acc
            is List.Cons ->
                zipWith(acc.cons(f(list1.head)(list2.head)), list1.tail, list2.tail)
        }
    }
    return zipWith(List(), list1, list2).reverse()
}

fun <A, B, C> product(list1: List<A>,
                      list2: List<B>,
                      f: (A) -> (B) -> C): List<C> =
    list1.flatMap { a -> list2.map { b -> f(a)(b) } }

fun <A, B> unzip(list: List<Pair<A, B>>): Pair<List<A>, List<B>> = list.unzip { it }

fun <A, S> unfold(z: S, getNext: (S) -> Option<Pair<A, S>>): List<A> {
    tailrec fun unfold(acc: List<A>, z: S): List<A> {
        val next = getNext(z)
        return when (next) {
            Option.None -> acc
            is Option.Some -> unfold(acc.cons(next.value.first), next.value.second)
        }
    }
    return unfold(List.Nil,  z).reverse()
}

fun range(start: Int, end: Int): List<Int> {
    return unfold(start) { i ->
        if (i < end)
            Option(Pair(i, i + 1))
        else
            Option()
    }
}