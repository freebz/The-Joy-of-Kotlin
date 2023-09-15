// 10.12 균형 트리 다루기

// 10.12.1 트리 회전시키기

// 연습문제 10-12

protected abstract fun rotateRight(): Tree<A>
protected abstract fun rotateLeft(): Tree<A>


override fun rotateRight(): Tree<A> = when (left) {
    Empty -> this
    is T -> T(left.left, left.value,
                    T(left.right, value, right))
}

override fun rotateLeft(): Tree<A> = when (right) {
    Empty -> this
    is T -> T(T(left, value, right.left),
        right.value, right.right)
}



// 연습문제 10-13

fun toListInOrderRight(): List<A>


override fun toListInOrderRight(): List<A> =
    right.toListInOrderRight()
        .concat(List(value))
        .concat(left.toListInOrderRight())


private tailrec
fun <A: Comparable<A>> unBalanceRight(acc: List<A>, tree: Tree<A>): List<A> =
    when (tree) {
        Empty -> acc
        is T -> when (tree.left) {
            Empty -> unBalanceRight(acc.cons(tree.value), tree.right)
            is T -> unBalanceRight(acc, tree.rotateRight())
        }
    }


fun toListInOrderRight(): List<A> = unBalanceRight(List(), this)