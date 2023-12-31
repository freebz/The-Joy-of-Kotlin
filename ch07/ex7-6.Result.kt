// 예제 7-6 오류와 선택적 데이터를 처리할 수 있는 새로운 Result 클래스

sealed class Result<out A>: Serializable {
    abstract fun <B> map(f: (A) -> B): Result<B>
    abstract fun <B> flatMap(f: (A) -> Result<B>): Result<B>

    fun getOrElse(defaultValue: @UnsafeVariance A): A = when (this) {
        is Success -> this.value
        else -> defaultValue
    }

    fun getOrElse(defaultValue: () -> @UnsafeVariance A): A = when (this) {
        is Success -> this.value
        else -> defaultValue()
    }

    fun orElse(defaultValue: () -> Result<@UnsafeVariance A>): Result<A> = when (this) {
        is Success -> this
        else -> try {
                defaultValue()
            } catch (e: RuntimeException) {
                Result.failure<A>(e)
            } catch (e: Exception) {
                Result.failure<A>(RuntimeException(e))
            }
    }

    internal object Empty: Result<Nothing>() {
        override fun <B> map(f: (Nothing) -> B): Result<B> = Empty
        override fun <B> flatMap(f: (Nothing) -> Result<B>): Result<B> = Empty
        override fun toString(): String = "Empty"
    }

    internal class Failure<out A>(private val exception: RuntimeException): Result<A>() {
        override fun <B> map(f: (A) -> B): Result<B> = Failure(exception)
        override fun <B> flatMap(f: (A) -> Result<B>): Result<B> = Failure(exception)
        override fun toString(): String = "Failure(${exception.message})"
    }

    internal class Success<out A>(internal val value: A) : Result<A>() {
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
        override fun toString(): String = "Success($value)"
    }

    companion object {
        operator fun <A> invoke(a: A? = null): Result<A> = when (a) {
            null -> Failure(NullPointerException())
            else -> Success(a)
        }
        operator fun <A> invoke(): Result<A> = Empty
        fun <A> failure(message: String): Result<A> =
            Failure(IllegalStateException(message))
        fun <A> failure(exception: RuntimeException): Result<A> =
            Failure(exception)
        fun <A> failure(exception: Exception): Result<A> =
            Failure(IllegalStateException(exception))
    }
}