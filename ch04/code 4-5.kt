// 4.2 꼬리 호출제거

fun toString(list: List<Char>): String {
    fun toString(list: List<Char>, s: String): String =
        if (list.isEmpty())
            s
        else
            toString(list.subList(1, list.size), append(s, list[0]))
    return toString(list, "")
}


fun toStringCorec2(list: List<Char>): String {
    var s = ""
    for (c in list) s = append(s, c)
    return s
}