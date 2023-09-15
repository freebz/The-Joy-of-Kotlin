// 12.4.5 IO 타입 확장하기

// 연습문제 12-8

fun <A> repeat(n: Int, io: IO<A> ): IO<List<A>>

// Stream
fun <A> fill(n: Int, elem: Lazy<A>): Stream<A> {
    tailrec
    fun <A> fill(acc: Stream<A>, n: Int, elem: Lazy<A>): Stream<A> =
        when {
            n <= 0 -> acc
            else -> fill(Cons(elem, Lazy { acc }), n - 1, elem)
        }
    return fill(Empty, n, elem)
}


fun <A, B, C> map2(ioa: IO<A>, iob: IO<B>, f: (A) -> (B) -> C): IO<C>

// IO
fun <A, B, C> map2(ioa: IO<A>, iob: IO<B>, f: (A) -> (B) -> C): IO<C> =
    ioa.flatMap { a ->
        iob.map { b ->
            f(a)(b)
        }
    }

// IO
fun <A> repeat(n: Int, io: IO<A> ): IO<List<A>> =
    Stream.fill(n, Lazy { io })
        .foldRight( Lazy { IO { List<A>() } }) { ioa ->
            { sioLa ->
                map2(ioa, sioLa()) { a ->
                    { la: List<A> -> cons(a, la) }
                }
            }
        }

// IO
fun <A> repeat(n: Int, io: IO<A> ): IO<List<A>> {
    val stream: Stream<IO<A>> = Stream.fill(n, Lazy { io })

    val f: (A) -> (List<A>) -> List<A> =
        { a ->
            { la: List<A> -> cons(a, la) }
        }

    val g: (IO<A>) -> (Lazy<IO<List<A>>>) -> IO<List<A>> =
        { ioa ->
            { sioLa ->
                map2(ioa, sioLa(), f)
            }
        }

    val z: Lazy<IO<List<A>>> = Lazy { IO { List<A>() } }
    return stream.foldRight(z, g)
}


val program = IO.repeat(3, sayHello())


fun sayHello(n: Int) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    for (i in 0 until n) {
        print("Enter your name: ")
        val name = br.readLine()
        println(buildMessage(name))
    }
}