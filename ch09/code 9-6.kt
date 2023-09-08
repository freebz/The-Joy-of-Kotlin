// 9.4.2 함수 끌어올리기

// 연습문제 9-4

val consMessage: (String) -> (String) -> String =
    { greetings ->
        { name ->
            "$greetings, $name!"
        }
    }


(Lazy<String>) -> (Lazy<String>) -> Lazy<String>


((String) -> (String) -> String)

((String) -> (String) -> String) -> ((Lazy<String>) -> (Lazy<String>) -> Lazy<String>)

(String) -> ((String) -> String)

(String) -> (String) -> String


val lift2: ((String) -> (String) -> String) -> (Lazy<String>) ->
    (Lazy<String>) -> Lazy<String>


(Lazy<String>) -> (Lazy<String>) -> Lazy<String>

{ f → { ls1 → { ls2 → TODO() } } }


val lift2: ((String) -> (String) -> String) -> (Lazy<String>) ->
    (Lazy<String>) -> Lazy<String> =
        { f ->
            { ls1 ->
                { ls2 ->
                    TODO()
                }
            }
        }


val lift2: ((String) -> (String) -> String) -> (Lazy<String>) ->
    (Lazy<String>) -> Lazy<String> =
        { f->
            { ls1 ->
                { ls2 ->
                    Lazy { f(ls1())(ls2()) }
                }
            }
        }



// 연습문제 9-5

fun <A, B, C> lift2(f: (A) -> (B) -> C): (Lazy<A>) -> (Lazy<B>) -> Lazy<C>


fun <A, B, C> lift2(f: (A) -> (B) -> C):
    (Lazy<A>) -> (Lazy<B>) -> Lazy<C> =
        { ls1 ->
            { ls2 ->
                Lazy { f(ls1())(ls2()) }
            }
        }