// 3.3.3 고차 함수 구현하기

// 연습문제 3-4

(Int) -> Int

(T) -> (T) -> T

((Int) -> Int) -> ((Int) -> Int) -> (Int) -> Int

{ x -> { y -> { z -> x(y(z)) } } }


val compose: ((Int) -> Int) -> ((Int) -> Int) -> (Int) -> Int =
                               { x -> { y -> { z -> x(y(z)) } } }


val compose = { x: (Int) -> Int -> { y: (Int) -> Int ->
                                   { z: Int -> x(y(z)) } } }


typealias IntUnaryOp = (Int) -> Int

val compose: (IntUnaryOp) -> (IntUnaryOp) -> IntUnaryOp =
                             { x -> { y -> { z -> x(y(z)) } } }

val square: IntUnaryOp = { it * it }

val triple: IntUnaryOp = { it * 3 }

val squareOfTriple = compose(square)(triple)


println(squareOfTriple(2))

36