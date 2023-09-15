// 12.1.1 효과 처리하기

// 12.1.2 효과 구현하기

(T) -> Unit


val display = { x: Any -> println(x) }

val display: (Any) -> Unit = ::println


val ri: Result<Int> = ...
val rd: Result<Double> = ri.flatMap(inverse)
rd.map { it * 1.35 }


val ri: Result<Int> = ...
val rd: Result<Double> = ri.flatMap(inverse)
val function: (Double) -> Double = { it * 2.35 }
val result = rd.map(function)


val ri: Result<Int> = ...
val rd: Result<Double> = ri.flatMap(inverse)
val function: (Double) -> Double = { it * 2.35 }
val result = rd.map(function)
val ef: (Double) -> Unit = ::println
val x: Result<Unit> = result.map(ef)


// Empty
override fun forEach(onSuccess: (Nothing) -> Unit,
                     onFailure: (RuntimeException) -> Unit,
                     onEmpty: () -> Unit) {
    onEmpty()
}

// Success
override fun forEach(onSuccess: (A) -> Unit,
                     onFailure: (RuntimeException) -> Unit,
                     onEmpty: () -> Unit) {
    onSuccess(value)
}

// Failure
override fun forEach(onSuccess: (A) -> Unit,
                     onFailure: (RuntimeException) -> Unit,
                     onEmpty: () -> Unit) {
    onFailure(exception)
}


// Result
abstract fun forEach(onSuccess: (A) -> Unit = {},
                     onFailure: (RuntimeException) -> Unit = {},
                     onEmpty: () -> Unit = {})



// 연습문제 12-1

// List
fun forEach(ef: (A) -> Unit)

// Nil
override fun forEach(ef: (Nothing) -> Unit) {}

// Cons
override fun forEach(ef: (A) -> Unit) {
    ef(head)
    tail.forEach(ef)
}


override fun forEach(ef: (A) -> Unit) {
    tailrec fun forEach(list: List<A>) {
        when (list) {
            Nil -> {}
            is cons -> {
                ef(list.head)
                forEach(list.tail)
            }
        }
    }
    forEach(this)
}