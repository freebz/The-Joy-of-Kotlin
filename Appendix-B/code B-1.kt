// B.1 속성 기반 테스트를 사용해야 하는 이유는 무엇인가

// 예제 00
fun maxMultiple(multiple: Int, list: List<Int>): Int {
    var result = 0
    for (i in 1 until list.size) {
        if (list[i] / multiple * multiple == list[i] && list[i] > result) {
            result = list[i]
        }
    }
    return result
}