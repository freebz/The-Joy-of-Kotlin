// 9.4.5 예외 다루기

// 연습문제 9-9

fun <A> sequenceResult(lst: List<Lazy<A>>): Lazy<Result<List<A>>>


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
    val name4 = Lazy {
        println("Evaluating name4")
        throw IllegalStateException("Exception while evaluating name4")
    }
    val list1 = sequenceResult(List(name1, name2, name3))
    val list2 = sequenceResult(List(name1, name2, name3, name4))
    val defaultMessage = "No greetings when time is odd"
    val condition = Random(System.currentTimeMillis()).nextInt() % 2 == 0
    println(if (condition) list1() else defaultMessage)
    println(if (condition) list1() else defaultMessage)
    println(if (condition) list2() else defaultMessage)
    println(if (condition) list2() else defaultMessage)
}


// Evaluating name1
// Evaluating name2
// Evaluating name3
// Success([Mickey, Donald, Goofy, NIL])
// Success([Mickey, Donald, Goofy, NIL])
// Evaluating name4
// Failure(Exception while evaluating name4)
// Failure(Exception while evaluating name4)


// No greetings when time is odd
// No greetings when time is odd
// No greetings when time is odd
// No greetings when time is odd


import com.fpinkotlin.common.sequence

...

fun <A> sequenceResult(lst: List<Lazy<A>>): Lazy<Result<List<A>>> =
    Lazy { sequence(lst.map { Result.of(it) }) }


fun <A> sequenceResult2(lst: List<Lazy<A>>): Lazy<Result<List<A>>> =
    Lazy { traverse(lst) { Result.of(it) } }


fun <A> sequenceResult(lst: List<Lazy<A>>): Lazy<Result<List<A>>> =
    Lazy {
        lst.foldRight(Result(List())) { x: Lazy<A> ->
            { y: Result<List<A>> ->
                map2(Result.of(x), y) { a: A ->
                    { b: List<A> ->
                        b.cons(a)
                    }
                }
            }
        }
    }


fun <A> sequenceResult(lst: List<Lazy<A>>): Lazy<Result<List<A>>> =
    Lazy {
        val p = { r: Result<List<A>> -> r.map{false}.getOrElse(true) }
        lst.foldLeft(Result(List()), p) { y: Result<List<A>> ->
            { x: Lazy<A> ->
                map2(Result.of(x), y) { a: A ->
                    { b: List<A> ->
                        b.cons(a)
                    }
                }
            }
        }
    }