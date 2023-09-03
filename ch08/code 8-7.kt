// 8.5.3 리스트 나누기

// 연습문제 8-14

// List
fun splitAt(index: Int): Pair<List<A>, List<A>> {
    tailrec fun splitAt(acc: List<A>,
                        list: List<A>, i: Int): Pair<List<A>, List<A>> =
        when (list) {
            Nil -> Pair(list.reverse(), acc)
            is Cons ->
                if (i == 0)
                    Pair(list.reverse(), acc)
                else
                    splitAt(acc.cons(list.head), list.tail, i - 1)
        }
    return when {

        index < 0        -> splitAt(0)
        index > length() -> splitAt(length())
        else             -> splitAt(Nil, this.reverse(), this.length() - index)
    }
}



// 연습문제 8-15(8-13을 푼 경우 그렇게 어렵지 않음)

// List
fun splitAt(index: Int): Pair<List<A>, List<A>> {
    val ii = if (index < 0) 0
             else if (index >= length()) length()
             else index
    val identity = Triple(Nil, Nil, ii)
    val rt = foldLeft(identity) { ta: Triple<List<A>, List<A>, Int> ->
        { a ->
            if (ta.third == 0)
                Triple(ta.first, ta.second.cons(a), ta.third)
            else
                Triple(ta.first.cons(a), ta.second, ta.third - 1)
        }
    }
    return Pair(rt.first.reverse(), rt.second.reverse())
}


// List
abstract fun <B> foldLeft(identity: B, zero: B,
                          f: (B) -> (A) -> B): Pair<B, List<A>>

// Nil
override
fun <B> foldLeft(identity: B, zero: B, f: (B) -> (Nothing) -> B):
    Pair<B, List<Nothing>> = Pair(identity, Nil)

// Cons
override fun <B> foldLeft(identity: B, zero: B, f: (B) -> (A) -> B):
    Pair<B, List<A>> {
        fun <B> foldLeft(acc: B, zero: B, list: List<A>, f: (B) -> (A) -> B):
            Pair<B, List<A>> =
                when (list) {
                    Nil -> Pair(acc, list)
                    is Cons ->
                        if (acc == zero)
                            Pair(acc, list)
                        else
                            foldLeft(f(acc)(list.head), zero, list.tail, f)
                }
    return foldLeft(identity, zero, this, f)        
}

// List
fun splitAt(index: Int): Pair<List<A>, List<A>> {
    data class Pair<out A>(val first: List<A>, val second: Int) {
        override fun equals(other: Any?): Boolean = when {
            other == null -> false
            other.javaClass == this.javaClass ->
                (other as Pair<A>).second == second
            else -> false
        }
        override fun hashCode(): Int =
            first.hashCode() + second.hashCode()
    }
    return when {
        index <= 0 -> Pair(Nil, this)
        index >= length -> Pair(this, Nil)
        else -> {
            val identity = Pair(Nil as List<A>, -1)
            val zero = Pair(this, index)
            val (pair, list) = this.foldLeft(identity, zero) { acc ->
                { e -> Pair(acc.first.cons(e), acc.second + 1) }
            }
            Pair(pair.first.reverse(), list)
        }
    }
}