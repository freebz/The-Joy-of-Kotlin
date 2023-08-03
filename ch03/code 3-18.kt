// 3.3.5 익명 함수 사용하기

val f: (Double) -> Double = { Math.PI / 2 - it }
val sin: (Double) -> Double = Math::sin
val cos: Double = compose(f, sin)(2.0)


val cosValue: Double =
    compose({ x: Double -> Math.PI / 2 - x }, Math::sin)(2.0)


val cos = higherCompose<Double, Double, Double>()
    ({ x: Double -> Math.PI / 2 - x })(Math::sin)

val cosValue = cos(2.0)


val cos = higherCompose<Double, Double, Double>()()
    { x: Double -> Math.PI / 2 - x }(Math::sin)



// 익명 함수를 사용해야 하는 경우와 이름이 있는 함수를 사용해야 하는 경우

fun cos(arg: Double) = compose({ x -> Math.PI / 2 - x },Math::sin)(arg)



// 타입 추론 구현하기

fun <T, U, V> compose(f: (U) -> V, g: (T) -> U): (T) -> V = { f(g(it)) }


fun cos(arg: Double) = compose({ x -> Math.PI / 2 - x }, { y -> Math.sin(y)})(arg)

// Error: Kotlin: Type inference failed: Not enough information to infer
//     parameter T in fun  <T, U, V> compose(f: (U) -> V, g: (T) -> U): (T) -> V
//     Please specify it explicitly.
// Error: Kotlin: Cannt infer a type for this parameter.
//     Please specify it explicitly.
// Error: Kotlin: Cannt infer a type for this parameter.
//     Please specify it explicitly.


fun cos(arg: Double) =
    compose({ x: Double -> Math.PI / 2 - x },
            { x: Double -> Math.sin(x)})(arg)