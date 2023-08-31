// 6.4.1 Option에서 값 가져오기

// 연습문제 6-1

fun getOrElse(defaut: A): A

// Option
fun getOrElse(default: @UnsafeVariance A): A = when (this) {
    is None -> default
    is Some -> value
}


val max1 = max(listOf(3, 5, 7, 2, 1)).getOrElse(0)
val max2 = max(listOf()).getOrElse(0)


fun getDefault(): Int = throw RuntimeException()

fun main(args: Array<String>) {
    val max1 = max(listOf(3, 5, 7, 2, 1)).getOrElse(getDefault())
    println(max1)
    val max2 = max(listOf()).getOrElse(getDefault())
    println(max2)
}



// 연습문제 6-2

fun getOrElse(default: () -> @UnsafeVariance A): A = when (this) {
    is None -> default()
    is Some -> value
}


val max1 = max(listOf(3, 5, 7, 2, 1)).getOrElse(::getDefault)
println(max1)
val max2 = max(listOf()).getOrElse(::getDefault)
println(max2)