// 예제 6-1 Option 데이터 타입

sealed class Option<out A> {
    abstract fun isEmpty(): Boolean

    internal object None: Option<Nothing>() {
        override fun isEmpty() = true
        override fun toString(): String = "None"
        override fun equals(other: Any?): Boolean =
            other === None
        override fun hashCode(): Int = 0
    }

    internal data class Some<out A>(internal val value: A) : Option<A>() {
        override fun isEmpty() = false
    }

    companion object {
        operator fun <A> invoke(a: A? = null): Option<A>
            = when (a) {
                null -> None
                else -> Some(a)
            }
    }
}