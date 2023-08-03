// 3.3.8 함수 부분 적용과 자동 커링

class TaxComputer(private val rate: Double) {
    fun compute(price: Double): Double = price * rate + price
}


val tc9 = TaxComputer(0.09)
val price = tc9.compute(12.0)


val tc9 = addTax(0.09)
val price = tc9(12.0)



// 연습문제 3-7(쉬움)

fun <A, B, C> partialA(a: A, f: (A) -> (B) -> C): (B) -> C


fun <A, B, C> partialA(a: A, f: (A) -> (B) -> C): (B) -> C = f(a)



// 연습문제 3-8

fun <A, B, C> partialB(b: B, f: (A) -> (B) -> C): (A) -> C


fun <A, B, C> partialB(b: B, f: (A) -> (B) -> C): (A) -> C =
    { a: A ->
    }

fun <A, B, C> partialB(b: B, f: (A) -> (B) -> C): (A) -> C =
    { a: A ->
        f(a)
    }

fun <A, B, C> partialB(b: B, f: (A) -> (B) -> C): (A) -> C =
    { a: A ->
        f(a)(b)
    }



// 연습문제 3-9(쉬움)

fun <A, B, C, D> func(a: A, b: B, c: C, d: D): String = "$a, $b, $c, $d"


fun <A, B, C, D> curried()

fun <A, B, C, D> curried(): (A) ->

fun <A, B, C, D> curried(): (A) -> (B) ->

fun <A, B, C, D> curried(): (A) -> (B) -> (C) -> (D) ->

fun <A, B, C, D> curried(): (A) -> (B) -> (C) -> (D) -> String

fun <A, B, C, D> curried() =
    { a: A ->
        { b: B ->
            { c: C ->
                { d: D ->
                }
            }
        }
    }

    fun <A, B, C, D> curried() =
    { a: A ->
        { b: B ->
            { c: C ->
                { d: D ->
                    "$a, $b, $c, $d"
                }
            }
        }
    }



// 연습문제 3-10

fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C


fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C =
    { a ->
        { b ->
        }
    }

fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C =
    { a ->
        { b ->
            f(a, b)
        }
    }