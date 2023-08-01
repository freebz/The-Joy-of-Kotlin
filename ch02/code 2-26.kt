// 2.10 비검사 예외

val num: Int = try {
    args[0].toInt()
} catch (e: Exception) {
    0
} finally {
    // 이 블록 안에 있는 코드는 항상 실행됨
}