// 예제 5-2 foldRight 구현과 foldRight를 사용한 sum과 product 구현

fun <A, B> foldRight(list: List<A>,
                     identity: B,
                     f: (A) -> (B) -> B): B =
    when (list) {
        List.Nil -> identity
        is List.Cons -> f(list.head)(foldRight(list.tail, identity, f))
    }

fun sum(list: List<Int>): Int =
    foldRight(list, 0) { x -> { acc -> x + acc } }

fun product(list: List<Double>): Double =
    foldRight(list, 1.0) { x -> { acc -> x * acc } }