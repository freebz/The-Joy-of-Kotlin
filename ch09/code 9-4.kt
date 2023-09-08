// 9.4 지연 계산 구현

() -> Int

val x: () -> Int = { 5 }


fun main(args: Array<String>) {
    val first = { true }
    val second = { throw IllegalStateException() }
    println(first() || second())
    println(or(first, second))
}

fun or(a: () -> Boolean, b: () -> Boolean): Boolean = if (a()) true else b()

// true
// true


fun main(args: Array<String>) {
    fun first() = true
    fun second(): Boolean = throw IllegalStateException()
    println(first() || second())
    println(or(::first, ::second))
}

fun or(a: () -> Boolean, b: () -> Boolean): Boolean = if (a()) true else b()



// 연습문제 9-1

fun main(args: Array<String>) {
    val first = Lazy {
        println("Evaluating first")
        true
    }
    val second = Lazy {
        println("Evaluating second")
        throw IllegalStateException()
    }
    println(first() || second())
    println(first() || second())
    println(or(first, second))
}

fun or(a: Lazy<Boolean>, b: Lazy<Boolean>): Boolean = if (a()) true else b()

// Evaluating first
// true
// true
// true


class Lazy<out A>(function: () -> A): () -> A {
    private val value: A by lazy(function)
    operator override fun invoke(): A = value
}