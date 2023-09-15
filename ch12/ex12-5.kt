// 예제 12-5 입력에서 출력으로 흘러가는 완전한 프로그램

fun main(args: Array<String>) {
    val input = ConsoleReader()
    val rString = input.readString("Enter your name:").map { t -> t.first }

    val nameMessage = rString.map { "Hello, $it!" }
    nameMessage.forEach(::println, onFailure = { println(it.message)})
    val rInt = input.readInt("Enter your age:").map { t -> t.first }
    val ageMessage = rInt.map { "You look younger than $it!" }
    ageMessage.forEach(::println, onFailure =
        { println("Invalid age. Please enter an integer")})
}