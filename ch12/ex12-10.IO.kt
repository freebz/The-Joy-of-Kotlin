// 예제 12-10 IO를 스택안전하게 만들 때 필요한 3가지 클래스

sealed class IO<out A> {
    internal
    class Return<out A>(val value: A): IO<A>()

    internal
    class Suspend<out A>(val resume: () -> A): IO<A>()

    internal
    class Continue<A, out B>(val sub: IO<A>,
        val f: (A) -> IO<B>): IO<A>()
}