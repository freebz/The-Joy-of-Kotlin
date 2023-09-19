// 14.4.3 3단계: 프로그램을 더욱더 함수형으로 만들기

fun toStringList(list: List<Element>, format: Pair<String, List<String>>): List<String> =
    list.map { e -> processElement(e, format) }

fun processElement(element: Element, format: Pair<String, List<String>>): String {
    val formatString = format.first
    val parameters = format.second.map { element.getChildText(it) }
    return String.format(formatString, *parameters.toArrayList().toArray())
}