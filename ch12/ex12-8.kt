// 예제 12-8 ScriptReader를 사용해 데이터 입력하기

fun readPersonsFromScript(vararg commands: String): List<Person> =
    Stream.unfold(ScriptReader(*commands), ::person).toList()

fun main(args: Array<String>) {
    readPersonsFromScript("1", "Mickey", "Mouse",
                          "2", "Minnie", "Mouse",
                          "3", "Donald", "Duck").forEach(::println)
}