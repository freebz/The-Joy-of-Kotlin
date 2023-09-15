// 10.9.5 트리에서 원소 제거하기

// 연습문제 10-6

fun remove(a: A): Tree<A>


fun removeMerge(ta: Tree<@UnsafeVariance A>): Tree<A>


fun removeMerge(ta: Tree<@UnsafeVariance A>): Tree<A> = when (this) {
    Empty -> ta
    is T -> when (ta) {
        Empty -> this
        is T -> when {
            ta.value < value -> T(left.removeMerge(ta), value, right)
            else -> T(left, value, right.removeMerge(ta))
        }
    }
}


fun remove(a: @UnsafeVariance A): Tree<A> = when(this) {
    Empty -> this
    is T -> when {
        a < value -> T(left.remove(a), value, right)
        a > value -> T(left, value, right.remove(a))
        else -> left.removeMerge(right)
    }
}