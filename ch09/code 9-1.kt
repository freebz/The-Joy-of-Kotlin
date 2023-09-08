// 9.1 즉시 계산과 지연 계산

val result =
    if (testCondition())
        getIfTrue()
    else
        getIfFalse()


val x: Int = 2 + 3

val x: Int = getValue()


fun main(args: Array<String>) {
    val x = getValue()
}

fun getValue(): Int {
    println("Returning 5")
    return 5
}