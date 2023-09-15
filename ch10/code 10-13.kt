// 10.12.2 데이-스타우트-워런 알고리즘 사용하기

// packages
fun log2nlz(n: Int): Int = when (n) {
    0 -> 0
    else -> 31 - Integer.numberOfLeadingZeros(n)
}



// 연습문제 10-14

// companion object
fun <A> unfold(a: A, f: (A) -> Result<A>): A {
    tailrec fun <A> unfold(a: Pair<Result<A>, Result<A>>,
        f: (A) -> Result<A>): Pair<Result<A>, Result<A>> {
            val x = a.second.flatMap { f(it) }
            return x.map { unfold(Pair(a.second, x), f) }.getOrElse(a)
        }
    return Result(a).let { unfold(Pair(it, it), f).second.getOrElse(a) }
}


fun <A> unfold(a: A, f: (A) -> Result<A>): A {
    tailrec fun <A> unfold(a: Pair<Result<A>, Result<A>>,
        f: (A) -> Result<A>): Pair<Result<A>, Result<A>> {
            val x = a.second.flatMap { f(it) }
            return when (x) {
                is Result.Success -> unfold(Pair(a.second, x), f)
                else -> a
            }
    }
    return Result(a).let { unfold(Pair(it, it), f).second.getOrElse(a) }
}


fun <A: Comparable<A>> isUnBalanced(tree: Tree<A>): Boolean =
    when (tree) {
        Empty -> false
        is T -> Math.abs(tree.left.height - tree.right.height) > (tree.size - 1) % 2
    }


// Tree
internal abstract val value: A

internal abstract val left: Tree<A>

internal abstract val right: Tree<A>


// Empty
override val value: Nothing =
    throw IllegalStateException("No value in Empty")

override val left: Tree<Nothing> =
    throw IllegalStateException("No left in Empty")

override val right: Tree<Nothing> =
    throw IllegalStateException("No right in Empty")


// Empty
override val value: Nothing by lazy {
    throw IllegalStateException("No value in Empty")
}

override val left: Tree<Nothing> by lazy {
    throw IllegalStateException("No left in Empty")
}

override val right: Tree<Nothing> by lazy {
    throw IllegalStateException("No right in Empty")
}


// T
internal
class T<out A: Comparable<@UnsafeVariance A>>(override val left: Tree<A>,
                                              override val value: A,
                                              override val right: Tree<A>):
                                                                  Tree<A>() {

}


// companion object
fun <A: Comparable<A>> balance(tree: Tree<A>): Tree<A> =
    balanceHelper(tree.toListInOrderRight().foldLeft(Empty) {
        t: Tree<A> -> { a: A ->
            T(Empty, a, t)
        }
    })

fun <A: Comparable<A>> balanceHelper(tree: Tree<A>): Tree<A> = when {
    !tree.isEmpty() && tree.height > log2nlz(tree.size) -> when {
        Math.abs(tree.left.height - tree.right.height) > 1 ->
            balanceHelper(balanceFirstLevel(tree))
        else -> T(balanceHelper(tree.left),
            tree.value, balanceHelper(tree.right))
    }
    else -> tree
}

private fun <A: Comparable<A>> balanceFirstLevel(tree: Tree<A>): Tree<A> =
    unfold(tree) { t: Tree<A> ->
        when {
            isUnBalanced(t) -> when {
                tree.right.height > tree.left.height ->
                    Result(t.rotateLeft())
                else -> Result(t.rotateRight())
            }
            else -> Result()
        }
    }