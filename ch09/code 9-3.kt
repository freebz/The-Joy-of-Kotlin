// 9.3 코틀린과 지연 계산

val first: Boolean by Delegate()

operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean

operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean)

val first: Boolean by lazy { ... }


class Lazy {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean = ...
}

val first: Boolean by Lazy()


fun main(args: Array<String>) {
    val first: Boolean by lazy { true }
    val second: Boolean by lazy { throw IllegalStateException() }
    println(first || second)
    println(or(first, second))
}

fun or(a: Boolean, b: Boolean): Boolean = if (a) true else b


// true
// Exception in thread "main" java.lang.IllegalStateException