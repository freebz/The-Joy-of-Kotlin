// 7.8 효과 적용하기

// 연습문제 7-9

// Result
abstract fun forEach(effect: (A) -> Unit)

// Failure
override fun forEach(effect: (A) -> Unit) {}

// Empty
override fun forEach(effect: (Nothing) -> Unit) {}

// Success
override fun forEach(effect: (A) -> Unit) {
    effect(value)
}



// 연습문제 7-10

abstract fun forEachOrElse(onSuccess: (A) -> Unit,
                           onFailure: (RuntimeException) -> Unit,
                           onEmpty: () -> Unit)


// Success
override fun forEachOrElse(onSuccess: (A) -> Unit,
                           onFailure: (RuntimeException) -> Unit,
                           onEmpty: () -> Unit) = onSuccess(value)

// Failure
override fun forEachOrElse(onSuccess: (A) -> Unit,
                           onFailure: (RuntimeException) -> Unit,
                           onEmpty: () -> Unit) = onFailure(exception)

// Empty
override fun forEachOrElse(onSuccess: (Nothing) -> Unit,
                           onFailure: (RuntimeException) -> Unit,
                           onEmpty: () -> Unit) = onEmpty()



// 연습문제 7-11

abstract fun forEach(onSuccess: (A) -> Unit = {},
                     onFailure: (RuntimeException) -> Unit = {},
                     onEmpty: () -> Unit = {})


val result: Result<Int> = if (z % 2 == 0) Result(z) else Result()
result.forEach({ println("$it is even") }, onEmpty = { println("This one is odd") })


val result = getComputation()

result.forEach(::println, ::log)