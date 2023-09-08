// 9.4.1 지연 값 합성하기

fun or(a: Lazy<Boolean>, b: Lazy<Boolean>): Boolean = if (a()) true else b()


fun constructMessage(greetings: String,
                     name: String): String = "$greetings, $name!"

constructMessage("Hello", "Mickey")


val greetings = Lazy {
    println("Evaluating greetings")
    "Hello"
}

val name: Lazy<String> = Lazy {
    println("computing name")
    "Mickey"
}

import java.util.Random

val message = constructMessage(greetings, name)
val condition = Random(System.currentTimeMillis()).nextInt() % 2 == 0
println(
    if (condition) <compose and print the message>
    else "No greetings when time is odd")


fun constructMessage(greetings: Lazy<String>,
    name: Lazy<String>): String = "${greetings()}, ${name()}!"



// 연습문제 9-2

fun constructMessage(greetings: Lazy<String>,
    name: Lazy<String>): Lazy<String> =
        Lazy { "${greetings()}, ${name()}!" }

fun main(args: Array<String>) {
    val greetings = Lazy {
        println("Evaluating greetings")
        "Hello"
    }
    val name1: Lazy<String> = Lazy {
        println("Evaluating name")
        "Mickey"
    }
    val name2: Lazy<String> = Lazy {
        println("Evaluating name")
        "Donald"
    }
    val defaultMessage = Lazy {
        println("Evaluating default message")
        "No greetings when time is odd"
    }
    val message1 = constructMessage(greetings, name1)
    val message2 = constructMessage(greetings, name2)
    val condition = Random(System.currentTimeMillis()).nextInt() % 2 == 0
    println(if (condition) message1() else defaultMessage())
    println(if (condition) message1() else defaultMessage())
    println(if (condition) message2() else defaultMessage())
}


// Evaluating greetings
// Evaluating name
// Hello, Mickey!
// Hello, Mickey!
// Evaluating name
// Hello, Donald!


// Evaluating default message
// No greetings when time is odd
// No greetings when time is odd
// No greetings when time is odd



// 연습문제 9-3

val constructMessage: (Lazy<String>) -> (Lazy<String>) -> Lazy<String> =
    { greetings ->
        { name ->
            Lazy { "${greetings()}, ${name()}!"}
        }
    }

fun main(args: Array<String>) {
    val greetings = Lazy {
        println("Evaluating greetings")
        "Hello"
    }
    val name1: Lazy<String> = Lazy {
        println("Evaluating name1")
        "Mickey"
    }
    val name2: Lazy<String> = Lazy {
        println("Evaluating name2")
        "Donald"
    }
    val defaultMessage = Lazy {
        println("Evaluating default message")
        "No greetings when time is odd"
    }
    val greetingString = constructMessage(greetings)
    val message1 = greetingString(name1)
    val message2 = greetingString(name2)
    val condition = Random(System.currentTimeMillis()).nextInt() % 2 == 0
    println(if (condition) message1() else defaultMessage())
    println(if (condition) message2() else defaultMessage())
}