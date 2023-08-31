// 7.2 Either 타입

fun <A: Comparable<A>> max(list: List<A>): Either<String, A> = when(list) {
    is List.Nil -> Either.left("max called on an empty list")
    is List.Cons -> Either.right(list.foldLeft(list.head) { x -> { y ->
        if (x.compareTo(y) == 0) x else y
        }
    })
}



// 연습문제 7-1

// Either
abstract fun <B> map(f: (A) -> B): Either<E, B>

// Left
override fun <B> map(f: (A) -> B): Either<E, B> = Left(value)

// Right
override fun <B> map(f: (A) -> B): Either<E, B> = Right(f(value))



// 연습문제 7-2

// Either
abstract fun <B> flatMap(f: (A) -> Either<E, B>): Either<E, B>

// Left
override fun <B> flatMap(f: (A) -> Either<E, B>): Either<E, B> = Left(value)

// Right
override fun <B> flatMap(f: (A) -> Either<E, B>): Either<E, B> = f(value)



// 연습문제 7-3

fun getOrElse(defaultValue: () -> @UnsafeVariance A): A

fun orElse(defaultValue: () -> Either<E, @UnsafeVariance A>): Either<E, A>

// Either
fun getOrElse(defaultValue: () -> @UnsafeVariance A): A = when (this) {
    is Right -> this.value
    is Left  -> defaultValue()
}

// Either
fun orElse(defaultValue: () -> Either<E, @UnsafeVariance A>): Either<E, A> =
    map { this }.getOrElse(defaultValue)