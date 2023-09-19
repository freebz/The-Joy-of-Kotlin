// 14.3 파일에서 프로퍼티 읽기

// 14.3.1 프로퍼티 파일 읽기

// 14.3.2 프로퍼티를 문자열로 읽기

properties.map { it.getProperty("name") }


fun readProperty(name: String) =
    properties.flatMap {
        Result.of {
            it.getProperty(name)
        }
}


fun main(args: Array<String>) {
    val propertyReader = PropertyReader("/config.properties")
    propertyReader.readProperty("host")
        .forEach(onSuccess = { println(it) }, onFailure = { println(it) })
    propertyReader.readProperty("name")
        .forEach(onSuccess = { println(it) }, onFailure = { println(it) })
    propertyReader.readProperty("year")
        .forEach(onSuccess = { println(it) }, onFailure = { println(it) })
}