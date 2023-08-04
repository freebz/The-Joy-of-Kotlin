// 4.4.5 다인자 함수의 메모화 구현하기

val mhc = Memoizer.memoize { x: Int ->
    Memoizer.memoize { y: Int ->
        x + y
    }
}


val f3 = { x: Int -> { y: Int -> { z: Int -> x + y - z } } }

val f3m = Memoizer.memoize { x: Int ->
    Memoizer.memoize { y: Int ->
        Memoizer.memoize { z: Int -> x + y - z }
    }
}


// First call to memoized function: result = -40, time = 3003
// Second call to memoized function: result = -40, time = 0


data class Tuple4<T, U, V, W>(val first: T,
                              val second: U,
                              val third: V,
                              val fourth: W)