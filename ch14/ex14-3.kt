// 예제 14-3 구체적인 오류 메시지 만들기

// 이제 프로퍼티를 private으로 만들 수 있다
private val properties: Result<Properties> =
    try {
        MethodHandles.lookup().lookupClass()
            .getResourceAsStream(configFileName)
            .use { inputStream ->
                when (inputStream) {
                    null ->
                        Result.failure("File $configFileName not found in classpath")
                    else -> Properties().let {
                        it.load(inputStream)
                        Result(it)
                    }
                }
        }
} catch (e: Exception) {
        Result.failure("Exception: ${e.message} " +
        " while reading classpath resource $configFileName")
}