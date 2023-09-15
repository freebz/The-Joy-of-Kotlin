// 11.1.2 레드-블랙 트리에 원소 추가하기

// 연습문제 11-1

operator fun plus(value: @UnsafeVariance A): Tree<A>
fun balance(color: Color, left: Tree<A>, value: A, right: Tree<A>): Tree<A>
fun <A: Comparable<A>> blacken(): Tree<A>


// Tree
protected fun balance(color: Color,
                      left: Tree<@UnsafeVariance A>,
                      value: @UnsafeVariance A,
                      right: Tree<@UnsafeVariance A>): Tree<A> = when {
    // B (T R (T R a x b) y c) z d = T R (T B a x b) y (T B c z d) 균형 잡기
    color == B && left.isTR && left.left.isTR ->
        T(R, left.left.blacken(), left.value, T(B, left.right, value, right))
    // B (T R a x (T R b y c)) z d = T R (T B a x b) y (T B c z d) 균형 잡기
    color == B && left.isTR && left.right.isTR ->
        T(R, T(B, left.left, left.value, left.right.left), left.right.value,
            T(B, left.right.right, value, right))
    // B a x (T R (T R b y c) z d) = T R (T B z x b) y (T B c z d) 균형 잡기
    color == B && right.isTR && right.left.isTR ->
        T(R, T(B, left, value, right.left.left), right.left.value,
            T(B, right.left.right, right.value, right.right))
    // B a x (T R b y (T R c z d)) = T R (T B a x b) y (T B c z d) 균형 잡기
    color == B && right.isTR && right.right.isTR ->
        T(R, T(B, left, value, right.left), right.value, right.right.blacken())
    // color a x b = T color a x b 균형 잡기
    else -> T(color, left, value, right)
}


// Empty
override fun add(newVal: @UnsafeVariance A): Tree<A> = T(R, E, newVal, E)

// T
override fun add(newVal: @UnsafeVariance A): Tree<A> = when {
    newVal < value -> balance(color, left.add(newVal), value, right)
    newVal > value -> balance(color, left, value, right.add(newVal))
    else           -> when (color) {
        B -> T(B, left, newVal, right)
        R -> T(R, left, newVal, right)
    }
}


// Empty
override fun blacken(): Tree<A> = E

// T
override fun blacken(): Tree<A> = T(B, left, value, right)

// Tree
operator fun plus(value: @UnsafeVariance A): Tree<A> = add(value).blacken()