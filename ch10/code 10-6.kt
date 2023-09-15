// 10.9.6 임의의 트리 합병하기

// 연습문제 10-7(어려움)

// Tree
abstract fun merge(tree: Tree<@UnsafeVariance A>): Tree<A>

// Empty
override fun merge(tree: Tree<Nothing>): Tree<Nothing> = tree

// T
override fun merge(tree: Tree<@UnsafeVariance A>): Tree<A> = when (tree) {
    Empty -> this
    is T -> when {
        tree.value > this.value ->
            T(left, value, right.merge(T(Empty, tree.value, tree.right)))
        tree.value < this.value ->
            T(left.merge(T(tree, tree.value, Empty)), value, right)
                .merge(tree.right)
        else ->
            T(left.merge(tree.left), value, right.merge(tree.right))
    }
}


T(left.merge(tree.left), tree.value, right.merge(tree.right))