// 9.6.1 스트림 접기

// 연습문제 9-20

// Stream
abstract fun <B> foldRight(z: Lazy<B>,
                           f: (A) -> (Lazy<B>) -> B): B

// Empty
override fun <B> foldRight(z: Lazy<B>,
                           f: (Nothing) -> (Lazy<B>) -> B): B = z()

// Cons
override fun <B> foldRight(z: Lazy<B>,
                           f: (A) -> (Lazy<B>) -> B): B =
                               f(hd())(Lazy { tl().foldRight(z, f) })



// 연습문제 9-21

// Stream
fun takeWhildViaFoldRight(p: (A) -> Boolean): Stream<A> =
    foldRight(Lazy { Empty }, { a ->
        { b: Lazy<Stream<A>> -> 
            if (p(a))
                cons(Lazy { a }, b)
            else
                Empty
        }
    })



// 연습문제 9-22

fun headSafeViaFoldRight(): Result<A> =
    foldRight(Lazy { Result<A>() }, { a -> { Result(a) } })



// 연습문제 9-23

fun <B> map(f: (A) -> B): Stream<B> =
    foldRight(Lazy { Empty }, { a ->
        { b: Lazy<Stream<B>> ->
            cons(Lazy { f(a) }, b)
        }
    })



// 연습문제 9-24

fun filter(p: (A) -> Boolean): Stream<A> =
    foldRight(Lazy { Empty }, { a ->
        { b: Lazy<Stream<A>> ->
            if (p(a)) cons(Lazy { a }, b) else b()
        }
    })



// 연습문제 9-25

fun append(stream2: Lazy<Stream<@UnsafeVariance A>>): Stream<A> =
    this.foldRight(stream2) { a: A ->
        { b: Lazy<Stream<A>> ->
            Stream.cons(Lazy { a }, b)
        }
    }



// 연습문제 9-26

fun <B> flatMap(f: (A) -> Stream<B>): Stream<B> =
    foldRight(Lazy { Empty as Stream<B> }, { a ->
        { b: Lazy<Stream<B>> ->
            f(a).append(b)
        }
    })