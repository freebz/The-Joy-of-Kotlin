// 예제 12-1 데이터 출력하기

fun main(args: Array<String>) {
    val ra = Result(4)
    val rb = Result(0)
    val inverse: (Int) -> Result<Double> = { x ->
        when {
            x != 0 -> Result(1.toDouble() / x)
            else -> Result.failure("Division by 0")
        }
    }

    val showResult: (Double) -> Unit = ::println
    val showError: (RuntimeException) -> Unit =
        { println("Error - ${it.message}")}

    val rt1 = ra.flatMap(inverse)
    val rt2 = rb.flatMap(inverse)

    print("Inverse of 4: ")
    rt1.forEach(showResult, showError)

    System.out.print("Inverse of 0: ")
    rt2.forEach(showResult, showError)
}


// Inverse of 4: 0.25
// Inverse of 0: Error - Division by 0