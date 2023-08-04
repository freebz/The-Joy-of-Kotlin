// 4.4.2 재귀 함수에서 메모화 사용하기

// 연습문제 4-15


// 4.4.3 암시적 메모화 사용하기

val f = { x: Pair<BigInteger, BigInteger> ->
    Pair(x.second, x.first + x.second) }


val f = { (a, b): Pair<BigInteger, BigInteger> -> Pair(b, a + b)}



// 연습문제 4-16

fun <T> iterate(seed: T, f: (T) -> T, n: Int): List<T> {
    tailrec fun iterate_(acc: List<T>, seed: T): List<T> =
        if (acc.size < n)
            iterate_(acc + seed, f(seed))
        else
            acc
    return iterate_(listOf(), seed)
}



// 연습문제 4-17

fun <T, U> map(list: List<T>, f: (T) -> U): List<U> {
    tailrec fun map_(acc: List<U>, list: List<T>): List<U> =
        if (list.isEmpty())
            acc
        else
            map_(acc + f(list.head()), list.tail())
    return map_(listOf(), list)
}


fun <T> copy(list: List<T>): List<T> =
    foldLeft(list, listOf()) { lst, elem -> lst + elem }


fun <T, U> map(list: List<T>, f: (T) -> U): List<U> =
    foldLeft(list, listOf()) { acc, elem -> acc + f(elem) }



// 연습문제 4-18

fun fiboCorecursive(number: Int): String {
    val seed = Pair(BigInteger.ZERO, BigInteger.ONE)
    val f = { x: Pair<BigInteger, BigInteger> -> Pair(x.second, x.first + x.second) }
    val listOfPairs = iterate(seed, f, number + 1)
    val list = map(listOfPairs) { p -> p.first }
    return makeString(list, ", ")
}