// 5.6.1 객체 표기법의 이점 살리기

class List<A> {
    ...
}

fun <A> drop(aList: List<A>, n: Int): List<A> {
    tailrec fun drop_(list: List<A>, n: Int): List<A> = when (list) {
        List.Nil -> list
        is List.Cons -> if (n <= 0) list else drop_(list.tail, n - 1)
    }
    return drop_(aList, n)
}


class List<A> {
    ...
}

tailrec fun drop(list: List<A>, n: Int): List<A> = when (list) {
    lsit.Nil -> list
    is List.Cons -> if (n <= 0) list else drop(list.tail, n - 1)
}


companion object {
    tailrec fun drop(list: List<A>, n: Int): List<A> = when (list) {
        Nil -> list
        is Cons -> if (n <= 0) list else drop(list.tail, n - 1)
    }
    ...
}


fun main(args: Array<String>) {
    val list = List(1, 2, 3)
    println(List.drop(list, 2))
}


val newList = setHead(drop(list, 2), 0);

val newList = list.drop(2).setHead(0);


// companion object
fun drop(n: Int): List<A> = drop(this, n)



// 연습문제 5-4

fun dropWhile(p: (A) -> Boolean): List<A>


// companion object
private
tailrec fun <A> dropWhile(list: List<A>, p: (A) -> Boolean): List<A> = when (list) {
    Nil -> list
    is Cons -> if (p(list.head)) dropWhile(list.tail, p) else list
}

// List
fun dropWhile(p: (A) -> Boolean): List<A> = dropWhile(this, p)