// 4.3 재귀 함수와 리스트

fun sum(list: List<Int>): Int =
    if (list.isEmpty()) 0 else list[0] + sum(list.drop(1))


fun <T> head(list: List<T>): T =
    if (list.isEmpty())
        throw IllegalArgumentException("head called on empty list")
    else
        list[0]

fun <T> tail(list: List<T>): List<T> =
    if (list.isEmpty())
        throw IllegalArgumentException("tail called on empty list")
    else
        list.drop(1)

fun sum(list: List<Int>): Int =
    if (list.isEmpty())
        0
    else
        head(list) + sum(tail(list))


fun <T> List<T>.head(): T =
    if (this.isEmpty())
        throw IllegalArgumentException("head called on empty list")
    else
        this[0]

fun <T> List<T>.tail(): List<T> =
    if (this.isEmpty())
        throw IllegalArgumentException("tail called on empty list")
    else
        this.drop(1)

fun sum(list: List<Int>): Int =
    if (list.isEmpty())
        0
    else
        list.head() + sum(list.tail())


fun sum(list: List<Int>): Int {
    tailrec fun sumTail(list: List<Int>, acc: Int): Int =
        if (list.isEmpty())
            acc
        else
            sumTail(list.tail(), acc + list.head())
    return sumTail(list, 0)
}