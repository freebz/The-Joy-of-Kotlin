// B.5.1 커스텀 생성기 작성하기

interface Gen<T> {
    fun constants(): Iterable<T>
    fun random(): Sequence<T>
}


data class Person(val name: String, val age: Int)

val getPerson: Gen<Person> =
    Gen.bind(Gen.string(), Gen.shoose(1, 50))
        { name, age -> Person(name, age) }