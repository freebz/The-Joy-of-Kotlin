// 7.9 고급 Result 합성

// 연습문제 7-12

fun <A, B> lift(f: (A) -> B): (Result<A>) -> Result<B>


fun <A, B> lift(f: (A) -> B): (Result<A>) -> Result<B> = { it.map(f) }



// 연습문제 7-13

fun <A, B, C> lift2(f: (A) -> (B) -> C):
    (Result<A>) -> (Result<B>) -> Result<C>
fun <A, B, C, D> lift3(f: (A) -> (B) -> (C) -> D):
    (Result<A>) -> (Result<B>) -> (Result<C>) -> Result<D>


fun <A, B, C> lift2(f: (A) -> (B) -> C):
    (Result<A>) -> (Result<B>) -> Result<C> =
        { a ->
            { b ->
                a.map(f).flatMap { b.map(it) }
            }
        }

fun <A, B, C, D> lift3(f: (A) -> (B) -> (C) -> D):
    (Result<A>) -> (Result<B>) -> (Result<C>) -> Result<D> =
        { a ->
            { b ->
                { c ->
                    a.map(f).flatMap { b.map(it) }.flatMap { c.map(it) }
                }
            }
        }
    


// 연습문제 7-14

// Option packages
fun <A, B, C> map2(oa: Option<A>,
                   ob: Option<B>,
                   f: (A) -> (B) -> C): Option<C> = oa.flatMap { a -> ob.map { b ->
                   f(a)(b) } }

// Result packages
fun <A, B, C> map2(a: Result<A>,
                   b: Result<B>,
                   f: (A) -> (B) -> C): Result<C> = lift2(f)(a)(b)


fun getFirstName(): Result<String> = Result("Mickey")

fun getLastName(): Result<String> = Result("Mouse")

fun getMail(): Result<String> = Result("mickey@disney.com")


var createPerson: (String) -> (String) -> (String) -> Toon =
    { x -> { y -> { z -> Toon(x, y, z) } } }

val toon = lift3(createPerson)(getFirstName())(getLastName())(getMail())


val toon = getFirstName()
    .flatMap { firstName ->
        getLastName()
            .flatMap { lastName ->
                getMail()
                    .map { mail -> Toon(firstName, lastName, mail) }
            }
    }


val toon2 = lift3 { x: String ->
    { y: String ->
        { z: String ->
            Toon(x, y, z)
        }
    }
}(getFirstName())(getLastName())(getMail())


for {
    firstName in getFirstName(),
    lastName in getLastName(),
    mail in getMail()
} return new Toon(firstName, lastName, mail)


val result1 = Result(1)
val result2 = Result(2)
val result3 = Result(3)
val result4 = Result(4)
val result5 = Result(5)

fun compute(p1: Int, p2: Int, p3: Int, p4: Int, p5: Int) =
    p1 + p2 + p3 + p4 + p5

val result = result1.flatMap { p1: Int ->
    result2.flatMap { p2 ->
        result3.flatMap { p3 ->
            result4.flatMap { p4 ->
                result5.map { p5 ->
                    compute(p1, p2, p3, p4, p5)
                }
            }
        }
    }
}


val result1 = Result(1)
val result2 = Result(2)
val result3 = Result(3)
val result4 = Result(4)
val result5 = Result(5)

fun compute(p1: Int, p2: Int, p3: Int, p4: Int, p5: Int) =
    Result(p1 + p2 + p3 + p4 + p5)

val result = result1.flatMap { p1: Int ->
    result2.flatMap { p2 ->
        result3.flatMap { p3 ->
            result4.flatMap { p4 ->
                result5.flatMap { p5 ->
                    compute(p1, p2, p3, p4, p5)
                }
            }
        }
    }
}