// 예제 12-2 데이터 입력을 위한 인터페이스

interface Input: Closeable {
    fun readString(): Result<Pair<String, Input>>
    fun readInt(): Result<Pair<Int, Input>>
    fun readString(message: String): Result<Pair<String, Input>> = readString()
    fun readInt(message: String): Result<Pair<Int, Input>> = readInt()
}