// 예제 14-5 PropertyReader에 함수 추가하기

class PropertyReader(private val properties: Result<Properties>,
                     private val source: String) {

    ...

    fun readAsPropertyString(propertyName: String): Result<String> =
        readAsString(propertyName).map { toPropertyString(it) }

    companion object {
        fun toPropertyString(s: String): String = s.replace(";", "\n")
        private
        fun readPropertiesFromFile(configFileName: String): Result<Properties> =
            try {
                MethodHandles.lookup().lookupClass()
                    .getResourceAsStream(configFileName)
                    .use { inputStream ->
                        when (inputStream) {
                            null -> Result.failure(
                                "File $configFileName not found in classpath")
                            else -> Properties().let {
                                it.load(inputStream)
                                Result(it)
                            }
                        }
                }
            } catch (e: IOException) {
                Result.failure(
                    "IOException reading classpath resource $configFileName")
            } catch (e: Exception) {
                Result.failure("Exception: ${e.message}reading classpath"
                    + " resource $configFileName")
            }
        private
        fun readPropertiesFromString(propString: String): Result<Properties> =
            try {
                StringReader(propString).use { reader ->
                    val properties = Properties()
                    properties.load(reader)
                    Result(properties)
                }
            } catch (e: Exception) {
                Result.failure("Exception reading property string " +
                    "$propString: ${e.message}")
            }
        fun filePropertyReader(fileName: String): PropertyReader =
            PropertyReader(readPropertiesFromFile(fileName),
                "File: $fileName")

        fun stringPropertyReader(propString: String): PropertyReader =
            PropertyReader(readPropertiesFromString(propString),
                "String: $propString")
    }
}