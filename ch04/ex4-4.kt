// 예제 4-4 인자가 세 개인 메모화한 함수 테스트하기

val f3m = Memoizer.memoize { x: Int ->
    Memoizer.memoize { y: Int ->
        Memoizer.memoize { z: Int ->
            longComputation(z) - (longComputation(y) + longComputation(x))
        }
    }
}

fun main(args: Array<String>) {
    val startTime1 = System.currentTimeMillis()
    val result1 = f3m(41)(42)(43)
    val time1 = System.currentTimeMillis() - startTime1
    val startTime2 = System.currentTimeMillis()
    val result2 = f3m(41)(42)(43)
    val time2 = System.currentTimeMillis() - startTime2
    println("First call to memoized function: result = $result1, time = $time1")
    println("Second call to memoized function: result = $result2, time = $time2")
}