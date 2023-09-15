// 예제 12-12 스택 안전한 InvokeHelper 함수

tailrec fun invokeHelper(io: IO<A>): A = when (io) {
    is Return -> io.value
    is Suspend -> io.resume()
    else -> {
        val ct = io as Continue<A, A>
        val sub = ct.sub
        val f = ct.f
        when (sub) {
            is Return -> invokeHelper(f(sub.value))
            is Suspend -> invokeHelper(f(sub.resume()))
            else -> {
                val ct2 = sub as Continue<A, A>
                val sub2 = ct2.sub
                val f2 = ct2.f
                invokeHelper(sub2.flatMap { f2(it).flatMap(f) })
            }
        }
    }
}