// 4.2.2 루프를 공재귀로 변환하기

fun sum(n: Int): Int = if (n < 1) 0 else n + sum(n - 1)


fun sum(n: Int): Int {
    var s = 0
    var i = 0
    while(i <= n) {
        s += i
        i += 1
    }
    return s
}


fun sum(n: Int, s: Int, i: Int): Int = ...


fun sum(n: Int, s: Int, i: Int): Int = ...

fun sum(n: Int) = sum(n, 0, n)


fun sum(n: Int): Int {
    fun sum(s: Int, i: Int): Int = ...
    return sum(0, n)
}


fun sum(n: Int): Int {
    fun sum(s: Int, i: Int): Int = sum(s + i, i + 1)
    return sum(0, n)
}


fun sum(n: Int): Int {
    fun sum(s: Int, i: Int): Int = if (i > n) s else sum(s + i, i + 1)
    return sum(0, n)
}


fun sum(n: Int): Int {
    tailrec fun sum(s: Int, i: Int): Int =
        if (i > n) s else sum(s + i, i + 1)
    return sum(0, n)
}



// 연습문제 4-1

fun inc(n: Int) = n + 1
fun dec(n: Int) = n - 1


fun add(a: Int, b: Int): Int


fun add(a: Int, b: Int): Int {
    var x = a
    var y = b
    while(y != 0) {
        x = inc(x)
        y = dec(y)
    }
    return x
}


fun add(a: Int, b: Int): Int {
    var x = a
    var y = b
    while(true) {
        if (y == 0) return x
        x = inc(x)
        y = dec(y)
    }
}


tailrec fun add(x: Int, y: Int): Int = if (y == 0) x else add(inc(x), dec(y))