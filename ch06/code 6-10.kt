// 6.4.6 Option으로 List 합성하기

// 연습문제 6-11

fun <A> sequence(list: List<Option<A>>): Option<List<A>>


fun <A> sequence(list: List<Option<A>>): Option<List<A>> {
    return if (list.isEmpty())
        Option(List())
    else
        list.head().flatMap({ hh ->
            sequence(list.tail()).map({ x -> x.cons(hh) }) })
}


fun <A> sequence(list: List<Option<A>>): Option<List<A>> =
    list.foldRight(Option(List())) { x ->
        { y: Option<List<A>> -> map2(x, y) { a ->
            { b: List<A> -> b.cons(a) } }
        }
    }


val parseWithRadix: (Int) -> (String) -> Int = { radix ->
    { string ->
        Integer.parseInt(string, radix)
    }
}

val parse16 = hLift(parseWithRadix(16))
val list = List("4", "5", "6", "7", "8", "9", "A", "B")
val result = sequence(list.map(parse16))

println(result)
// Some(value=[4, 5, 6, 7, 8, 9, 10, 11, NIL])



// 연습문제 6-12

fun <A, B> traverse(list: List<A>, f: (A) -> Option<B>): Option<List<B>>


fun <A, B> traverse(list: List<A>, f: (A) -> Option<B>): Option<List<B>> =
    list.foldRight(Option(List())) { x ->
        { y: Option<List<B>> ->
            map2(f(x), y) { a ->
                { b: List<B> ->
                    b.cons(a)
                }
            }
        }
    }


fun <A> sequence(list: List<Option<A>>): Option<List<A>> =
    traverse(list) { x -> x }