// 10.12.3 자동 균형 트리

// 연습문제 10-15

operator fun plus(a: @UnsafeVariance A): Tree<A> = balance(plusUnBalanced(a))

private fun plusUnBalanced(a: @UnsafeVariance A): Tree<A> = plus(this, a)


operator fun plus(a: @UnsafeVariance A): Tree<A> {
    fun plusUnBalanced(a: @UnsafeVariance A, t: Tree<A>): Tree<A> =
        when (t) {
            Empty -> T(Empty, a, Empty)
            is T -> when {
                a < t.value -> T(plusUnBalanced(a, t.left), t.value, t.right)
                a > t.value -> T(t.left, t.value, plusUnBalanced(a, t.right))
                else -> T(t.left, a, t.right)
            }
        }
    
    return plusUnBalanced(a, this).let {
        when {
            it.height > log2nlz(it.size) * 100 -> balance(it)
            else -> it
        }
    }
}