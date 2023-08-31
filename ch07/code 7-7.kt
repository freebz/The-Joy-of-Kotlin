// 7.7 팩터리 함수 추가하기

operator fun <A> invoke(a: A? = null, message: String): Result<A>
operator fun <A> invoke(a: A? = null, p: (A) -> Boolean): Result<A>
operator fun <A> invoke(a: A? = null, message: String, p: (A) -> Boolean): Result<A>


// 연습문제 7-8

operator fun <A> invoke(a: A? = null, message: String): Result<A> =
    when (a) {
        null -> Failure(NullPointerException(message))
        else -> Success(a)
    }

operator fun <A> invoke(a: A? = null, p: (A) -> Boolean): Result<A> =
    when (a) {
        null -> Failure(NullPointerException())
        else -> when {
            p(a) -> Success(a)
            else -> Empty
        }
    }

operator fun <A> invoke(a: A? = null,
                        message: String,
                        p: (A) -> Boolean): Result<A> =
    when (a) {
        null -> Failure(NullPointerException())
        else -> when {
            p(a) -> Success(a)
            else -> Failure(IllegalStateException(
                "Argument $a does not match condition: $message"))
        }
    }