// 예제 7-3 Result를 반환하는 Map.getResult 함수

fun <K, V> Map<K, V>.getResult(key: K) = when {
    this.containsKey(key) -> Result(this[key])
    else -> Result.failure("Key $key not found in map")
}