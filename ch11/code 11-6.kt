// 11.3.6 큐와 유사한 인터페이스 구현하기

// 연습문제 11-6

// Heap
abstract fun tail(): Result<Heap<A>>

// Empty
override fun tail(): Result<Heap<A>> =
    Result.failure(IllegalStateException("tail() called on empty heap"))

// H
override fun tail(): Result<Heap<A>> = Result(merge(lft, rght))



// 연습문제 11-7

// Heap
fun get(index: Int): Result<A>

// Empty
override fun get(index: Int): Result<A> =
    Result.failure(NoSuchElementException("Index out of bounds"))

// H
override fun get(index: Int): Result<A> = when (index) {
    0 -> Result(hd)
    else -> tail().flatMap { it.get(index - 1) }
}