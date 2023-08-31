// 6.4.5 Option을 조합하는 다른 방법

// 연습문제 6-8

fun <A, B> lift(f: (A) -> B): (Option<A>) -> Option<B> = { it.map(f) }


val upperOption: (Option<String>) -> Option<String> = lift { it.toUpperCase() }

val upperOption: (Option<String>) -> Option<String> = lift(String::toUpperCase)



// 연습문제 6-9

fun <A, B> lift(f: (A) -> B): (Option<A>) -> Option<B> = {
    try {
        it.map(f)
    } catch (e: Exception) {
        Option()
    }
}


fun <A, B> hLift(f: (A) -> B): (A) -> Option<B> = {
    try {
        Option(it).map(f)
    } catch (e: Exception) {
        Option()
    }
}


val parseWithRadix: (Int) -> (String) -> Int =
    { radix -> { string -> Integer.parseInt(string, radix) } }


val parseHex: (String) -> Int = parseWithRadix(16)



// 연습문제 6-10

fun <A, B, C> map2(oa: Option<A>,
                   ob: Option<B>,
                   f: (A) -> (B) -> C): Option<C> =
    oa.flatMap { a ->
        ob.map { b ->
            f(a)(b)
        }
    }


fun <A, B, C, D> map3(oa: Option<A>,
                      ob: Option<B>,
                      oc: Option<C>,
                      f: (A) -> (B) -> (C) -> D): Option<D> =
    oa.flatMap { a ->
        ob.flatMap { b ->
            oc.map { c ->
                f(a)(b)(c)
            }
        }
    }