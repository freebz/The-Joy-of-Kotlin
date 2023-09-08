// 9.4.3 Lazy 값 매핑하고 매핑 후 펼치기

// 연습문제 9-6

// Lazy
fun <B> map(f: (A) -> B): Lazy<B> = Lazy { f(value) }


fun main(args: Array<String>) {
    val greets: (String) -> String = { "Hello, $it!" }
    val name: Lazy<String> = Lazy {
        println("Evaluating name")
        "Mickey"
    }
    val defaultMessage = Lazy {
        println("Evaluating default message")
        "No greetings when time is odd"
    }
    val message = name.map(greets)
    val condition = Random(System.currentTimeMillis()).nextInt() % 2 == 0
    println(if (condition) message() else defaultMessage())
    println(if (condition) message() else defaultMessage())
}


// Evaluating name
// Hello, Mickey!
// Hello, Mickey!


// Evaluating default message
// No greetings when time is odd
// No greetings when time is odd



// 연습문제 9-7

fun <B> flatMap(f: (A) -> Lazy<B>): Lazy<B> = Lazy { f(value)() }


import java.util.Locale

fun getGreetings(locale: Locale): String {
    println("Evaluating greetings")
    return when(locale) {
        Locale.KOREA -> "안녕"
        else -> "Hello"
    }
}


fun main(args: Array<String>) {

    // getGreetings은 "Evaluating greetings"를 콘솔에 찍는 부수 효과가 있으면서
    // 비용이 많이 드는 함수라고 가정하자.
    val greetings: Lazy<String> = Lazy { getGreetings(Locale.US) }
    val flatGreets: (String) -> Lazy<String> =
        { name -> greetings.map { "$it, $name!" } }
    val name: Lazy<String> = Lazy {
        println("computing name")
        "Mickey"
    }
    val defaultMessage = Lazy {
        println("Evaluating default message")
        "No greetings when time is odd"
    }
    val message = name.flatMap(flatGreets)
    val condition = Random(System.currentTimeMillis()).nextInt() % 2 == 0
    println(if (condition) message() else defaultMessage())
    println(if (condition) message() else defaultMessage())
}


// computing name
// Evaluating greetings
// Hello, Mickey!
// Hello, Mickey!


// Evaluating default message
// No greetings when time is odd
// No greetings when time is odd