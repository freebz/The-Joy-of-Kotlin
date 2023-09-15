// 예제 12-3 AbstractReader 구현

abstract class AbstractReader (private val reader: BufferedReader): Input {

    override fun readString(): Result<Pair<String, Input>> = try {
        reader.readLine().let {
            when {
                it.isEmpty() -> Result()
                else -> Result(Pair(it, this))
            }
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun readInt(): Result<Pair<Int, Input>> = try {
        reader.readLine().let {
            when {
                it.isEmpty() -> Result()
                else -> Result(Pair(it.toInt(), this))
            }
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun close(): Unit = reader.close()
}