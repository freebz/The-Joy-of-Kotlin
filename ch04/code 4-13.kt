// 4.3.4 공재귀 리스트 만들기

for (int i = 0; i < limit; i++) {
    // 처리 작업
}


for (int i = 0; i < 5; i++) {
    System.out.println(i);
}


listOf(0, 1, 2, 3, 4).forEach(::println)



// 연습문제 4-9

fun range(start: Int, end: Int): List<Int>

fun range(start: Int, end: Int): List<Int> {
    val result: MutableList<Int> = mutableListOf()
    var index = start
    while (index < end) {
        result.add(index)
        index++
    }
    return result
}



// 연습문제 4-10

fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T>

fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    val result: MutableList<T> = mutableListOf()
    var elem = seed
    while (p(elem)) {
        result.add(elem)
        elem = f(elem)
    }
    return result
}



// 연습문제 4-11

fun range(start: Int, end: Int): List<Int> =
    unfold(start, { it + 1 }, { it < end })



// 연습문제 4-12

fun range(start: Int, end: Int): List<Int> =
    if (end <= start)
        listOf()
    else
        prepend(range(start + 1, end), start)



// 연습문제 4-13

fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> =
    if (p(seed))
        prepend(unfold(f(seed), f, p), seed)
    else
        listOf()



// 연습문제 4-14

fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    tailrec fun unfold_(acc: List<T>,
                        seed: T,
                        f: (T) -> T, p: (T) -> Boolean): List<T> =
        if (p(seed))
            unfold_(acc + seed, f(seed), f, p)
        else
            acc
    return unfold_(listOf(), seed, f, p)
}


fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    tailrec fun unfold_(acc: List<T>, seed: T): List<T> =
        if (p(seed))
            unfold_(acc + seed, f(seed))
        else
            acc
    return unfold_(listOf(), seed)
}