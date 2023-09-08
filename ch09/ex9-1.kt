// 예제 9-1 and와 or 논리함수

fun main(args: Array<String>) {
    println(or(true, true))
    println(or(true, false))
    println(or(false, true))
    println(or(false, false))
    println(and(true, true))
    println(and(true, false))
    println(and(false, true))
    println(and(false, false))
}

fun or(a: Boolean, b: Boolean): Boolean = if (a) true else b

fun and(a: Boolean, b: Boolean): Boolean = if (a) b else false