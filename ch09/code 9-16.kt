// 9.6.3 스트림을 구체적인 문제에 적용하기

// 연습문제 9-28

fun fibs(): Stream<Int> = Stream.iterate(Pair(1, 1)) {
    x -> Pair(x.second, x.first + x.second)
}.map({ x -> x.first })


fun fibs(): Stream<Int> = Stream.iterate(Pair(1, 1)) {
    (x, y) -> Pair(y, x + y)
}.map { it.first }



// 연습문제 9-29

fun <A, S> unfold(z: S, f: (S) -> Result<Pair<A, S>>): Stream<A>

// companion object
fun <A, S> unfold(z: S, f: (S) -> Result<Pair<A, S>>): Stream<A> = f(z).map {
    x -> Stream.cons(Lazy { x.first }, Lazy { unfold(x.second, f) })
}.getOrElse(Stream.Empty)

// companion object
fun <A, S> unfold(z: S, f: (S) -> Result<Pair<A, S>>): Stream<A> = f(z).map {
    (a, s) -> Stream.cons(Lazy { a }, Lazy { unfold(s, f) })
}.getOrElse(Stream.Empty)

// Stream
fun from(n: Int): Stream<Int> = unfold(n) { x -> Result(Pair(x, x + 1)) }


fun fibs(): Stream<Int> = Stream.unfold(Pair(1, 1)) {
    x -> Result(Pair(x.first, Pair(x.second, x.first + x.second)))
}



// 연습문제 9-30

fun filter2(p: (A) -> Boolean): Stream<A> =
    dropWhile { x -> !p(x) }.let { stream ->
        when (stream) {
            is Empty -> stream
            is Cons -> stream.head().map({ a ->
                cons(Lazy { a }, Lazy { stream.tl().filter(p) })
            }).getOrElse(Empty)
        }
    }


fun filter(p: (A) -> Boolean): Stream<A> =
    dropWhile { x -> !p(x) }.let { stream ->
        when (stream) {
            is Empty -> stream
            is Cons -> cons(stream.hd, Lazy { stream.tl().filter(p) })
        }
    }