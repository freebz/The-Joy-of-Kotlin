// 2.9 프로그램 흐름과 제어 구조

// 2.9.1 조건 선택 사용하기

int a = ...
int b = ...

if (a < b) {
    System.println("a is smaller than b")
} else {
    System.println("a is not smaller than b")
}


val a: Int = ...
val b: Int = ...

val s = if (a < b) {
    "a is smaller than b"
} else {
    "a is not smaller than b"
}

println(s)


val a: Int = ...
val b: Int = ...

val s = if (a < b)
            "a is smaller than b"
        else
            "a is not smaller than b"

println(s)


val a: Int = 6
val b: Int = 5

val percent = if (b != 0) {
    val temp = a / b
    temp * 100
} else {
    0
}