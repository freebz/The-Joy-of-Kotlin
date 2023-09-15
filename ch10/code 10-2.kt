// 10.9.2 Tree 클래스의 추상 함수로 구현하는 방법은 어떨까

// Empty (error)
override fun plus(element: Nothing): Tree<Nothing> = T(Empty, element, Empty)

// Empty (error)
override operator fun <A: Comparable<A>> plus(a: @UnsafeVariance A): Tree<A> =
    T(Empty, a, Empty)