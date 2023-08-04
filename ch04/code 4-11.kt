// 4.3.2 리스트에 대한 재귀 추상화하기

fun sum(list: List<Int>): Int =
    if (list.isEmpty())
        0
    else
        list.head() + sum(list.tail())


fun <T> makeString(list: List<T>, delim: String): String =
    when {
        list.isEmpty() -> ""
        list.tail().isEmpty() ->
            "${list.head()}${makeString(list.tail(), delim)}"
        else -> "${list.head()}$delim${makeString(list.tail(), delim)}"
    }



// 연습문제 4-4

fun <T> makeString(list: List<T>, delim: String): String {
    tailrec fun makeString_(list: List<T>, acc: String): String = when {
        list.isEmpty() -> acc
        acc.isEmpty() -> makeString_(list.tail(), "${list.head()}")
        else -> makeString_(list.tail(), "$acc$delim${list.head()}")
    }
    return makeString_(list, "")
}



// 연습문제 4-5

fun <T, U> foldLeft(list: List<T>, z: U, f: (U, T) -> U): U {
    tailrec fun foldLeft(list: List<T>, acc: U): U =
        if (list.isEmpty())
            acc
        else
            foldLeft(list.tail(), f(acc, list.head()))
    return foldLeft(list, z)
}

fun sum(list: List<Int>) = foldLeft(list, 0, Int::plus)

fun string(list: List<Char>) = foldLeft(list, "", String::plus)

fun <T> makeString(list: List<T>, delim: String) =
    foldLeft(list, "") { s, t -> if (s.isEmpty()) "$t" else "$s$delim$t" }


fun toString(list: List<Char>): String =
    if (list.isEmpty())
        ""
    else
        prepend(list.head(), string(list.tail()))



// 연습문제 4-6

fun <T, U> foldRight(list: List<T>, identity: U, f: (T, U) -> U): U =

fun <T, U> foldRight(list: List<T>, identity: U, f: (T, U) -> U): U =
    if (list.isEmpty())
        identity

fun <T, U> foldRight(list: List<T>, identity: U, f: (T, U) -> U): U =
    if (list.isEmpty())
        identity
    else
        f(list.head(), foldRight(list.tail(), identity, f))


fun toString(list: List<Char>): String =
    foldRight(list, "", { c, s -> prepend(c, s) })


fun toString(list: List<Char>): String =
    foldRight(list, "") { c, s -> prepend(c, s) }