// 3.3.6 로컬 함수 정의하기

fun cos(arg: Double): Double {
    fun f(x: Double): Double = Math.PI / 2 - x
    fun sin(x: Double): Double = Math.sin(x)
    return compose(::f, ::sin)(arg)
}