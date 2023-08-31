// 7.6 실패 매핑하기

// 연습문제 7-7

// Result
abstract fun mapFailure(message: String): Result<A>

// Empty
override fun mapFailure(message: String): Result<Nothing> = this

// Success
override fun mapFailure(message: String): Result<A> = this

// Failure
override fun mapFailure(message: String): Result<A> =
    Failure(RuntimeException(message, exception))