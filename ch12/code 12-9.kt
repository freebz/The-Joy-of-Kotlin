// 12.4.6 IO 타입을 스택 안전하게 만들기

// 연습문제 12-9

fun <A, B> forever(ioa: IO<A>): IO<B>


fun <A, B> forever(ioa: IO<A>): IO<B> {
    val t: () -> IO<B> = { forever(ioa) }
    return ioa.flatMap { t() }
}


fun main(args: Array<String>) {
    val program = IO.forever<String, String>(IO { "Hi again!" })
        .flatMap { Console.println(it) }
    program()
}


IO.forever<Unit, String>(Console.println("Hi again!"))()


fun <A, B> forever(ioa: IO<A>): IO<B> {
    return ioa.flatMap { { forever(ioa) }() }
}


fun <A, B> forever(ioa: IO<A>): IO<B> {
    return ioa.flatMap { { ioa.flatMap { { forever<A, B>(ioa) }() } }() }
}