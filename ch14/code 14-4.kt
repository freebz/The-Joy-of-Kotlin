// 14.3.3 더 나은 오류 메시지 만들기

// Java code
public InputStream getResourceAsStream(String name) {
    URL url = getResource(name);
    try {
        return url != null ? url.openStream() : null;
    } catch (IOException e) {
        return null;
    }
}


val year: Result<String> = propertyReader.readProperty(properties, "year")


data class Person(val id: Int, val firstName: String, val lastName: String)

fun main(args: Array<String>) {
    val propertyReader = PropertyReader("/config.properties")
    val person = propertyReader.readProperty("id")
        .map(String::toInt)
        .flatMap { id ->
            propertyReader.readProperty("firstName")
                .flatmap { firstName ->
                    propertyReader.readProperty("lastName")
                        .map { lastname -> Person(id, firstName, lastName) }
                }
    }
    person.forEach(onSuccess = { println(it) }, onFailure = { println(it) })
}


fun readProperty(name: String) =
    properties.flatMap {
        Result.of {
            it.getProperty(name)
        }.mapFailure("Property \"$name\" no found")
}


propertyReader.readProperty("id")
    .map(String::toInt)
    .mapFailure("Invalid format for property \"id\": ???")


fun readAsString(name: String) =
    properties.flatMap {
        Result.of {
            it.getProperty(name)
        }.mapFailure("Property \"$name\" no found")
}


fun readAsInt(name: String): Result<Int> =
    readAsString(name).flatMap {
        try {
            Result(it.toInt())
        } catch (e: NumberFormatException) {
            Result.failure<Int>(
                "Invalid value while parsing property '$name' to Int: '$it'")
        }
}


val person = propertyReader.readAsInt("id")
    .flatMap { id ->
        propertyReader.readAsString("firstName")
            .flatMap { firstName ->
                propertyReader.readAsString("lastName")
                    .map { lastName -> Person(id, firstName, lastName) }
            }
}

person.forEach(onSuccess = { println(it) }, onFailure = { println(it) })