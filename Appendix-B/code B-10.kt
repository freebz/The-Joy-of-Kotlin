// B.5.3 추상화를 더 진행해서 코드를 간단하게 만들기

// 예제 05
fun getCharUsed(words: List<String>): Map<Map<Char, Int>, List<String>> =
    words.groupBy(::getCharMap)

fun getCharmap(s: String): Map<Char, Int> = s.fold(mapOf(), ::updateMap)

fun updateMap(map: Map<Char, Int>, char: Char): Map<Char, Int> =
    when {
        map.containsKey(char) -> map + Pair(char, map[char]!! + 1)
        else -> map + Pair(char, 1)
    }


// 예제 05 테스트
fun mapGenerator(min: Char = 'a', max: Char = 'z'): Gen<Map<Char, Int>> =
    Gen.list(Gen.choose(min.toInt(), max.toInt())
                .map(Int::toChar)).map(::makeMap)


// 예제 05 테스트
class UpdateMapTest: StringSpec() {
    private val random = Random()
    private val min = 'a'
    private val max = 'z'

    init {
        "getCharUsed" {
            forAll(mapGenerator()) { map: Map<Char, Int> ->
                (random.nextInt(max.toInt() - min.toInt())
                        + min.toInt()).toChar().let {
                    if (map.containsKey(it)) {
                        updateMap(map, it)[it] == map[it]!! + 1
                    } else {
                        updateMap(map, it)[it] == 1
                    } && updateMap(map, it) - it == map - it
                }
            }
        }
    }
}


// 예제 06 테스트
fun charGenerator(p: (Char) -> Boolean): Gen<Char> =
    Gen.choose(0, 255).map(Int::toChar).filter(p)


class UpdateMapTest: StringSpec() {
    init {
        "getCharused" {
            forAll(MapGenerator, charGenerator(Char::isLetterOrDigit)) {
                map: Map<Char, Int>, char ->
                    if (map.containsKey(char)) {
                        updateMap(map, char)[char] == map[char]!! + 1
                    } else {
                        updateMap(map, char)[char] == 1
                    }
                    && updateMap(map, char) - char == map - char
            }
        }
    }
}


// 예제 06 테스트
class UpdateMapTest: StringSpec() {
    init {
        "getCharUsed" {
            forAll(MapGenerator, charGenerator(Char::isLetterOrDigit)) {
                map: Map<Char, Int>, char ->
                    updateMap(map, char)[char] == map.getOrDefault(char, 0) + 1
                        && updateMap(map, char) - char == map - char
            }
        }
    }
}