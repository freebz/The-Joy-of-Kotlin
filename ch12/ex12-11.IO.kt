// 예제 12-11 스택 안전한 IO 버전에서 바뀐 부분

sealed class IO<out A> {
    operator fun invoke(): A = invoke(this)
    
    operator fun invoke(io: IO<@UnsafeVariance A>): A {
        tailrec fun invokeHelper(io: IO<A>): A =
            when (io) {
                ...
            }
        return invokeHelper(io)
    }

    fun <B> map(f: (A) -> B): IO<B> = flatMap { Return(f(it)) }
    fun <B> flatMap(f: (A) -> IO<B>): IO<B> = Continue(this, f) as IO<B>

    class IORef<A>(private var value: A) {
        fun set(a: A): IO<A> {
            value = a
            return unit(a)
        }
        fun get(): IO<A> = unit(value)
        fun modify(f: (A) -> A): IO<A> = get().flatMap({ a -> set(f(a)) })
    }

    internal class Return<out A>(val value: A): IO<A>()
    internal class Suspend<out A>(val resume: () -> A): IO<A>()
    internal class Continue<A, out B>(val sub: IO<A>,
        val f: (A) -> IO<B>): IO<A>()

    companion object {
        val empty: IO<Unit> = IO.Suspend { Unit }
        internal fun <A> unit(a: A): IO<A> = IO.Suspend { a }
        // 나머지 부분은 생략
    }
}