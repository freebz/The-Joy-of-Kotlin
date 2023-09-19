// 14.1 단언문과 데이터 검증

fun inverse(x: Int): Double = 1.0 / x


fun inverse(x: Int): Double {
    assert(x == 0)
    return 1.0 / x
}


if (!Thread.currentThread().javaClass.desiredAssertionStatus()) {
    throw RuntimeException("Asserts must be enabled!!!")
}


fun inverse(x: Int): Double = when (x) {
    0    -> throw IllegalArgumentException("div. By 0")
    else -> 1.0 / x
}


fun inverse(x: Int): Result<Double> = when (x) {
    0    -> Result.failure("div. By 0")
    else -> Result(1.0 / x)
}


class Person private constructor(val id: Int,
                                 val firstName: String,
                                 val lastName: String) {
    companion object {
        operator
        fun invoke(id: Int?,
                   firstName: String?,
                   lastName: String?): Person = Person(id, firstName, lastName)
    }
}


val person = Person(rs.getInt("personId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"))


class Person private constructor(val id: Int,
                                 val firstName: String,
                                 val lastName: String) {
    companion object {
        operator
        fun invoke(id: Int?, firstName: String?, lastName: String?) =
            Person(assertPositive(id, "null or negative id"),
                   assertValidName(firstName, "invalid first name"),
                   assertValidName(lastName, "invalid last name"))
        private fun assertPositive(i: Int?,
                                   message: String): Int = when (i) {
            null -> throw IllegalStateException(message)
            else -> i
        }
        private fun assertValidName(name: String?,
                                    message: String): String = when {
            name == null
                || name.isEmpty()
                || name[0].toInt() < 65
                || name[0].toInt() > 91 ->
                    throw IllegalStateException(message)
            else -> name
        }
    }
}


fun isPositive(i: Int?): Boolean = i != null && i > 0

fun isValidName(name: String?): Boolean =
    name != null && name[0].toInt() >= 65 && name[0].toInt() <= 91


class Person private constructor(val id:Int,
                                 val firstName: String,
                                 val lastName: String) {
    companion object {
        fun of(id: Int, firstName: String, lastName: String) =
            Result.of (::isPositive, id, "Negative id").flatmap { validId ->
                Result.of(::isValidName, firstName, "Invalid first name")
                    .flatMap { validFirstName ->
                        Result.of(::isValidName, lastName, "Invalid last name")
                            .map { validLastName ->
                                Person(validId, validFirstName, validLastName)
                            }
                    }
            }
    }
}


fun assertPositive(i: Int, message: String): Result<Int> =
    Result.of(::isPositive, i, message)

fun assertValidName(name: String, message: String): Result<String> =
    Result.of(::isValidName, name, message)


fun of(id: Int, firstName: String, lastName: String) =
    assertPositive(id, "Negative id")
        .flatMap { validId ->
            assertValidName(firstName, "Invalid first name")
                .flatMap { validFirstName ->
                    assertValidName(lastName, "Invalid last name")
                        .map { validLastName ->
                            Person(validId, validFirstname, validLastName)
                        }
                }
        }