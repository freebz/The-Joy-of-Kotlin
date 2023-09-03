// 8.5.4 부분 리스트 검색하기

// 연습문제 8-16

fun hasSubList(sub: List<@UnsafeVariance A>): Boolean


fun startsWith(sub: List<@UnsafeVariance A>): Boolean {
    tailrec fun startsWith(list: List<A>, sub: List<A>): Boolean =
        when (sub) {
            Nil -> true
            is Cons -> when (list) {
                Nil -> false
                is Cons ->
                    if (list.head == sub.head)
                        startsWith(list.tail, sub.tail)
                    else
                        false
            }
        }
    return startsWith(this, sub)
}


fun hasSubList(sub: List<@UnsafeVariance A>): Boolean {
    tailrec
    fun <A> hasSubList(list: List<A>, sub: List<A>): Boolean =
        when (list) {
            Nil -> sub.isEmpty()
            is Cons ->
                if (list.startsWith(sub))
                    true
                else
                    hasSubList(list.tail, sub)
        }
    return hasSubList(this, sub)
}