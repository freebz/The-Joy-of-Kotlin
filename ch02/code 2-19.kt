// 2.7.5 람다 사용하기

fun triple(list: List<Int>): List<Int> = list.map({ a -> a * 3 })


fun triple(list: List<Int>): List<Int> = list.map { a -> a * 3 }

fun product(list: List<Int>): Int = list.fold(1) { a, b -> a * b }


fun List<Int>.product(): Int = this.fold(1) {(a, b) -> q * b}


// 람다의 파라미터 타입

fun List<Int>.product(): Int = this.fold(1) { a: Int, b: Int -> a * b }


// 여러 줄 람다

fun List<Int>.product(): Int = this.fold(1) { a, b ->
    val result = a * b
    result
}


// 람다를 위한 간이 구문

fun triple(list: List<Int>): List<Int> = list.map { it * 3 }


fun triple(list: List<Int>): List<Int> = list.map {
    it * 3
}

fun triple(list: List<Int>): List<Int> = list.map { a ->
    a * 3
}


// 클로저 안의 람다

val multiplier = 3

fun multiplyAll(list: List<Int>): List<Int> = list.map { it * multiplier }


fun multiplyAll(list: List<Int>, multiplier: int): list.map { it * multiplier }