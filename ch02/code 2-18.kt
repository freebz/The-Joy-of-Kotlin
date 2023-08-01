// 2.7.4 확장 함수 사용하기

fun <T> length(list: List<T>) = list.size


fun <T> List<T>.length() = this.size


fun <T> List<T>.length() = this.size

val ints = listOf(1, 2, 3, 4, 5, 6, 7)

val listLength = ints.length()


fun List<Int>.product(): Int = this.fold(1) { a, b -> a * b }

val ints = listOf(1, 2, 3, 4, 5, 6, 7)

val product = ints.product()