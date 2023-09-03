// 8.3 메모화의 이점

// 8.3.1 메모화의 단점 처리하기

// 연습하기 8-1

// List
abstract fun lengthMemoized(): Int

// Nil
override fun lengthMemoized(): Int = 0

// Cons
internal class Cons<out A>(internal val head: A,
                           internal val tail: List<A>): List<A>() {
    private val length: Int = tail.lengthMemoized() + 1
    ...
}

override fun lengthMemoized() = length


// List
abstract val length: Int

// Nil
override val length = 0

// Cons
override val length = tail.length + 1