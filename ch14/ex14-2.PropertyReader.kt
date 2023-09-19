// 예제 14-2 프로퍼티 파일 읽기

class PropertyReader(configFileName: String) {

    internal val properties: Result<Properties> =
        Result.of {
            MethodHandles.lookup().lookupClass()
                .getResourceAsStream(configFileName)
                .use { inputStream ->
                    Properties().let {
                        it.load(inputStream)
                        it
                    }
            }
    }
}

fun main(args: Array<String>) {
    val propertyReader = PropertyReader("/config.properties")
    propertyReader.properties.forEach(onSuccess =
        { println(it) }, onFailure = { println(it) })
}