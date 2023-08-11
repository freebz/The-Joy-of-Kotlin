// 5.6.7 리스트 매핑과 필터링

// 연습문제 5-16

fun triple(list: List<Int>): List<Int> =
    List.foldRight(list, List()) { h ->
        { acc: List<Int> ->
            acc.cons(h * 3)
        }
    }



// 연습문제 5-17

fun doubleToString(list: List<A>): List<String> =
    List.foldRight(list, List()) { h ->
        { acc: List<String> ->
            acc.cons(h.toString())
        }
    }



// 연습문제 5-18

fun <B> map(f: (A) -> B): List<B>


fun <B> map(f: (A) -> B): List<B> =
    foldRight(Nil) { h: A -> { acc: List<B> -> Cons(f(h), acc) } }

fun <B> map(f: (A) -> B): List<B> =
    foldLeft(Nil) { acc: List<B> -> { h: A -> Cons(f(h), acc) } }.reverse()

fun <B> map(f: (A) -> B): List<B> = coFoldRight(Nil) { h -> { acc: List<B> -> Cons(f(h), acc) } }



// 연습문제 5-19

fun filter(p: (A) -> Boolean): List<A>


fun filter(p: (A) -> Boolean): List<A> =
    coFoldRight(Nil) { h -> { acc: List<A> -> if (p(h)) Cons(h, acc) else acc } }



// 연습문제 5-20

fun <B> flatMap(f: (A) -> List<B>): List<B>

List(1,2,3).flatMap { i -> List(i, -i) }

List(1,-1,2,-2,3,-3)


fun <B> flatMap(f: (A) -> List<B>): List<B> = flatten(map(f))



// 연습문제 5-21

fun filter(p: (A) -> Boolean): List<A> = flatMap { a -> if (p(a)) List(a) else Nil }