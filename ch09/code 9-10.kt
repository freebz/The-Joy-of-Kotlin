// 9.5 추가 지연 합성

fun <A> lazyComposition(): Lazy<A> =
    Lazy { <anything producing an A> }