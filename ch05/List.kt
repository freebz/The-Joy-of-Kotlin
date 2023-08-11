sealed class List<out A> {
    abstract fun isEmpty(): Boolean
    abstract fun setHead(a: @UnsafeVariance A): List<A>

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
    
    private object Nil : List<Nothing>() {
        override fun isEmpty() = true
        override fun toString(): String = "[NIL]"
        override fun setHead(a: Nothing): List<Nothing> =
            throw IllegalStateException("setHead called on an empty list")
    }

    private class Cons<A>(internal val head: A,
                          internal val tail: List<A>) : List<A>() {
        override fun isEmpty() = false
        override fun toString(): String = "[${toString("", this)}NIL]"
        private tailrec fun toString(acc: String, list: List<A>): String =
            when (list) {
                is Nil -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }
        override fun setHead(a: A): List<A> = tail.cons(a)
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