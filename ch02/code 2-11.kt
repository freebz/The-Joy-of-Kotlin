// 2.2.7 정적 메서드 구현하기

data class Person(val name: String, val registered: Instant = Instant.now()) {
    companion object {
        fun create(xml: String): Person {
            TODO("Write an implementation creating " +
                 "a Person from an xml string")
        }
    }
}


Person.create(someXmlString)


Person.Companion.create(someXmlString)