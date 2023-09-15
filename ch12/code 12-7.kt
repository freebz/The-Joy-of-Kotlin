// 12.4.4 IO로 입력 다루기

class IO<out A>(private val f: () -> A) {
    operator fun invoke() = f()

    companion object {
        val empty: IO<Unit> = IO { }
        operator fun <A> invoke(a: A): IO<A> = IO { a }
    }
}



// 연습문제 12-5

object Console {
    fun readln(): IO<String> = TODO("")
    fun println(o: Any): IO<Unit> = TODO("")
    fun print(o: Any): IO<Unit> = TODO("")
}


fun println(o: Any): IO<Unit> = IO {
    kotlin.io.println(o.toString())
}

fun print(o: Any): IO<Unit> = IO {
    kotlin.io.print(o.toString())
}


private val br = BufferedReader(InputStreamReader(System.`in`))

fun readln(): IO<String> = IO {
    try {
        br.readLine()
    } catch (e: IOException) {
        throw IllegalStateException(e)
    }
}



// 연습문제 12-6

fun <B> map (g: (A) -> B): IO<B> = IO {
    g(this())
}


fun main(args: Array<String>) {
    val script = sayHello()
    script()
}

private fun sayHello(): IO<Unit> = Console.print("Enter your name: ")
    .map { Console.readln()() }
    .map { buildMessage(it) }
    .map { Console.println(it)() }

private fun buildMessage(name: String): String = "Hello, $name!"



// 연습문제 12-7

fun <B> flatMap (g: (A) -> IO<B>): IO<B> = IO {
    g(this())()
}


fun main(args: Array<String>) {
    val script = sayHello()
    script()
}

private fun sayHello(): IO<Unit> = Console.print("Enter your name; ")
    .flatMap { Console.readln() }
    .map { buildMessage(it) }
    .flatMap { Console.println(it) }

private fun buildMessage(name: String): String = "Hello, $name!"