// 4.3.3 리스트 뒤집기

fun <T> reverse(list: List<T>): List<T> {
    val result: MutableList<T> = mutableListOf()
    (list.size downTo 1).forEach {
        result.add(list[it - 1])
    }
    return result
}



// 연습문제 4-7

fun <T> prepend(list: List<T>, elem: T): List<T> = listOf(elem) + list

fun <T> reverse(list: List<T>) = foldLeft(list, listOf(), ::prepend)



// 연습문제 4-8

fun <T> copy(list: List<T>): List<T> = foldLeft(list, listOf()) { lst, elem -> lst + elem }

fun <T> prepend(list: List<T>, elem: T): List<T> =
    foldLeft(list, listOf(elem)) { lst, elm -> lst + elm }

fun <T> reverse(list: List<T>): List<T> = foldLeft(list, listOf(), ::prepend)