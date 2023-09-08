// 9.5.1 효과를 지연 계산으로 적용하기

val lazyString: Lazy<String> = ...
...
println(lazyString())


fun forEach(ef: (A) -> Unit) = ef(value)


if (condition) list1.forEach { println(it) } else println(defaultMessage)



// 연습문제 9-10

fun forEach(condition: Boolean, ifTrue: (A) -> Unit, ifFalse: (A) -> Unit) =
    if (condition) ifTrue(value) else ifFalse(value)


list1.forEach(condition, ::println, { println(defaultMessage)} )


fun forEach(condition: Boolean,
            ifTrue: (A) -> Unit,
            ifFalse: () -> Unit = {}) =
    if (condition)
        ifTrue(value)
    else
        ifFalse()

fun forEach(condition: Boolean,
            ifTrue: () -> Unit = {},
            ifFalse: (A) -> Unit) =
    if (condition)
        ifTrue()
    else
        ifFalse(value)

fun forEach(condition: Boolean,
            ifTrue: (A) -> Unit,
            ifFalse: (A) -> Unit) =
    if (condition)
        ifTrue(value)
    else
        ifFalse(value)


val printMessage: (Any) -> Unit = ::println
val printDefault: () -> Unit = { println(defaultMessage) }
list1.forEach(condition, printMessage, printDefault)


val printMessage: (Any) -> Unit = ::println
list1.forEach(condition, ifFalse = printMessage)