// B.5.2 커스텀 생성기 사용하기

// 예제 03
fun main(args: Array<String>) {
    val words = listOf("the", "act", "cat", "is", "bats",
                       "tabs", "tac", "aabc", "abbc", "abca")
    val map = getCharUsed(words)
    println(map)
}

fun getCharUsed(words: List<String>) = words.groupBy(::getCharMap)

fun getCharMap(s: String): Map<Char, Int> {
    val result = mutableMapOf<Char, Int>()
    for (i in 0 until s.length) {
        val ch = s[i]
        if (result.containsKey(ch)) {
            result.replace(ch, result[ch]!! + 1)
        } else {
            result[ch] = 1
        }
    }
    return result
}


{
    {t=1, h=1, e=1}=[the],
    {a=1, c=1, t=1}=[act, cat, tac],
    {i=1, s=1}=[is],
    {b=1, a=1, t=1, s=1}=[bats, tabs],
    {a=2, b=1, c=1}=[aabc, abca],
    {a=1, b=2, c=1}=[abbc]
}


// 예제 03 테스트
val stringGenerator: Gen<List<Pair<String, Map<Char, Int>>>> =
    Gen.list(Gen.list(Gen.choose(97, 122)))
        .map { initListList ->
            intListList.asSequence().map { intList ->
                intList.map { n ->
                    n.toChar()
                }
            }.map { charList ->
                Pair(String(charList.toCharArray()),
                    makeMap(charList))
            }.toList()
        }

fun makeMap(charList: List<Char>): Map<Char, Int> =
    charList.fold(mapOf(), ::updateMap)

fun updateMap(map: Map<Char, Int>, char: Char) = when {
    map.containsKey(char) -> map + Pair(char, map[char]!! + 1)
    else -> map + Pair(char, 1)
}


// 예제 03 테스트
import io.kotlintest.properties.forAll
import io.kotlintest.specs.StringSpec

class SameLetterStringKtTest: StringSpec() {
    init {
        "getCharUsed" {
            forAll(stringGenerator) {
                list: List<Pair<String, Map<Char, Int>>> ->
                    getCharused(list.map { it.first }).keys.toSet() ==
                        list.asSequence().map { it.second }.toSet()
            }
        }
    }
}


// 예제 04 테스트
class StringGenerator(private val maxList: Int,
                      private val maString: Int) :
                        Gen<List<Pair<String, Map<Char, Int>>>> {
    override
    fun constants(): Iterable<List<Pair<String, Map<Char, Int>>>> =
        listOf(listOf(Pair("", mapOf())))

    override
    fun random(): Sequence<List<Pair<String, Map<Char, Int>>>> =
        Random().let { random ->
            generateSequence {
                (0 until random.nextInt(maxList)).map {
                    (0 until random.nextInt(maxString))
                        .fold(Pair("", mapOf<Char, Int>())) { pair, _ ->
                            (random.nextInt(122 - 96) + 96).toChar().let { char ->
                                Pair("${pair.first}$char", updateMap(pair.second, char))
                            }
                        }
                }
            }
        }
}


class ListGenerator<T>(private val gen: Gen<T>,
                       private val maxLength: Int) : Gen<List<T>> {
    private val random = Random()

    override fun constants(): Iterable<List<T>> =
                        listOf(gen.constants().toList())

    override fun random(): Sequence<List<T>> = generateSequence {
        val size = random.nextInt(maxLength)
        gen.random().take(size).toList()
    }

    override fun shrinker() = ListShrinker<T>()
}


fun stringGenerator(maxList: Int,
                    maxString: Int): Gen<List<Pair<String, Map<Char, Int>>>> =
        ListGenerator(ListGenerator(Gen.choose(32, 127), maxString), maxList)
    .map { intListList ->
        intListList.asSequence().map { intList ->
            intList.map { n ->
                n.toChar()
            }
        }.map { charList ->
            Pair(String(charList.toCharArray()), makeMap(charList))
        }.toList()
    }


class SameLettersStringKtTest: StringSpec() {
    init {
        "getCharUsed" {
            forAll(stringGenerator(100, 100)) {
                list: List<Pair<String, Map<Char, Int>>> ->
                    getCharUsed(list.map { it.first }).keys.toSet() ==
                        list.asSequence().map { it.second }.toSet()
            }
        }
    }
}