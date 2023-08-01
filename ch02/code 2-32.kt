// 2.16 변성: 파라미터화한 타입과 하위 타입

// 2.16.1 변성이 문제인 이유

val s = "A String"
val a: Any = s


val ls = mutableListOf("A String")
val la: MutableList<Any> = ls // <-- 컴파일 오류
la.add(42)


val ls = listOf("A String")
val la = ls + 42 // <-- 코틀린은 `la`의 타입을 `List<Any>`로 추론함


fun <T> addAll(list1: MutableList<T>,
               list2: MutableList<t>) {
    for (elem in list2) list1.add(elem)
}

val ls = mutableListOf("A String")
val la: MutableList<Any> = mutableListOf()
addAll(la, ls) // <-- 컴파일되지 않음


fun <T> addAll(list1: MutableList<T>,
               list2: MutableList<out T>) { // <-- T를 공변성으로 만듦
    for (elem in list2) list1.add(elem)
}

val ls = mutableListOf("A String")
val la: MutableList<Any> = mutableListOf()
addAll(la, ls) // <-- 오류가 발생하지 않음