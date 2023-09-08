// 9.6.2 평가와 함수 적용 추적하기

import com.fpinkotlin.common.List

private val f = { x: Int ->
    println("Mapping " + x)
    x * 3
}

private val p = { x: Int ->
    println("Filtering " + x)
    x % 2 == 0
}

fun main(args: Array<String>) {
    val list = List(1, 2, 3, 4, 5).map(f).filter(p)
    println(list)
}


// Mapping 1
// Mapping 2
// Mapping 3
// Mapping 4
// Mapping 5
// Filtering 15
// Filtering 12
// Filtering 9
// Filtering 6
// Filtering 3
// [6, 12, NIL]


fun main(args: Array<String>) {
    val stream = Stream.from(1).takeAtMost(5).map(f).filter(p)
    println(stream.toList())
}


// Mapping 1
// Filtering 3
// Mapping 2
// Filtering 6
// Mapping 3
// Filtering 9
// Mapping 4
// Filtering 12
// Mapping 5
// Filtering 15
// [6, 12, NIL]



// 연습문제 9-27

fun find(p: (A) -> Boolean): Result<A> = filter(p).head()