// 2.2.4 프로퍼티 생성자 오버로드하기

class Person(val name: String, val registered: Instant = Instant.now())


class Person(val name: String, val registered: Instant = Instant.now()) {
    constructor(name: Name) : this(name.toString()) {
        // 생성자 구현 추가 기능
    }
}


// 비공개 생성자와 프로퍼티

class Person private constructor(val name: String)


// 접근자와 프로퍼티

val person = Person("Bob")
...
println(person.name) // 게터 호출