// 4.2.3 재귀 함수 값 사용하기

fun factorial(n: Int): Int = if (n == 0) 1 else n * factorial(n - 1)


// 연습문제 4-2(어려움)

val factorial: (Int) -> Int =


val factorial: (Int) -> Int =
    { n -> if (n <= 1) n else n * factorial(n - 1) }


val x: Int = x + 1


lateinit var x: Int
init {
    x = x + 1
}


object Factorial {
    lateinit var factorial: (Int) -> Int
    init {
        factorial = { n -> if (n <= 1) n else n * factorial(n - 1) }
    }
}


object Factorial {
    private lateinit var fact: (Int) -> Int
    init {
        fact = { n -> if (n <= 1) n else n * fact(n - 1) }
    }
    val factorial = fact
}


object Factorial {
    val factorial: (Int) -> Int by lazy { { n: Int ->
        if (n <= 1) n else n * factorial(n - 1)
    } }
}


object Factorial {
    val factorial: (Int) -> Int by lazy { ... }
}


{ n: Int -> if (n <= 1) n else n * factorial(n - 1) }


import java.math.BigInteger

fun factorial(n: BigInteger): BigInteger =
    if (n == BigInteger.ZERO)
        BigInteger.valueOf(1) // valueOf를 보여주고자 일부러 사용함
    else
        n * factorial(n - BigInteger.ONE)