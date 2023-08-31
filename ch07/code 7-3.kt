// 7.3 Result 타입

// 연습문제 7-4

// Result
fun <B> map(f: (A) -> B): Result<B>
fun <B> flatMap(f: (A) -> Result<B>): Result<B>
fun <A> getOrElse(defaultValue: A): A
fun <A> orElse(defaultValue: () -> Result<A>): Result<A>


// Success
override fun <B> map(f: (A) -> B): Result<B> = try {
    Success(f(value))
} catch (e: RuntimeException) {
    Failure(e)
} catch (e: Exception) {
    Failure(RuntimeException(e))
}

override fun <B> flatMap(f: (A) -> Result<B>): Result<B> = try {
    f(value)
} catch (e: RuntimeException) {
    Failure(e)
} catch (e: Exception) {
    Failure(RuntimeException(e))
}


// Failure
override fun <B> map(f: (A) -> B): Result<B> =
    Failure(exception)

override fun <B> flatMap(f: (A) -> Result<B>): Result<B> =
    Failure(exception)


fun getOrElse(defaultValue: @UnsafeVariance A): A = when (this) {
    is Success -> this.value
    is Failure -> defaultValue
}


fun orElse(defaultValue: () -> Result<@UnsafeVariance A>): Result<A> =
    when (this) {
        is Success -> this
        is Failure -> try {
                defaultValue()
            } catch (e: RuntimeException) {
                Result.failure<A>(e)
            } catch (e: Exception) {
                Result.failure<A>(RuntimeException(e))
            }
    }


fun getOrElse(defaultValue: () -> A): A