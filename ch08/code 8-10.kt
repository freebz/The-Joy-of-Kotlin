// 8.6 리스트를 자동으로 병렬 처리하기

// 8.6.1 모든 계산을 병렬화할 수는 없다

// 8.6.2 리스트를 부분 리스트로 나누기

// 연습문제 8-22

fun divide(depth: Int): List<List<A>>


fun splitListAt(index: Int): List<List<A>>


fun splitListAt(index: Int): List<List<A>> {
    tailrec fun splitListAt(acc: List<A>, list: List<A>, i: Int): List<List<A>> =
        when (list) {
            Nil -> List(list.reverse(), acc)
            is Cons ->
                if (i == 0)
                    List(list.reverse(), acc)
                else
                    splitListAt(acc.cons(list.head), list.tail, i - 1)
        }
    return when {
        index < 0        -> splitListAt(0)
        index > length() -> splitListAt(length())
        else             ->
                   splitListAt(Nil, this.reverse(), this.length() - index)
    }
}


fun divide(depth: Int): List<List<A>> {
    tailrec
    fun divide(list: List<List<A>>, depth: Int): List<List<A>> =
        when (list) {
            Nil -> list // dead code
            is Cons ->
                if (list.head.length() < 2 || depth < 1)
                    list
                else
                    divide(list.flatMap { x ->
                        x.splitListAt(x.length() / 2)
                    }, depth - 1)
        }
    return if (this.isEmpty())
        List(this)
    else
        divide(List(this), depth)
}