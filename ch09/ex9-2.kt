// 예제 9-2 즉시 계산의 문제점

fun main(args: Array<String>) {
    println(getFirst() || getSecond())
    println(or(getFirst(), getSecond()))
}

fun getFirst(): Boolean = true

fun getSecond(): Boolean = throw IllegalStateException()

fun or(a: Boolean, b: Boolean): Boolean = if (a) true else b

fun and(a: Boolean, b: Boolean): Boolean = if (a) b else false