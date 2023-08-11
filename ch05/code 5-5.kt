// 5.6.2 리스트 연결

// companion object
fun <A> concat(list1: List<A>, list2: List<A>): List<A> = when (list1) {
    Nil -> list2
    is Cons -> concat(list1.tail, list2).cons(list1.head)
}

// List
fun concat(list: List<A>): List<A> = concat(this, list)