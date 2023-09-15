// 예제 11-3 레프티스트 힙 구조

sealed class Heap<out A: Comparable<@UnsafeVariance A>> {
    internal abstract val left: Result<Heap<A>>
    internal abstract val right: Result<Heap<A>>
    internal abstract val head: Result<A>

    protected abstract val rank: Int

    abstract val size: Int
    abstract val isEmpty: Boolean

    abstract class Empty<out A: Comparable<@UnsafeVariance A>>: Heap<A>() {
        override val isEmpty: Boolean = true
        override val left: Result<Heap<A>> = Result(E)
        override val right: Result<Heap<A>> = Result(E)
        override val head: Result<A> = Result.failure("head() called on empty heap")
        override val rank: Int = 0
        override val size: Int = 0
    }

    internal object E: Empty<Nothing>()

    internal class H<out A: Comparable<@UnsafeVariance A>>(
        override val rank: Int,
        private val lft: Heap<A>,
        private val hd: A,
        private val rght: Heap<A>): Heap<A>() {
            override val isEmpty: Boolean = false
            override val left: Result<Heap<A>> = Result(lft)
            override val right: Result<Heap<A>> = Result(rght)
            override val head: Result<A> = Result(hd)
            override val size: Int = lft.size + rght.size + 1
        }
    
    companion object {
        operator fun <A: Comparable<A>> invoke(): Heap<A> = E
    }
}