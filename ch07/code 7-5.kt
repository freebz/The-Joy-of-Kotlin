// 7.5 고급 Result 처리

// 7.5.1 술어 적용하기

// 연습문제 7-5

fun filter(p: (A) -> Boolean): Result<A>


fun filter(p: (A) -> Boolean): Result<A> = flatMap {
    if (p(it))
        this
    else
        failure("Condition not matched")
}

fun filter(message: String, p: (A) -> Boolean): Result<A> = flatMap {
    if (p(it))
        this
    else
        failure(message)
}



// 연습문제 7-6

fun exists(p: (A) -> Boolean): Boolean


fun exists(p: (A) -> Boolean): Boolean = map(p).getOrElse(false)