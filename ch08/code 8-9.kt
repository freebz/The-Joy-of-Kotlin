// 8.5.5 리스트를 처리하는 다른 함수들

// 연습문제 8-17

data class Payment(val name: String, val amount: Int)


val map: Map<String, List<Payment>> = list.groupBy { x -> x.name }


fun <B> groupBy(f: (A) -> B): Map<B, List<A>> =
    reverse().foldLeft(mapOf()) { mt: Map<B, List<A>> ->
        { t ->
            val key = f(t)
            mt + (key to (mt[key] ?: Nil).cons(t))
        }
    }


fun <B> groupBy(f: (A) -> B): Map<B, List<A>> =
    reverse().foldLeft(mapOf()) { mt: Map<B, List<A>> ->
        { t->
            val k = f(t)
            mt + (k to (mt.getOrDefault(k, Nil)).cons(t))
        }
    }


fun <B> groupBy(f: (A) -> B): Map<B, List<A>> =
    reverse().foldLeft(mapOf()) { mt: Map<B, List<A>> ->
        { t ->
            f(t).let {
                mt + (it to (mt.getOrDefault(it, Nil)).cons(t))
            }
        }
    }


fun <B> groupBy(f: (A) -> B): Map<B, List<A>> =
    foldRight(mapOf()) { t ->
        { mt: Map<B, List<A>> ->
            f(t).let { mt + (it to (mt.getOrDefault(it, Nil)).cons(t)) }
        }
    }



// 연습문제 8-18

unfold(0) { i ->
    if (i < 10)
        Option(Pair(i, i + 1))
    else
        Option()
}


fun <A, S> unfold_(z: S, f: (S) -> Option<Pair<A, S>>): List<A> =
    f(z).map({ x ->
        unfold_(x.second, f).cons(x.first)
    }).getOrElse(List.Nil)


fun <A, S> unfold(z: S, getNext: (S) -> Option<Pair<A, S>>): List<A> {
    tailrec fun unfold(acc: List<A>, z: S): List<A> {
        val next = getNext(z)
        return when (next) {
            Option.None -> acc
            is Option.Some -> unfold(acc.cons(next.value.first), next.value.second)
        }
    }
    return unfold(List.Nil,  z).reverse()
}


fun main(args: Array<String>) {
    val f: (Int) -> Option<Pair<Int, Int>> =
        { it ->
            if (it < 10_000) Option(Pair(it, it + 1)) else Option()
        }
    val result = unfold(0, f)
    println(result)
}


fun <A, S> unfold(z: S, getNext: (S) -> Result<Pair<A, S>>): Result<List<A>> {
    tailrec fun unfold(acc: List<A>, z: S): Result<List<A>> {
        val next = getNext(z)
        return when (next) {
            Result.Empty -> Result(acc)
            is Result.Failure -> Result.failure(next.exception)
            is Result.Success -> unfold(acc.cons(next.value.first), next.value.second)
        }
    }
    return unfold(List.Nil, z).map(List<A>::reverse)
}



// 연습문제 8-19

fun range(start: Int, end: Int): List<Int> {
    return unfold(start) { i ->
        if (i < end)
            Option(Pair(i, i + 1))
        else
            Option()
    }
}



// 연습문제 8-20

// List
fun exists(p: (A) -> Boolean): Boolean =
    when (this) {
        Nil -> false
        is Cons -> p(head) || tail.exists(p)
    }


fun exists(p: (A) -> Boolean): Boolean =
    foldLeft(false, true) { x -> { y: A -> x || p(y) } }.first



// 연습문제 8-21

fun forAll(p: (A) -> Boolean): Boolean =
    foldLeft(true, false) { x-> { y: A -> x && p(y) } }.first


fun forAll(p: (A) -> Boolean): Boolean = !exists { !p(it) }