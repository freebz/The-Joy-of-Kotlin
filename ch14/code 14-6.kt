// 14.3.5 이넘(enum) 값 읽기

fun <T> readAsType(f: (String) -> Result<T>, name: String) =
    readAsString(name).flatMap {
        try {
            f(it)
        } catch (e: Exception) {
            Result.failure<T>(
                "Invalid value while parsing property '$name': '$it'")
        }
}


inline
fun <reified T: Enum<T>> readAsEnum(name: String,
                                    enumClass: Class<T>): Result<T> {
    val f: (String) -> Result<T> = {
        try {
            val value = enumValueOf<T>(it)
            Result(value)
        } catch (e: Exception) {
            Result.failure("Error parsing property '$name': " +
                "value '$it' can't be parsed to ${enumClass.name}.")
        }
    }
    return readAsType(f, name)
}


type=SERIAL


enum class Type {SERIAL, PARALLEL}


val type = propertyReader.readAsEnum("type", Type::class.java)