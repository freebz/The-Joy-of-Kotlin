// 3.3 고급 함수 기능

// 3.3.1 인자가 여럿 있는 함수 처리하기

(int) -> (Int) -> Int

(Int) -> ((Int) -> Int)


// 연습문제 3-3

val add: (Int) -> (Int) -> Int = { a -> { b -> a + b } }


typealias IntBinOp = (Int) -> (Int) -> Int

val add: IntBinOp = { a -> { b -> a + b } }
val mult: IntBinOp = { a -> { b -> a * b } }