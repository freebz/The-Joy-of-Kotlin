// 5.6 다른 리스트 연산들

// 연습문제 5-3

fun drop(n: Int): List<A>

// List
abstract fun drop(n: Int): List<A>

// Nil
override fun drop(n: Int): List<A> = this

// Cons
override fun drop(n: Int): List<A> = if (n == 0) this else tail.drop(n - 1)


// Cons
override fun drop(n: Int): List<A> {
    tailrec fun drop(n: Int, list: List<A>): List<A> =
        if (n <= 0) list else drop(n - 1, list.tail())
    return drop(n, this)
}


// List
fun drop(n: Int): List<A> {
    tailrec fun drop(n: Int, list: List<A>): List<A> =
        if (n <= 0) list else when (list) {
            is Cons -> drop(n - 1, list.tail)
            is Nil -> list
        }
    return drop(n, this)
}