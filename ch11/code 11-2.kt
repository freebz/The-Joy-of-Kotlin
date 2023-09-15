// 11.2 레드-블랙 트리의 활용: 맵

// 11.2.1 맵 구현하기

// 연습문제 11-2

fun operator get(element: @UnsafeVariance A): Result<A>


class MapEntry<K: Comparable<@UnsafeVariance K>, V>
    private constructor(private val key: K, val value: Result<V>):
                        Comparable<MapEntry<K, V>> {
        override fun compareTo(other: MapEntry<K, V>): Int =
            this.key.compareTo(other.key)
        override fun toString(): String = "MapEntry($key, $value)"
        override fun equals(other: Any?): Boolean =
            this === other || when (other) {
                is MapEntry<*, *> -> key == other.key
                else -> false
            }
        override fun hashCode(): Int = key.hashCode()

        companion object {
            fun <K: Comparable<K>, V> of(key: K, value: V): MapEntry<K, V> =
                MapEntry(key, Result(value))
            operator
            fun <K: Comparable<K>, V> invoke(pair: Pair<K, V>): MapEntry<K, V> =
                MapEntry(pair.first, Result(pair.second))
            operator
            fun <K: Comparable<K>, V> invoke(key: K): MapEntry<K, V> =
                MapEntry(key, Result())
        }
    }


class Map<out K: Comparable<@UnsafeVariance K>, V>(
    private val delegate: Tree<MapEntry<@UnsafeVariance K, V>> = Tree()) {
    operator fun plus(entry: Pair<@UnsafeVariance K, V>): Map<K, V> =
        Map(delegate + MapEntry(entry))
    operator fun minus(key: @UnsafeVariance K): Map<K, V> =
        Map(delegate - MapEntry(key))
    fun contains(key: @UnsafeVariance K): Boolean =
        delegate.contains(MapEntry(key))
    operator fun get(key: @UnsafeVariance K): Result<MapEntry<@UnsafeVariance K, V>> =
        delegate[MapEntry(key)]
    fun isEmpty(): Boolean = delegate.isEmpty
    fun size() = delegate.size
    override fun toString() = delegate.toString()
    companion object {
        operator fun <K: Comparable<@UnsafeVariance K>, V> invoke():
            Map<K, V> = Map()
    }
}