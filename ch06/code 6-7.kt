// 6.4.3 Option 합성 처리하기

// 연습문제 6-4

fun <B> flatMap(f: (A) -> Option<B>): Option<B>


fun <B> flatMap(f: (A) -> Option<B>): Option<B> = map(f).getOrElse{None}



// 연습문제 6-5

fun orElse(default: () -> Option<A>): Option<A>


fun orElse(default: () -> Option<@UnsafeVariance A>): Option<A> =
    map { _ -> this }.getOrElse(default)

fun orElse(default: () -> Option<@UnsafeVariance A>): Option<A> =
    map { this }.getOrElse(default)



// 연습문제 6-6

fun filter(p: (A) -> Boolean): Option<A>


fun filter(p: (A) -> Boolean): Option<A> =
    flatMap { x -> if (p(x)) this else None }