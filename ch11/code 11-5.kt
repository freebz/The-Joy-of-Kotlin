// 11.3.5 레프티스트 힙 구현하기

// 연습문제 11-5

operator fun plus(element: @UnsafeVariance A): Heap<A>


operator fun <A: Comparable<A>> invoke(element: A): Heap<A>

fun <A: Comparable<A>> merge(first: Heap<A>, second: Heap<A>): Heap<A>


// companion object
operator fun <A: Comparable<A>> invoke(element: A): Heap<A> = H(1, E, element, E)

// companion object
protected
fun <A: Comparable<A>> merge(head: A,
                             first: Heap<A>,
                             second: Heap<A>): Heap<A> =
    when {
        first.rank >= second.rank -> H(second.rank + 1, first, head, second)
        else -> H(first.rank + 1, second, head, first)
    }

// companion object
fun <A: Comparable<A>> merge(first: Heap<A>, second: Heap<A>): Heap<A> =
    first.head.flatMap { fh ->
        second.head.flatMap { sh ->
            when {
                fh <= sh -> first.left.flatMap { fl ->
                    first.right.map { fr ->
                        merge(fh, fl, merge(fr, second))
                    }
                }
                else -> second.left.flatMap { sl ->
                    second.right.map { sr ->
                        merge(sh, sl, merge(first, sr))
                    }
                }
            }
        }
    }.getOrElse(when (first) {
        E -> second
        else -> first
    })

// Heap
operator fun plus(element: @UnsafeVariance A): Heap<A> = merge(this, Heap(element))