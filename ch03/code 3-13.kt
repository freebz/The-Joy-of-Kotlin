// 3.2.9 함수 재사용하기

// 연습문제 3-2

fun <T, U, V> compose(f: (U) -> V, g: (T) -> U): (T) -> V = { f(g(it)) }