// 3.3.4 다형적 HOF 정의하기

// 연습문제 3-5(어려움)

val <T, U, V> higherCompose: ((U) -> V) -> ((T) -> U) -> (T) -> V =
    { f->
        { g ->
            { x -> f(g(x)) }
        }
    }


fun <T, U, V> higherCompose: ((U) -> V) -> ((T) -> U) -> (T) -> V =
    { f->
        { g ->
            { x -> f(g(x)) }
        }
    }


fun <T, U, V> higherCompose() =
    { f: (U) -> V ->
        { g: (T) -> U ->
            { x: T -> f(g(x)) }
        }
    }


val squareOfTriple = higherCompose()(square)(triple)

// Error: Kotlin: Type inference failed:
//     Not enough information to infer type variable T in fun <T, U, V>
//     higherCompose(): ((U) -> V) -> ((T) -> U) -> (T) -> V
// Please specify it explicitly.


val squareOfTriple = higherCompose<Int, Int, Int>()(square)(triple)



// 연습문제 3-6(이제는 쉬움!)

fun <T, U, V> higherAndThen(): ((T) -> U) -> ((U) -> V) -> (T) -> V =
    { f: (T) -> U ->
        { g: (U) -> V ->
            { x: T -> g(f(x)) }
        }
    }



// 함수 파라미터 테스트하기

fun testHigherCompose() {
    val f: (Double) -> Int = { a -> (a * 3).toInt() }
    val g: (Long) -> Double = { a -> a + 2.0 }

    assertEquals(Integer.valueOf(9), f(g(1L)))
    assertEquals(Integer.valueOf(9),
        higherCompose<Long, Double, Int>()(f)(g)(1L))
}