// 6.3 널 참조에 대한 대안

val map: Map<String, Person = ...
val person: Person = map["Joe"]


fun mean(list: List<Int>): Double = when {
    list.isEmpty() -> TODO("What should you return?")
    else -> list.sum().toDouble() / list.size
}


fun mean(list: List<Int>): Double = when {
    list.isEmpty() -> Double.NaN
    else -> list.sum().toDouble() / list.size
}


fun <T, U> f(list: List<T>): U = when {
    list.isEmpty() -> ???
    else -> ... (U를 만들어내는 계산)
}


fun mean(list: List<Int>): Double = when {
    list.isEmpty() -> throw IllegalArgumentException("Empty list")
    else -> list.sum().toDouble() / list.size
}


fun mean(list: List<Int>): Double? = when {
    list.isEmpty() -> null
    else -> list.sum().toDouble() / list.size
}


fun max(list: List<Int>): Double = when {
    list.isEmpty() -> default
    else -> list.max()
}


fun max(list: List<Int>, default: Int): Int = list.max() ?: default


val max = max(listOf(1, 2, 3), 0)

println("The maximum is $max")


val max = max(listOf(1, 2, 3), 0)

print(if (max != 0) "The maximum is $max\n", else "")


val max = listOf(1, 2, 3).max()

if (max != null) println("The maximum is $max")