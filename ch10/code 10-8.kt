// 10.10.1 두 함수를 사용해 접기

// 연습문제 10-8

// Tree
abstract fun <B> foldLeft(identity: B,
                          f: (B) -> (A) -> B,
                          g: (B) -> (B) -> B): B

// Empty
override fun <B> foldLeft(identity: B,
                          f: (B) -> (A) -> B,
                          g: (B) -> (B) -> B): B = identity

// T
override
fun <B> foldLeft(identity: B, f: (B) -> (A) -> B, g: (B) -> (B) -> B): B =
    g(right.foldLeft(identity, f, g))(f(left
                                .foldLeft(identity, f, g))(this.value))


override
fun <B> foldLeft(identity: B, f: (B) -> (A) -> B, g: (B) -> (B) -> B): B =
    g(left.foldLeft(identity, f, g))(f(right.foldLeft(identity, f, g))(this.value))


fun main(args: Array<String>) {
    val result = Tree(4, 2, 6, 1, 3, 5, 7)
        .foldLeft(List(), { list: List<Int> -> { a: Int -> list.cons(a) } })
        { x -> { y -> y.concat(x) } }
    println(result)
}

// [7, 5, 3, 1, 2, 4, 6, NIL]

// [7, 5, 6, 3, 4, 1, 2, NIL]


override
fun <B> foldRight(identity: B, f: (A) -> (B) -> B, g: (B) -> (B) -> B): B =
    g(f(this.value)(left.foldRight(identity, f, g)))(right.foldRight(identity, f, g))