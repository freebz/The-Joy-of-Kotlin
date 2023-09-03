// 8.1 length의 단점

fun length(): Int = foldLeft(0) { { _ -> it + 1 } }

fun sum(list: List<Int>): Int = list.foldRight(0) { x -> { y -> x + y } }