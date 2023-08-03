// 3.2.8 함수 합성

fun square(n: Int) = n * n

fun triple(n: Int) = n * 3

println(square(triple(2)))

36


// 연습문제 3-1

fun compose(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = { x -> f(g(x)) }


fun compose(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = { f(g(it)) }


val squareOfTriple = compose(::square, ::triple)

println(squareOfTriple(2))