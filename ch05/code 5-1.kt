// 5.1 데이터 컬렉션을 분류하는 방법

// 5.2 리스트의 여러 유형

// 5.3 리스트의 상대적 예상 성능

// 5.3.1 시간 복잡도와 공간 복잡도를 서로 맞바꾸기

// 5.3.2 제자리 상태 변이 피하기

// 5.4 코틀린에서 사용할 수 있는 리스트의 종류

// 5.4.1 영속적인 데이터 구조 사용하기

// 5.4.2 불변이며 영속적인 단일 연결 리스트 구현하기

open class Pair<A, B>(val first: A, val second: B)

class List<A>(val head: A, val tail: List<A>): Pair<A, List<A>>(head, tail)


open class List<A>

object Nil : List<Nothing>()

class Cons<A>(private val head: A, private val tail: List<A>): List<A>()


sealed class List<A> {
    internal object Nil: List<Nothing>()
    internal class Cons<A>(private val head: A, private val tail: List<A>): List<A>()
}


val list: List<Int> = List(1, 2, 3)