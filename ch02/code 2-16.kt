// 2.7.2 로컬 함수 사용하기

fun sumOfPrimes(limit: Int): Long {
    val seq: Sequence<Long> = sequenceOf(2L) + generateSequence(3L, {
        it + 2
    }).takeWhile{
        it < limit
    }

    fun isPrime(n: Long): Boolean = seq.takeWhile {
        it * it <= n
    }.all {
        n % it != 0L
    }

    return seq.filter(::isPrime).sum()
}


fun sumOfPrimes(limit: Int): Long {
    val seq: Sequence<Long> = sequenceOf(2L) + generateSequence(3L, {
        it + 2
    }).takeWhile{
        it < limit
    }

    return seq.filter {
        x -> isPrime(x,seq)
    }.sum()
}

fun isPrime(n: Long, seq: Sequence<Long>): Boolean = seq.takeWhile {
    it * it <= n
}.all {
    n % it != 0L
}