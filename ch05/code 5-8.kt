// 5.6.5 변성 사용하기

sealed class List<out A> {
    ...
}


// Error: Kotlin: Type parameter A is declared as 'out'
// but occurs in 'in' position in type A


fun cons(a: A): List<A> = Cons(a, this)



// 변성에 어긋나는 활용 방지하기

sealed class List<out A> {
    fun cons(a: A): List<A> = Cons(a, this) { // 컴파일 오류
        ...
    }

    internal class Cons<out A>(internal val head: A, internal val tail: List<A>): List<A> {
        internal object Nil: List<Nothing>()
    }
}


// Type parameter A is declared as 'out' but occurs in 'in' position in type `A`


sealed class List<out A> {
    abstract fun cons(a: A): List<A>

    internal class Cons<out A>(internal val head: A,
                               internal val tail: List<A>): List<A>() {
        ...
    }

    internal object Nil: List<Nothing>() {
        override fun cons(a: Nothing): List<Nothing> = Cons(a, this) // 오류
    }
}


Cons(a, this)

override fun cons(a: Nothing)

fun cons(a: @UnsafeVariance A): List<A>

sealed class List<out A> {
    fun cons(a: @UnsafeVariance A):List<A> = Cons(a, this)
    ...
}


sealed class List<out A> {
    fun setHead(a: @UnsafeVariance A): List<A> = when (this) {
        is Cons -> Cons(a, this.tail)
        Nil -> throw IllegalStateException("setHead called on an empty list")
    }

    fun cons(a: @UnsafeVariance A): List<A> = Cons(a, this)
    fun concat(list: List<@UnsafeVariance A>): List<A> = concat(this, list)
    ...
}


sealed class List<A> {
    abstract fun concat(list: List<A>): List<A>

    abstract class Empty<A> : List<A>() {
        override fun concat(list: List<A>): List<A> = list
    }

    private object Nil : Empty<Nothing>()

    private class Cons<A>(private val head: A, private val tail: List<A>): List<A>() {
        override fun concat(list: List<A>): List<A> =
                  Cons(this.head, list.concat(this.tail))
    }
}


fun <A> concat(list1: List<A>, list2: List<A>): List<A> = when (list1) {
    Nil -> list2
    is Cons -> Cons(list1.head, concat(list1.tail, list2))
}



// 연습문제 5-7

head * (꼬리의 product)


fun product(nums: List<Double>): Double = when (nums) {
    List.Nil -> 1.0
    is List.Cons -> nums.head * product(nums.tail)
}


fun product(nums: List<Double>): Double = when (nums) {
    List.Nil -> 1.0
    is List.Cons -> if (nums.head == 0.0)
                        0.0
                    else
                        nums.head * product(nums.tail)
}


fun sum(ints: List<Int>): Int = when (ints) {
    List.Nil -> 0
    is List.Cons -> ints.head + sum(ints.tail)
}

fun product(ints: List<Double>): Double = when (ints) {
    List.Nil -> 1.0
    is List.Cons -> ints.head * product(ints.tail)
}


fun operation(list: List<Type>): Type = when (list) {
    List.Nil -> identity
    is List.Cons -> list.head operator operation(list.tail)
}

fun operation(list: List<Type>): Type = when (list) {
    List.Nil -> identity
    is List.Cons -> list.head operator operation(list.tail)
}


fun <A> operation(list: List<A>,
                  identity: A,
                  operator: (A) -> (A) -> (A)): A =
    when (list) {
        List.Nil -> identity
        is List.Cons -> operator (list.head) (operation(list.tail, identity, operator))
    }


fun <A, B> operation(list: List<A>,
                     identity: B,
                     operator: (A) -> (B) -> (B)): B =
    when (list) {
        List.Nil -> identity
        is List.Cons -> operator (list.head) (operation(list.tail, identity, operator))
    }


Cons(1, Cons(2, Cons(3, Nil)))

f(1, f(2, f(3, identity)))


foldRight(List(1, 2, 3), List()) { x: Int ->
    { acc: List<Int> ->
        acc.cons(x)
    }
}


println(foldRight(List(1, 2, 3), List()) { x: Int ->
    { acc: List<Int> ->
        acc.cons(x)
    }
})

// [1, 2, 3, NIL]


foldRight(List(1, 2, 3), List(), x -> acc -> acc.cons(x));
foldRight(List(1, 2), List(3), x -> acc -> acc.cons(x));
foldRight(List(1), List(2, 3), x -> acc -> acc.cons(x));
foldRight(List(), List(1, 2, 3), x -> acc -> acc.cons(x));


// List
fun <B> foldRight(identity: B, f: (A) -> (B) -> B): B =
    foldRight(this, identity, f)



// 연습문제 5-8

fun length(): Int = foldRight(0) { x -> { acc -> acc + 1 } }

fun length(): Int = foldRight(0) { x -> { it + 1 } }

fun length(): Int = foldRight(0) { _ -> { it + 1 } }

fun length(): Int = foldRight(0) { { it + 1 } }



// 연습문제 5-9

fun <B> foldLeft(identity: B, f: (B) -> (A) -> B): B


// companion object
tailrec fun <A, B> foldLeft(acc: B, list: List<A>, f: (B) -> (A) -> B): B =
    when (list) {
        List.Nil -> acc
        is List.Cons -> foldLeft(f(acc)(list.head), list.tail, f)
    }

// List
fun <B> foldLeft(identity: B, f: (B) -> (A) -> B): B =
    foldLeft(identity, this, f)



// 연습문제 5-10

// companion object
fun sum(list: List<Int>): Int = list.foldLeft(0, { acc -> { y -> acc + y } })

// companion object
fun product(list: List<Double>): Double = list.foldLeft(1.0, { acc -> { y -> acc * y } })

// List
fun length(): Int = foldLeft(0) { { _ -> it + 1 } }

// List
fun length(): Int = foldLeft(0) { acc -> { acc + 1 } }



// 연습문제 5-11

fun reverse(): List<A> = foldLeft(Nil as List<A>) { acc -> { acc.cons(it) } }

fun reverse(): List<A> = foldLeft(List.invoke()) { acc -> { acc.cons(it) } }



// 연습문제 5-12

fun <B> foldRightViaFoldLeft(identity: B, f: (A) -> (B) -> B): B =
    this.reverse().foldLeft(identity) { acc -> { y -> f(y)(acc) } }