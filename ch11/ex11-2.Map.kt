// 예제 11-2 함수형 맵

class Map<out K: Comparable<@UnsafeVariance K>, V> {
    operator fun plus(entry: Pair<@UnsafeVariance K, V>): Map<K, V> = TODO()
    operator fun minus(key: @UnsafeVariance K): Map<K, V> = TODO()

    fun contains(key: @UnsafeVariance K): Boolean = TODO()
    fun get(key: @UnsafeVariance K): Result<MapEntry<@UnsafeVariance K, V>> = TODO()
    fun isEmpty(): Boolean = TODO()
    fun size(): Int = TODO("size")

    companion object {
        operator fun invoke(): Map<Nothing, Nothing> = Map()
    }
}