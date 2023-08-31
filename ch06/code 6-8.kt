// 6.4.4 Option 사용 예

// 연습문제 6-7

val variance: (List<Double>) -> Option<Double>


val mean: (List<Double>) -> Option<Double> = { list ->
    when {
        list.isEmpty() -> Option()
        else -> Option(list.sum() / list.size)
    }
}


val variance: (List<Double>) -> Option<Double> = { list ->
    mean(list).flatMap { m ->
        mean(list.map { x ->
            Math.pow((x - m), 2.0)
        })
    }
}


fun mean(list: List<Double>): Option<Double> =
    when {
        list.isEmpty() -> Option()
        else -> Option(list.sum() / list.size)
    }

fun variance(list: List<Double>): Option<Double> =
    mean(list).flatMap { m ->
        mean(list.map { x ->
            Math.pow((x - m), 2.0)
        })
    }


fun aToBfunction(a: A): B {
    return ...
}


val aToBfunction: (A) -> B = { a -> aToBfunction(a) }


val aToBfunction: (A) -> B = ::aToBfunction


fun aToBfunction(a: A): B = aToBfunction(a)