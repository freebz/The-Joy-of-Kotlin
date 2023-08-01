// 2.2.2 인터페이스를 구현하거나 클래스를 확장하기

class Person(
    val name: String,
    val registered: Instant
) : Serializable,
    Comparable<Person> {
    override fun compareTo(other: Person): Int {
        ...
    }
}


class Member(name: String, registered: Instant) : Person(name, registered)


open class Person(val name: String, val registered: Instant)