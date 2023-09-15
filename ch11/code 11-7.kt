// 11.4 원소와 정렬한 리스트

// 연습문제 11-8

fun pop(): Option<Pair<A, Heap<A>>>

// Heap
abstract fun pop(): Option<Pair<A, Heap<A>>>

// Empty
override fun pop(): Option<Pair<A, Heap<A>>> = Option()

// H
override fun pop(): Option<Pair<A, Heap<A>>> = Option(Pair(hd, merge(lft, rght)))

// Heap
fun toList(): List<A> = unfold(this) { it.pop() }



// 연습문제 11-9

// Heap
fun <A, S> unfold(z: S, getNext: (S) -> Option<Pair<A, S>>): List<A> {
    tailrec fun unfold(acc: List<A>, z: S): List<A> {
        val next = getNext(z)
        return when (next) {
            is Option.None -> acc
            is Option.Some -> unfold(acc.cons(next.value.first), next.value.second)
        }
    }
    return unfold(List.Nil, z).reverse()
}

// Heap
fun <A, S, B> unfold(z: S,
                     getNext: (S) -> Option<Pair<A, S>>,
                     identity: B,
                     f: (B) -> (A) -> B): B {
    tailrec fun unfold(acc: B, z: S): B {
        val next = getNext(z)
        return when (next) {
            is Option.None -> acc
            is Option.Some -> unfold(f(acc)(next.value.first), next.value.second)
        }
    }
    return unfold(identity, z)
}

// Heap
fun <B> foldLeft(identity: B, f: (B) -> (A) -> B): B =
    unfold(this, { it.pop() }, identity, f)

// Heap
fun toList(): List<A> =
    foldLeft(List<A>()) { list -> { a -> list.cons(a) } }.reverse()