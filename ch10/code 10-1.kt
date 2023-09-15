// 10.9.1 변성과 트리 이해하기

public interface Comparable<in T> {
    public operator fun compareTo(other: T): Int
}


operator fun <A: Comparable<A>> invoke() = Empty as Tree<A>



// 연습문제 10-1

operator fun plus(element: @UnsafeVariance A): Tree<A>


operator fun plus(element: @UnsafeVariance A): Tree<A> = when (this) {
    Empty -> T(Empty, element, Empty)
    is T -> when {
        element < this.value -> T(left + element, this.value, right)
        element > this.value -> T(left, this.value, right + element)
        else -> T(this.left, element, this.right)
    }
}


T(this.left, this.value, this.right)