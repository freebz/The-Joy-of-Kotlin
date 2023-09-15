// 11.2.2 맵 확장하기

fun <B> foldLeft(identity: B, f: (B) ->
    (MapEntry<@UnsafeVariance K, V>) -> B, g: (B) -> (B) -> B): B =
        delegate.foldLeft(identity, { b ->
            { me: MapEntry<K, V> ->
                f(b)(me)
            }
    }, g)



// 연습문제 11-3

// T
override fun <B> foldInOrder(identity: B, f: (B) -> (A) -> (B) -> B): B =
    f(left.foldInOrder(identity, f))(value)(right.foldInOrder(identity, f))

// T
override fun <B> foldInReverseOrder(identity: B,
    f: (B) -> (A) -> (B) -> B): B =
        f(right.foldInReverseOrder(identity, f))(value)(left
            .foldInReverseOrder(identity, f))

// Empty
override fun <B> foldInOrder(identity: B, f: (B) -> (A) -> (B) -> B): B = identity
override fun <B> foldInReverseOrder(identity: B, f: (B) -> (A) -> (B) -> B): B = identity

// Map
fun values(): List<V> =
    sequence(delegate.foldInReverseOrder(List<Result<V>>()) { lst1 ->
        { me ->
            { lst2 ->
                lst2.concat(lst1.cons(me.value))
            }
        }
    }).getOrElse(List())