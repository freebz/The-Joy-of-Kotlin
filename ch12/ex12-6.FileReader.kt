// 예제 12-6 FileReader 구현

class FileReader private constructor(private val reader: BufferedReader) :
                                     AbstractReader(reader), AutoCloseable {
    override fun close() {
        reader.close()
    }

    companion object {
        operator fun invoke(path: String): Result<Input> = try {
            Result(FileReader(File(path).BufferedReader()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}