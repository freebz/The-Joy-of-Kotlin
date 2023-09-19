// 14.3.4 프로퍼티를 리스트로 읽기

fun readAsIntList(name: String): Result<List<Int>> =
    readAsString(name).flatMap {
        try {
            Result(fromSeparated(it, ",").map(String::toInt))
        } catch (e: NumberFormatException) {
            Result.failure<List<Int>>(
                "Invalid value while parsing property '$name' to List<Int>: '$it'")
        }
}


fun readAsIntList(name: String): Result<List<Int>> =
    readAsString(name).flatMap {
        try {
            // 다음 줄은 코틀린 List를 사용한다
            Result(it.split(",").map(String::toInt))
        } catch (e: NumberFormatException) {
            Result.failure<List<Int>>(
                "Invalid value while parsing property '$name' to List<Int>: $it")
        }
}


fun <T> readAsList(name: String, f: (String) -> T): Result<List<T>> =
    readAsString(name).flatMap {
        try {
            Result(fromSeparated(it, ",").map(f))
        } catch (e: Exception) {
            Result.failure<List<T>>(
                "Invalid value while parsing property '$name' to List: $it")
        }
}


fun readAsIntList(name: String): Result<List<Int>> =
    readAsList(name, String::toInt)
fun readAsDoubleList(name: String): Result<List<Double>> =
    readAsList(name, String::toDouble)
fun readAsBooleanList(name: String): Result<List<Boolean>> =
    readAsList(name, String::toBoolean)