// 3.2.6 함수 값 사용하기

fun double(x: Int): Int = x * 2


val double: (Int) -> Int = { x -> x * 2 }


val doubleThenIncrement: (Int) -> Int = { x ->
    val double = x * 2
    double + 1
}


val add: (Int, Int) -> Int = { x, y -> x + y }


val double: (Int) -> Int = { it * 2 }