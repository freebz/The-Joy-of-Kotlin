// 12.4 완전히 함수형인 입출력

// 12.4.1 입출력을 완전히 함수형으로 만들기

// 12.4.2 순수 함수형 I/O 구현하기

fun sayHello(name: String) = println("Hello, $name!")

fun sayHello(name: String): () -> Unit = { println("Hello, $name!") }

fun main(args: Array<String>) {
    val program = sayHello("Georges")
    program()
}


class IO(private val f: () -> Unit) {
    operator fun invoke() = f()
}


fun show(message: String): IO = IO { println(message) }

fun <A> toString(rd: Result<A>): String =
    rd.map { it.toString() }.getOrElse { rd.toString() }

fun inverse(i: Int): Result<Double> = when (i) {
    0 -> Result.failure("Div by 0")
    else -> Result(1.0 / i)
}


val computation: IO = show(toString(inverse(3)))

computation()