// 5.6.4 재귀와 고차 함수로 리스트 접기

// 연습문제 5-6

fun sum(ints: List<Int>): Int = when (ints) {
    Nil -> 0
    is Cons -> ints.head + sum(ints.tail)
}