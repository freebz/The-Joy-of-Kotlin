// 8.4 List와 Result의 합성

// 8.4.1 Result를 반환하는 리스트 처리하기

// 연습문제 8-2

// List
abstract fun headSafe(): Result<A>

// Nil
override fun headSafe(): Result<Nothing> = Result()

// Cons
override fun headSafe(): Result<A> = Result(head)



// 연습문제 8-3

// List
fun lastSafe(): Result<A> = when (this) {
    Nil -> Result()
    is Cons -> when (tail) {
        Nil -> Result(head)
        is Cons -> tail.lastSafe()
    }
}

// List
tailrec fun <A> lastSafe(list: List<A>): Result<A> = when (list) {
    List.Nil -> Result()
    is List.Cons<A> -> when (list.tail) {
        List.Nil -> Result(list.head)
        is List.Cons -> lastSafe(list.tail)
    }
}


{ _: Result<A> -> { y: A -> Result(y) } }

// List
fun lastSafe(): Result<A> =
    foldLeft(Result()) { _: Result<A> -> { y: A -> Result(y) } }



// 연습문제 8-4

// List
fun headSafe(): Result<A> =
    foldRight(Result()) { x: A -> { _: Result<A> -> Result(x) } }

// List
fun headSafe(): Result<A> = when (this) {
    Nil -> Result()
    is Cons -> Result(head)
}