// 2.2.6 데이터 객체 구조 분해하기

data class Person(val name: String, val registered: Instant = Instant.now())

fun show(persons: List<Person>) {
    for ((name, date) in persons)
        println(name + "'s registration date: " + date)
}

fun main(args: Array<String>) {
    val persons = listOf(Person("Mike"), Person("Paul"))
    show(persons)
}


fun show(persons: List<Person>) {
    for (person in persons)
        println(person.component1() + "'s registration date: " + person.component2())
}