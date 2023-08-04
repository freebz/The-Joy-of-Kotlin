// 4.4.4 자동 메모화 사용하기

fun double(x: Int) = x * 2


val cache = mutableMapOf<Int, Int>()

fun double(x: Int) =
    if (cache.containsKey(x)) {
        cache[x]
    } else {
        val result = x * 2
        cache.put(x, result)
        result
    }


val cache: MutableMap<Int, Int> = mutableMapOf()

fun double(x: Int) = cache.computeIfAbsent(x) { it * 2 }


val double: (Int) -> Int = { cache.computeIfAbsent(it) { it * 2 } }


object Doubler {
    private val cache: MutableMap<Int, Int> = mutableMapOf()
    fun double(x: Int) = cache.computeIfAbsent(x) { it * 2 }
}


val y = Doubler.double(x);


val f: (Int) -> Int = { it * 2 }
val g: (Int) -> Int = Memoizer.memoize(f)


val f: (Int) -> Int = { it * 2 }
val g: (Int) -> Int = Memoizer.memoize(f)
val h: (Int) -> Int = Memoizer.memoize(f)


// Call to nonmemoized function: result = 43, time = 1000
// First call to memoized function: result = 43, time = 1001
// Second call to nonmemoized function: result = 43, time = 0