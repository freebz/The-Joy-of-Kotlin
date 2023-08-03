// 3.3.9 부분 적용 함수의 인자 뒤바꾸기

val addTax: (Double) -> (Double) -> Double =
    { x ->
        { y ->
            y + y / 100 * x
        }
    }


val add9percentTax: (Double) -> Double = addTax(9.0)


val priceIncludingTax = add9percentTax(price);


val addTax: (Double) -> (Double) -> Double =
    { x ->
        { y ->
            x + x / 100 * y
        }
    }



// 연습문제 3-11

fun <T, U, V> swapArgs(f: (T) -> (U) -> V): (U) -> (T) -> (V) =
    { u -> { t -> f(t)(u) } }

    
val payment = { amount -> { rate -> ... } }