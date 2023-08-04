// 4.3.1 이중 재귀 함수 사용하기

fun fibonacci(number: Int): Int =
    if (number == 0 || number == 1)
        1
    else
        fibonacci(number - 1) + fibonacci(number - 2)


fun main(args: Array<String>) {
    (0 until 10).forEach { print("${fibonacci(it)} ") }
}

// 1 1 2 3 5 8 13 21 34 55



// 연습문제 4-3

tailrec fun fib(val1: BigInteger, val2: BigInteger, x: BigInteger): BigInteger

tailrec fun fib(val1: BigInteger, val2: BigInteger, x: BigInteger): BigInteger =
    when {
        (x == BigInteger.ZERO) -> BigInteger.ONE
        ...
    }

tailrec fun fib(val1: BigInteger, val2: BigInteger, x: BigInteger): BigInteger =
    when {
        (x == BigInteger.ZERO) -> BigInteger.ONE
        (x == BigInteger.ONE) -> val1 + val2
        ...
    }

tailrec fun fib(val1: BigInteger, val2: BigInteger, x: BigInteger): BigInteger =
    when {
        (x == BigInteger.ZERO) -> BigInteger.ONE
        (x == BigInteger.ONE) -> val1 + val2
        else -> fib(val2, val1 + val2, x - BigInteger.ONE)
    }


import java.math.BigInteger

fun fib(x: Int): BigInteger {
    tailrec fun fib(val1: BigInteger, val2: BigInteger, x: BigInteger): BigInteger =
        when {
            (x == BigInteger.ZERO) -> BigInteger.ONE
            (x == BigInteger.ONE) -> val1 + val2
            else -> fib(val2, val1 + val2, x - BigInteger.ONE)
        }
    return fib(BigInteger.ZERO, BigInteger.ONE, BigInteger.valueOf(x.toLong()))
}