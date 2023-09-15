// 11.5 비교 불가능한 원소에 대한 우선순위 큐

// 연습문제 11-10

sealed class Heap<out A: Comparable<@UnsafeVariance A>>
// ->
sealed class Heap<out A>


// Heap
internal class Empty<out A>(
    override val comparator: Result<Comparator<@UnsafeVariance A>> =
        Result.Empty): Heap<A>() {

// Heap
internal class H<out A>(override val rank: Int,
                        internal val lft: Heap<A>,
                        internal val hd: A,
                        internal val rght: Heap<A>,
                        override val comparator: Result<Comparator<@UnsafeVariance A>> =
                            lft.comparator.orElse { rght.comparator }): Heap<A>() {


// companion object
operator fun <A: Comparable<A>> invoke(): Heap<A> =
    Empty()

operator fun <A> invoke(comparator: Comparator<A>): Heap<A> =
    Empty(Result(comparator))

operator fun <A> invoke(comparator: Result<Comparator<A>>): Heap<A> =
    Empty(comparator)

operator fun <A> invoke(element: A, comparator: Result<Comparator<A>>): Heap<A> =
    H(1, Empty(comparator), element, Empty(comparator), comparator)


operator fun <A: Comparable<A>> invoke(element: A): Heap<A> =
    invoke(element, Comparator { o1: A, o2: A -> o1.compareTo(o2) })

operator fun <A> invoke(element: A, comparator: Comparator<A>): Heap<A> =
    H(1, Empty(Result(comparator)), element,
        Empty(Result(comparator)), Result(comparator))


protected fun <A> merge(head: A, first: Heap<A>, second: Heap<A>): Heap<A> =
    first.comparator.orElse { second.comparator }.let {
        when {
            first.rank >= second.rank ->
                H(second.rank + 1, first, head, second, it)
            else -> H(first.rank + 1, second, head, first, it)
        }
    }


fun <A> merge(first: Heap<A>, second: Heap<A>, comparator: Result<Comparator<A>> =
    first.comparator.orElse { second.comparator }): Heap<A> =
        first.head.flatMap { fh ->
            second.head.flatMap { sh ->
                when {
                    compare(fh, sh, comparator) <= 0 ->
                        first.left.flatMap { fl ->
                            first.right.map { fr ->
                                merge(fh, fl, merge(fr, second, comparator))
                            }
                        }
                        else -> second.left.flatMap { sl ->
                            second.right.map { sr ->
                                merge(sh, sl, merge(first, sr, comparator))
                            }
                        }
                }
            }
        }.getOrElse(when (first) {
            is Empty -> second
            else -> first
        })


private fun <A> compare(first: A, second: A,
    comparator: Result<Comparator<A>>): Int =
        comparator.map { comp ->
            comp.compare(first, second)
        }.getOrElse { (first as Comparable<A>).compareTo(second) }


operator fun plus(element: @UnsafeVariance A): Heap<A> =
    merge(this, Heap(element, comparator))


// Empty
override val left: Result<Heap<A>> = Result(this)
override val right: Result<Heap<A>> = Result(this)