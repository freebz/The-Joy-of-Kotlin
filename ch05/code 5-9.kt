// 5.6.6 스택을 안전하게 사용하는 foldRight 만들기

// 연습문제 5-13

// companion object
private tailrec fun <A, B> cofoldRight(acc: B,
                                       list: List<A>,
                                       identity: B,
                                       f: (A) -> (B) -> B): B =
    when (list) {
        List.Nil -> acc
        is List.Cons ->
        cofoldRight(f(list.head)(acc), list.tail, identity, f)
    }

// List
fun <B> cofoldRight(identity: B, f: (A) -> (B) -> B): B =
    cofoldRight(identity, this.reverse(), identity, f)



// 연습문제 5-14

// List
fun <A> concatViaFoldRight(list1: List<A>, list2: List<A>): List<A> =
    foldRight(list1, list2) { x -> { acc -> Cons(x, acc) } }

// List
fun <A> concatViaFoldLeft(list1: List<A>, list2: List<A>): List<A> =
    list1.reverse().foldLeft(list2) { acc -> acc::cons }

// List
fun <A> concatViaFoldLeft(list1: List<A>, list2: List<A>): List<A> =
    list1.reverse().foldLeft(list2) { acc -> { y -> acc.cons(y) } }

// List
fun <A> concatViaFoldLeft(list1: List<A>, list2: List<A>): List<A> =
    list1.reverse().foldLeft(list2) { acc -> { y -> Cons(y, acc) } }



// 연습문제 5-15

// companion object
fun <A> flatten(list: List<List<A>>): List<A> = list.foldRight(Nil) { x -> x::concat }

// companion object
fun <A> flatten(list: List<List<A>>): List<A> = list.coFoldRight(Nil) { x -> x::concat }