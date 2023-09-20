// B.3 추상화와 속성 기반 테스트

fun maxMultiple(multiple: Int, list: List<Int>): Int =
    list.fold(initialValue) { acc, int -> ... }


fun maxMultiple(multiple: Int, list: List<Int>): Int =
    list.foldLeft(initialValue) { acc -> { int -> ... } }


fun maxMultiple(multiple: Int, list: List<Int>): Int =
    list.fold(0, ::isMaxMultiple)

fun isMaxMultiple(acc: Int, value: Int) = ...


fun isMaxMultiple(acc: Int, elem: Int): Int =
    if (elem / 2 * 2 == elem && elem > acc) elem else acc


fun maxMultiple(multiple: Int, list: List<Int>): Int {
    fun isMaxMultiple(acc: Int, elem: Int): Int =
        if (elem / multiple * multiple == elem && elem > acc) elem else acc
    return list.fold(0, ::isMaxMultiple)
}


// 예제 01
fun isMaxMultiple(multiple: Int) =
    { max: Int, value: Int ->
        when {
            value / multiple * multiple == value && value > max -> value
            else -> max
        }
    }


fun isMaxMultiple(multiple: Int) =
    { max: Int, value: Int ->
        when {
            value % multiple == 0 && value > max -> value
            else -> max
        }
    }


fun test(value: Int, max: Int, multiple: Int): Boolean {
    val result = isMaxMultiple(multiple)(max, value)

        ... // 속성 검증
}


result >= max
result % multiple == 0 || result == max
(result % multiple == 0 && result >= value) || result % multiple != 0