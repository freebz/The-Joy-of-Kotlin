// 10.9.4 트리에 대해 재귀 사용하기

// 연습문제 10-2

// companion object
operator fun <A: Comparable<A>> invoke(vararg az: A): Tree<A> =
    az.foldRight(Empty, { a: A, tree: Tree<A> -> tree.plus(a) })

// companion object
operator fun <A: Comparable<A>> invoke(az: List<A>): Tree<A> =
    az.foldRight(Empty, { a: A, tree: Tree<A> -> tree.plus(a) })

// companion object
operator fun <A: Comparable<A>> invoke(az: List<A>): Tree<A> =
    az.foldRight(Empty, { a: A -> { tree: Tree<A> -> tree.plus(a) } })

// companion object
operator fun <A: Comparable<A>> invoke(list: List<A>): Tree<A> =
    list.foldLeft(Empty as Tree<A>, { tree: Tree<A> ->
        { a: A ->
            tree.plus(a)
        }
    })



// 연습문제 10-3

fun contains(a: @UnsafeVariance A): Boolean

// Tree
fun contains(a: @UnsafeVariance A): Boolean = when (this) {
    is Empty -> false
    is T -> when {
        a < value -> left.contains(a)
        a > value -> right.contains(a)
        else -> value == a
    }
}

// Tree
fun <A: Comparable<@UnsafeVariance A>> contains1(a: A): Boolean =
    when (this) {
        is Empty -> false
        is T -> a == value || left.contains1(a) || right.contains1(a)
    }

// Tree
fun <B: Comparable<B>> contains1(a: B): Boolean =
    when (this) {
        is Empty -> false
        is T -> a == value || left.contains1(a) || right.contains1(a)
    }

// Tree
fun contains2(a: A): Boolean =
    when (this) {
        is Empty -> false
        is T -> a == value || left.contains2(a) || right.contains2(a)
    }



// 연습문제 10-4

// Tree
abstract fun size(): Int
abstract fun height(): Int

// Empty
override fun size(): Int = 0
override fun height(): Int = -1

// T
import kotlin.math.max
...
override fun size(): Int = 1 + left.size() + right.size()
override fun height(): Int = 1 + max(left.height(), right.height())


// Tree
abstract val size: Int

abstract val height: Int


internal object Empty : Tree<Nothing>() {
    override val size: Int = 0
    override val height: Int = -1
    ...
}
internal class T<out A: ...>(override val left: Tree<A>,
                             override val value: A,
                             override val right: Tree<A>) : Tree<A>() {
    override val size: Int = 1 + left.size + right.size
    override val height: Int = 1 + max(left.height, right.height)
    ...
}



// 연습문제 10-5

// Tree
abstract fun max(): Result<A>
abstract fun min(): Result<A>

// Empty
override fun max(): Result<Nothing> = Result.empty()
override fun min(): Result<Nothing> = Result.empty()

// T
override fun max(): Result<A> = right.max().orElse { Result(value) }
override fun min(): Result<A> = left.min().orElse { Result(value) }