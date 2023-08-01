// 2.2.1 예제 코드 간결하게 만들기

class Person constructor(name: String) {
    val name: String = name
}


class Person constructor(val name: String) {

}


class Person(val name: String)


class Person(val name: String, val registered: Instant)