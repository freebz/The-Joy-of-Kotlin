// 10.10.3 접기 연산 구현 선택하기

// List identity
list.foldRight(List()) { i -> { l -> l.cons(i) }}

list.foldLeft(List()) { l -> { i -> l.reverse.cons(i).reverse() }}



// 연습문제 10-10(어려움)

operator fun <A: Comparable<A>> invoke(left: Tree<A>, a: A, right: Tree<A>): Tree<A>


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


// companion object
operator
fun <A: Comparable<A>> invoke(left: Tree<A>, a: A, right: Tree<A>): Tree<A> =
    when {
        ordered(left, a, right) -> T(left, a, right)
        ordered(right, a, left) -> T(right, a, left)
        else -> Tree(a).merge(left).merge(right)
    }


tree.foldInOrder(Tree<Int>(),
    { t1 -> { i -> { t2 -> Tree(t1, i, t2) } } })
tree.foldPostOrder(Tree<Int>(),
    { t1 -> { t2 -> { i -> Tree(t1, i, t2) } } })
tree.foldPreOrder(Tree<Int>(),
    { i -> { t1 -> { t2 -> Tree(t1, i, t2) } } })


// Tree에 추상 메서드를 정의하고 T에 이 구현을 정의한다:
override fun <B> foldLeft(identity: B, f: (B) -> (A) -> B): B =
    toListPreOrderLeft().foldLeft(identity, f)

// Tree 안에 이 함수를 정의한다:
override fun toListPreOrderLeft(): List<A> =
    left.toListPreOrderLeft().concat(right.toListPreOrderLeft()).cons(value)