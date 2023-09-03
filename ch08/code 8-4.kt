// 8.4.2 List<Result>를 Result<List>로 변환하기

// 연습문제 8-5

fun <A> flattenResult(list: List<Result<A>): List<A>


{ ra -> ra.map { List(it) }.getOrElse(List()) }


fun <A> flattenResult(list: List<Result<A>>): List<A> =
    list.flatMap { ra -> ra.map { List(it) }.getOrElse(List()) }



// 연습문제 8-6

fun <A> sequence(list: List<Result<A>>): Result<List<A>>


import com.fpinkotlin.common.map2
...

fun <A> sequence(list: List<Result<A>>): Result<List<A>> =
    list.foldRight(Result(List())) { x ->
        { y: Result<List<A>> ->
            map2(x, y) { a -> { b: List<A> -> b.cons(a) } }
        }
    }


fun <A> sequence2(list: List<Result<A>>): Result<List<A>> =
    list.filter{ !it.isEmpty() }.foldRight(Result(List())) { x ->
        { y: Result<List<A>> ->
            map2(x, y) { a -> { b: List<A> -> b.cons(a) } }
        }
    }



// 연습문제 8-7

fun <A, B> traverse(list: List<A>, f: (A) -> Result<B>): Result<List<B>>


fun <A, B> traverse(list: List<A>, f: (A) -> Result<B>): Result<List<B>> =
    list.foldRight(Result(List())) { x ->
        { y: Result<List<B>> ->
            map2(f(x), y) { a -> { b: List<B> -> b.cons(a) } }
        }
    }


fun <A> sequence(list: List<Result<A>>): Result<List<A>> =
    traverse(list, { x: Result<A> -> x })