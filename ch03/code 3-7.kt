// 3.2 코틀린 함수

// 3.2.1 함수를 데이터로 이해하기

// 3.2.2 데이터를 함수로 이해하기

// 3.2.3 객체 생성자를 함수로 사용하기

val person = Person("Elvis")


val elvis = Person("Elvis")
val theKing = Person("Elvis")


val elvis = Person("Elvis")
val theKing = Person("Elvis")

println(elvis == theKing) // "true"를 반환해야 함


data class Person(val name: String)