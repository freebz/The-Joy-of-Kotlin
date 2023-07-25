// 1.2.1 프로그램을 추론하는 데 치환 모델 사용하기

fun main(args: Array<String>) {
    val x = add(mult(2, 3), mult(4, 5))
    println(x)
}

fun add(a: Int, b: Int): Int {
    log(String.format("Returning ${a + b} as the result of $a + $b"))
    return a + b
}

fun mult(a: Int, b: Int) = a * by

fun log(m: String) {
    println(m)
}