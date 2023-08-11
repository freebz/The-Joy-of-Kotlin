// 5.6.3 리스트의 끝에서부터 원소 제거하기

// 연습문제 5-5

fun init(): List<A>


// companion object
tailrec fun <A> reverse(acc: List<A>, list: List<A>): List<A> =
    when (list) {
        Nil -> acc
        is Cons -> reverse(acc.cons(list.head), list.tail)
    }


// List
fun reverse(): List<A> = reverse(List.invoke(), this)

// List
fun reverse(): List<A> {
    tailrec fun <A> reverse(acc: List<A>, list: List<A>): List<A> = when (list) {
        Nil -> acc
        is Cons -> reverse(acc.cons(list.head), list.tail)
    }
    return reverse(List.invoke(), this)
}

// List
fun init(): List<A> = reverse().drop(1).reverse()