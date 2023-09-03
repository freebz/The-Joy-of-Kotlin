// 8.5 리스트에 대한 일반적인 추상화

// 8.5.1 리스트를 묶거나 풀기

// 연습문제 8-8

fun <A, B, C> zipWith(list1: List<A>,
                      list2: List<B>,
                      f: (A) -> (B) -> C): List<C>


fun <A, B, C> zipWith(list1: List<A>,
                      list2: List<B>,
                      f: (A) -> (B) -> C): List<C> {
    tailrec
    fun zipWith(acc: List<C>,
                list1: List<A>,
                list2: List<B>): List<C> = when (list1) {
        List.Nil -> acc
        is List.Cons -> when (list2) {
            List.Nil -> acc
            is List.Cons ->
                zipWith(acc.cons(f(list1.head)(list2.head)), list1.tail, list2.tail)
        }
    }
    return zipWith(List(), list1, list2).reverse()
}



// 연습문제 8-9

fun <A, B, C> product(list1: List<A>,
                      list2: List<B>,
                      f: (A) -> (B) -> C): List<C> =
    list1.flatMap { a -> list2.map { b -> f(a)(b) } }


product(List(1, 2), List(4, 5, 6)) { x -> { y: Int -> Pair(x, y) } }
zipWith(List(1, 2), List(4, 5, 6)) { x -> { y: Int -> Pair(x, y) } }

// [(1, 4), (1, 5), (1, 6), (2, 4), (2, 5), (2, 6), NIL]
// [(1, 4), (2, 5), NIL]



// 연습문제 8-10

fun <A, B> unzip(list: List<Pair<A, B>>): Pair<List<A>, List<B>>

fun <A, B> unzip(list: List<Pair<A, B>>): Pair<List<A>, List<B>> =
    list.coFoldRight(Pair(List(), List())) { pair ->
        { listPair: Pair<List<A>, List<B>> ->
            Pair(listPair.first.cons(pair.first),
                listPair.second.cons(pair.second))
        }
    }



// 연습문제 8-11

fun <A1, A2> unzip(f: (A) -> Pair<A1, A2>): Pair<List<A1>, List<A2>>

// List
fun <A1, A2> unzip(f: (A) -> Pair<A1, A2>): Pair<List<A1>, List<A2>> =
    this.coFoldRight(Pair(Nil, Nil)) { a ->
        { listPair: Pair<List<A1>, List<A2>> ->
            val pair = f(a)
            Pair(listPair.first.cons(pair.first),
                 listPair.second.cons(pair.second))
        }
    }


fun <A1, A2> unzip(f: (A) -> Pair<A1, A2>): Pair<List<A1>, List<A2>> =
    this.coFoldRight(Pair(Nil, Nil)) { a ->
        { listPair: Pair<List<A1>, List<A2>> ->
            f(a).let {
                Pair(listPair.first.cons(it.first),
                     listPair.second.cons(it.second))
            }
        }
    }


fun <A, B> unzip(list: List<Pair<A, B>>): Pair<List<A>, List<B>> = list.unzip { it }