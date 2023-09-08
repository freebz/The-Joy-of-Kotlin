// 9.4.4 Lazy와 List 합성하기

// 연습문제 9-8

fun <A> sequence(lst: List<Lazy<A>>): Lazy<List<A>>


fun <A> sequence(lst: List<Lazy<A>>): Lazy<List<A>> =
    Lazy { lst.map { it() } }


fun main(args: Array<String>) {
    val name1: Lazy<String> = Lazy {
        println("Evaluating name1")
        "Mickey"
    }
    val name2: Lazy<String> = Lazy {
        println("Evaluating name2")
        "Donald"
    }
    val name3 = Lazy {
        println("Evaluating name3")
        "Goofy"
    }
    val list = sequence(List(name1, name2, name3))
    val defaultMessage = "No greetings when time is odd"
    val condition = Random(System.currentTimeMillis()).nextInt() % 2 == 0
    println(if (condition) list() else defaultMessage)
    println(if (condition) list() else defaultMessage)
}


// Evaluating name1
// Evaluating name2
// Evaluating name3
// [Mickey, Donald, Goofy, NIL]
// [Mickey, Donald, Goofy, NIL]


// No greetings when time is odd
// No greetings when time is odd