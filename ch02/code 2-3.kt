// 2.1.3 지연 초기화 이해하기

val name: String? = null
...
name = getName()


var name: String = "초기화_안_됨"
...
name = getName()


val name: String by lazy { getName() }


val name: String by lazy(::getName)


fun main(args: Array<String>) {
    val name: String by lazy { getName() }
    println("안녕1")
    val name2: String by lazy { name }
    println("안녕2")
    println(name)
    println(name2)
    println(name)
    println(name2)
}

fun getName(): String {
    println("이름 계산 중...")
    return "Frank 현석 Oh"
}


// 안녕1
// 안녕2
// 이름 계산 중...
// Frank 현석 Oh
// Frank 현석 Oh
// Frank 현석 Oh
// Frank 현석 Oh


lateinit var name: String
...
name = getName()