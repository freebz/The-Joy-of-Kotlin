// 예제 7-4 변경한 email 프로퍼티를 사용하도록 바꾼 Toon 클래스

data
class Toon private constructor (val firstName: String,
                                val lastName: String,
                                val email: Result<String>) {
    companion object {
        operator fun invoke(firstName: String,
                            lastName: String) =
            Toon(firstName, lastName,
                Result.failure("$firstName $lastName has no mail"))
        operator fun invoke(firstName: String,
                            lastName: String,
                            email: String) =
            Toon(firstName, lastName, Result(email))
    }
}