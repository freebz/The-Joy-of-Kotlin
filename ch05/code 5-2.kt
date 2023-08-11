// 5.5 리스트 연산에서 데이터 공유하기

// 연습문제 5-1

fun cons(a: A): List<A> = Cons(a, this)



// 연습문제 5-2

fun setHead(a: A): List<A> = when (this) {
    Nil -> throw IllegalStateException("setHead called on an empty list")
    is Cons -> tail.cons(a)
}



sealed class List<A> {
    abstract fun setHead(a: A): List<A>

    private object Nil: List<Nothing>() {
        override fun setHead(a: Nothing): List<Nothing> =
            throw IllegalStateException("setHead called on an empty list")
        ...
    }

    private class Cons<A>(internal val head: A,
                          internal val tail: List<A>) : List<A>() {
        override fun setHead(a: A): List<A> = tail.cons(a)
        ...
    }
}