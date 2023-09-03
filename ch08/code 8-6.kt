// 8.5.2 인덱스로 원소 접근하기

// 연습문제 8-12

fun getAt(index: Int): Result<A> {
    tailrec fun <A> getAt(list: List<A>, index: Int): Result<A> =
        when (list) {
            Nil -> Result.failure("Dead code. Should never executed.")
            is Cons ->
                if (index == 0)
                    Result(list.head)
                else
                    getAt(list.tail, index - 1)
        }
    
    return if (index < 0 || index >= length())
        Result.failure("Index out of bound")
    else
        getAt(this, index)
}


fun getAt(index: Int): Result<A> {
    tailrec fun <A> getAt(list: Cons<A>, index: Int): Result<A> =
        if (index == 0)
            Result(list.head)
        else
            getAt(list.tail as Cons, index - 1)
    
    return if (index < 0 || index >= length())
        Result.failure("Index out of bound")
    else
        getAt(this as Cons, index)
}


fun getAt(index: Int): Result<A> {
    tailrec fun <A> getAt(list: List<A>, index: Int): Result<A> = // 경고
        (list as Cons).let {
            if (index == 0)
                Result(list.head)
            else
                getAt(list.tail, index - 1)
        }
    
    return if (index < 0 || index >= length())
        Result.failure("Index out of bound")
    else
        getAt(this, index)
}


fun getAtViaFoldLeft(index: Int): Result<A> =
    Pair(Result.failure<A>("Index out of bound"), index).let {
        if (index < 0 || index >= length())
            it
        else
            foldLeft(it) { ta ->
                { a ->
                    if (ta.second < 0)
                        ta
                    else
                        Pair(Result(a), ta.second - 1)
                }
            }
    }.first



// 연습문제 8-13(어려움)

// List
abstract fun <B> foldLeft(identity: B, zero: B, f: (B) -> (A) -> B): B

// Nil
override fun <B> foldLeft(identity: B, zero: B, f: (B) -> (Nothing) -> B): B = identity

// Cons
override fun <B> foldLeft(identity: B, zero: B, f: (B) -> (A) -> B): B {
    fun <B> foldLeft(acc: B,
                     zero: B,
                     list: List<A>, f: (B) -> (A) -> B): B = when (list) {
        Nil -> acc
        is Cons ->
            if (acc == zero)
                acc
            else
                foldLeft(f(acc)(list.head), zero, list.tail, f)
    }
    return foldLeft(identity, zero, this, f)
}

// List
fun getAt(index: Int): Result<A> {
    data class Pair<out A>(val first: Result<A>, val second: Int) {
        override fun equals(other: Any?): Boolean = when {
            other == null -> false
            other.javaClass == this.javaClass -> (other as Pair<A>).second == second
            else -> false
        }
        override fun hashCode(): Int = first.hashCode() + second.hashCode()
    }

    return Pair<A>(Result.failure("Index out of bound"), index)
        .let { identity ->
            Pair<A>(Result.failure("Index out of bound"), -1).let { zero ->
                if (index < 0 || index >= length())
                    identity
                else
                    foldLeft(identity, zero) { ta: Pair<A> ->
                        { a: A ->
                            if (ta.second < 0)
                                ta
                            else
                                Pair(Result(a), ta.second - 1)
                        }
                    }
            }
        }.first
}


// List
abstract fun <B> foldLeft(acc: B, p: (B) -> Boolean, f: (B) -> (A) -> B): B

// Nil
override fun <B> foldLeft(identity: B,
                          p: (B) -> Boolean,
                          f: (B) -> (Nothing) -> B): B = identity

// Cons
override fun <B> foldLeft(identity: B,
                          p: (B) -> Boolean,
                          f: (B) -> (A) -> B): B {
    fun foldLeft(acc: B,
                 p: (B) -> Boolean,
                 list: List<A>): B = when (list) {
        Nil -> acc
        is Cons ->
            if (p(acc))
                acc
            else
                foldLeft(f(acc)(list.head), p, list.tail)
    }
    return foldLeft(identity, p, this)
}

// List
fun getAt(index: Int): Result<A> {
    val p: (Pair<Result<A>, Int>) -> Boolean = { it.second < 0 }
    return Pair<Result<A>, Int>(Result.failure("Index out of bound"), index)
        .let { identity ->
            if (index < 0 || index >= length())
                identity
            else
                foldLeft(identity, p) { ta: Pair<Result<A>, Int> ->
                    { a: A ->
                        if (p(ta))
                            ta
                        else
                            Pair(Result(a), ta.second - 1)
                    }
                }
        }.first
}