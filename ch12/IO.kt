class IO<out A>(private val f: () -> A) {
    operator fun invoke() = f()

    companion object {
        val empty: IO<Unit> = IO { }
        operator fun <A> invoke(a: A): IO<A> = IO { a }
        fun <A, B> forever(ioa: IO<A>): IO<B> {
            return ioa.flatMap { { ioa.flatMap { { forever<A, B>(ioa) }() } }() }
        }
    }

    fun <B> map (g: (A) -> B): IO<B> = IO {
        g(this())
    }

    fun <B> flatMap (g: (A) -> IO<B>): IO<B> = IO {
        g(this())()
    }

    fun <A, B, C> map2(ioa: IO<A>, iob: IO<B>, f: (A) -> (B) -> C): IO<C> =
    ioa.flatMap { a ->
        iob.map { b ->
            f(a)(b)
        }
    }

    fun <A> repeat(n: Int, io: IO<A> ): IO<List<A>> {
        val stream: Stream<IO<A>> = Stream.fill(n, Lazy { io })
    
        val f: (A) -> (List<A>) -> List<A> =
            { a ->
                { la: List<A> -> List.cons(a, la) }
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
}