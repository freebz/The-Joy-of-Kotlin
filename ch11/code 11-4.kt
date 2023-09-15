// 11.2.3 비교 불가능한 키를 사용하는 Map 다루기

// 연습문제 11-4

class MapEntry<K: Any, V> private constructor(val key: K, val value: Result<V>):
    Comparable<MapEntry<K, V>> {
        override fun compareTo(other: MapEntry<K, V>): Int =
            hashCode().compareTo(other.hashCode())
        ...
    }


private val delegate: Tree<MapEntry<Int, List<Pair<K, V>>>> = Tree()


private fun getAll(key: @UnsafeVariance K): Result<List<Pair<K, V>>> =
    delegate[MapEntry(key.hashCode)]
        .flatMap { x ->
            x.value.map { lt ->
                lt.map { t -> t}
            }
        }


operator fun plus(entry: Pair<@UnsafeVariance K, V>): Map<K, V> {
    val list = getAll(entry.first).map { lt ->
        lt.foldLeft(List(entry)) { lst ->
                if (pair.first == entry.first) lst else lst.cons(pair)
            }
        }
    }.getOrElse { List(entry) }
    return Map(delegate + MapEntry.of(entry.first.hashCode(), list))
}


operator fun minus(key: @UnsafeVariance K): Map<K, V> {
    val list = getAll(key).map { lt ->
        lt.foldLeft(List()) { lst: List<Pair<K, V>> ->
            { pair ->
                if (pair.first == key) lst else lst.cons(pair)
            }
        }
    }.getOrElse { List() }
    return when {
        list.isEmpty() -> Map(delegate - MapEntry(key.hashCode()))
        else -> Map(delegate + MapEntry.of(key.hashCode(), list))
    }
}


fun contains(key: @UnsafeVariance K): Boolean =
    getAll(key).map { list ->
        list.exists { pair ->
            pair.first == key
        }
    }.getOrElse(false)


fun get(key: @UnsafeVariance K): Result<Pair<K, V>> =
    getAll(key).flatMap { list ->
        list.filter { pair ->
            pair.first == key
        }.headSafe()
    }