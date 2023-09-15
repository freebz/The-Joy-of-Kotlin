import kotlin.math.max

sealed class Tree<out A: Comparable<@UnsafeVariance A>> {

    abstract val size: Int
    abstract val height: Int
    internal abstract val value: A
    internal abstract val left: Tree<A>
    internal abstract val right: Tree<A>
    abstract fun isEmpty(): Boolean
    abstract fun max(): Result<A>
    abstract fun min(): Result<A>
    abstract fun merge(tree: Tree<@UnsafeVariance A>): Tree<A>
    abstract fun <B> foldLeft(identity: B,
                              f: (B) -> (A) -> B,
                              g: (B) -> (B) -> B): B
    abstract fun <B> foldRight(identity: B,
                               f: (A) -> (B) -> B,
                               g: (B) -> (B) -> B): B
    abstract fun <B> foldInOrder(identity: B, f: (B) -> (A) -> (B) -> B): B
    abstract fun <B> foldPreOrder(identity: B, f: (A) -> (B) -> (B) -> B): B
    abstract fun <B> foldPostOrder(identity: B, f: (B) -> (B) -> (A) -> B): B
    protected abstract fun rotateRight(): Tree<A>
    protected abstract fun rotateLeft(): Tree<A>

    internal object Empty : Tree<Nothing>() {
        override val size: Int = 0
        override val height: Int = -1
        override val value: Nothing by lazy {
            throw IllegalStateException("No value in Empty")
        }
        override val left: Tree<Nothing> by lazy {
            throw IllegalStateException("No left in Empty")
        }
        override val right: Tree<Nothing> by lazy {
            throw IllegalStateException("No right in Empty")
        }
        override fun isEmpty(): Boolean = true
        override fun toString(): String = "E"
        override fun max(): Result<Nothing> = Result.empty()
        override fun min(): Result<Nothing> = Result.empty()
        override fun merge(tree: Tree<Nothing>): Tree<Nothing> = tree
        override fun <B> foldLeft(identity: B,
                                  f: (B) -> (Nothing) -> B,
                                  g: (B) -> (B) -> B): B = identity
        override fun <B> foldRight(identity: B,
                                   f: (Nothing) -> (B) -> B,
                                   g: (B) -> (B) -> B): B = identity
        override fun <B> foldInOrder(identity: B, f: (B) ->(Nothing) -> (B) -> B): B = identity
        override fun <B> foldPreOrder(identity: B, f: (Nothing) -> (B) -> (B) -> B): B = identity
        override fun <B> foldPostOrder(identity: B, f: (B) -> (B) -> (Nothing) -> B): B = identity
        override fun rotateRight(): Tree<Nothing> = this
        override fun rotateLeft(): Tree<Nothing> = this
    }

    internal class T<out A: Comparable<@UnsafeVariance A>>(
            override val left: Tree<A>,
            override val value: A,
            override val right: Tree<A>): Tree<A>() {
        override val size: Int = 1 + left.size + right.size
        override val height: Int = 1 + max(left.height, right.height)
        override fun isEmpty(): Boolean = false
        override fun toString(): String = "(T $left $value $right)"
        override fun max(): Result<A> = right.max().orElse { Result(value) }
        override fun min(): Result<A> = left.min().orElse { Result(value) }
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
        override fun <B> foldLeft(identity: B, f: (B) -> (A) -> B, g: (B) -> (B) -> B): B =
            g(right.foldLeft(identity, f, g))(f(left.foldLeft(identity, f, g))(this.value))
        // override fun <B> foldLeft(identity: B, f: (B) -> (A) -> B, g: (B) -> (B) -> B): B =
        //     g(left.foldLeft(identity, f, g))(f(right.foldLeft(identity, f, g))(this.value))
        override fun <B> foldRight(identity: B, f: (A) -> (B) -> B, g: (B) -> (B) -> B): B =
            g(f(this.value)(left.foldRight(identity, f, g)))(right.foldRight(identity, f, g))
        override fun <B> foldInOrder(identity: B, f: (B) ->(A) -> (B) -> B): B =
            f(left.foldInOrder(identity, f))(value)(right.foldInOrder(identity, f))
        override fun <B> foldPreOrder(identity: B, f: (A) -> (B) -> (B) -> B): B =
            f(value)(left.foldPreOrder(identity, f))(right.foldPreOrder(identity, f))
        override fun <B> foldPostOrder(identity: B, f: (B) -> (B) -> (A) -> B): B =
            f(left.foldPostOrder(identity, f))(right.foldPostOrder(identity, f))(value)
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
    }

    companion object {
        operator fun <A: Comparable<A>> invoke(): Tree<A> = Empty
        
        operator fun <A: Comparable<A>> invoke(vararg az: A): Tree<A> =
            az.foldRight(Empty, { a: A, tree: Tree<A> -> tree.plus(a) })

        operator fun <A: Comparable<A>> invoke(list: List<A>): Tree<A> =
            list.foldLeft(Empty as Tree<A>, { tree: Tree<A> ->
                { a: A ->
                    tree.plus(a)
                }
            })

        fun <A: Comparable<A>> lt(first: A, second: A): Boolean = first < second

        fun <A: Comparable<A>> lt(first: A, second: A, third: A): Boolean =
            lt(first, second) && lt(second, third)

        fun <A: Comparable<A>> ordered(left: Tree<A>,
                                       a: A,
                                       right: Tree<A>): Boolean =
            (left.max().flatMap { lMax ->
                right.min().map { rMin ->
                    lt(lMax, a, rMin)
                }
            }.getOrElse(left.isEmpty() && right.isEmpty()) ||
                left.min()
                    .mapEmpty()
                    .flatMap {
                        right.min().map { rMin ->
                            lt(a, rMin)
                        }
                    }.getOrElse(false) ||
                right.min()
                    .mapEmpty()
                    .flatMap {
                        left.max().map { lMax ->
                            lt(lMax, a)
                        }
                    }.getOrElse(false))

        operator fun <A: Comparable<A>> invoke(left: Tree<A>, a: A, right: Tree<A>): Tree<A> =
            when {
                ordered(left, a, right) -> T(left, a, right)
                ordered(right, a, left) -> T(right, a, left)
                else -> Tree(a).merge(left).merge(right)
            }

        fun log2nlz(n: Int): Int = when (n) {
            0 -> 0
            else -> 31 - Integer.numberOfLeadingZeros(n)
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
    }

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

    fun contains(a: @UnsafeVariance A): Boolean = when (this) {
        is Empty -> false
        is T -> when {
            a < value -> left.contains(a)
            a > value -> right.contains(a)
            else -> value == a
        }
    }

    fun <B: Comparable<B>> contains1(a: B): Boolean =
        when (this) {
            is Empty -> false
            is T -> a == value || left.contains1(a) || right.contains1(a)
        }

    fun contains2(a: @UnsafeVariance A): Boolean =
        when (this) {
            is Empty -> false
            is T -> a == value || left.contains2(a) || right.contains2(a)
        }

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

    fun <B: Comparable<B>> map(f: (A) -> B): Tree<B> =
        foldInOrder(Empty) { t1: Tree<B> ->
            { i: A ->
                { t2: Tree<B> ->
                    Tree(t1, f(i), t2)
                }
            }
        }

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
}
