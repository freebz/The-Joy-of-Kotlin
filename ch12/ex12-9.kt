// 예제 12-9 IO를 사용해 명령형 프로그램 감싸기

private val buildMessage = { name: String ->
    IO.condition(name.isNotEmpty(), Lazy {
        IO("Hello, $name!").flatMap { Console.println(it) }
    })
}

fun program(f: (String) -> IO<Boolean>, title: String): IO<Unit> {
    return IO.sequence(Console.println(title),
        IO.doWhile(Console.readln(), f),
        Console.println("bye!")
    )
}

fun main(args: Array<String>) {
    val program = program(buildMessage, "Enter the names of the persons to welcome: ")
    program()
}