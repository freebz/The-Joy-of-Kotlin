// 6.4.2 선택적 값에 함수 적용하기

// Option
abstract fun <B> map(f: (A) -> B): Option<B>

// None
override fun <B> map(f: (Nothing) -> B): Option<B> = None

// Some
override fun <B> map(f: (A) -> B): Option<B> = Some(f(value))


// Option
fun <B> map(f: (A) -> B): Option<B> = when (this) {
    is None -> None
    is Some -> Some(f(value))
}