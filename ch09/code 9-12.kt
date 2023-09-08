// 9.5.2 지연 계산이 없으면 할 수 없는 일

// 9.5.3 지연 리스트 데이터 구조 만들기

fun main(args: Array<String>) {
    val stream = Stream.from(1)
    stream.head().forEach({ println(it) })
    stream.tail().flatMap { it.head() }.forEach({ println(it) })
    stream.tail().flatMap {
        it.tail().flatMap { it.head() }
    }.forEach({ println(it) })
}


// 1
// 2
// 3


fun main(args: Array<String>) {
    val stream = Stream.from(1)
    stream.head().forEach({ println(it) })
    stream.tail().flatMap { it.head() }.forEach({ println(it) })
    stream.tail().flatMap { it.tail().flatMap { it.head() } }.forEach({ println(it) })
    stream.head().forEach({ println(it) })
    stream.tail().flatMap { it.head() }.forEach({ println(it) })
    stream.tail().flatMap { it.tail().flatMap { it.head() } }.forEach({ println(it) })
}


// 1
// 2
// 3
// 1
// 2
// 3


public class TestStream {
    public static void main(String...args) {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
        stream.findFirst().ifPresent(System.out::println);
        stream.findFirst().ifPresent(System.out::println);
    }
}