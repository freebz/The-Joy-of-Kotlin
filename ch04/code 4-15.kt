// 4.4 메모화

// 4.4.1 루프를 사용하는 프로그래밍에서 메모화 사용하기

fun main(args: Array<String>) {
    println(fibo(10))
}

fun fibo(limit: Int): String =
    when {
        limit < 1 -> throw IllegalArgumentException()
        limit == 1 -> "1"
        else -> {
            var fibo1 = BigInteger.ONE
            var fibo2 = BigInteger.ONE
            var fibonacci: BigInteger
            val builder = StringBuilder("1, 1")
            for (i in 2 until limit) {
                fibonacci = fibo1.add(fibo2)
                builder.append(", ").append(fibonacci)
                fibo1 = fibo2
                fibo2 = fibonacci
            }
            builder.toString()
        }
    }