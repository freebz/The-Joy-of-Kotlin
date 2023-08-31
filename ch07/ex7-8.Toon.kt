// 예제 7-8 선택적 데이터에 대해 Result.Empty를 사용하는 Toon 클래스

data class Toon private constructor (val firstName: String,
                                     val lastName: String,
                                     val email: Result<String>) {
    companion object {
        operator fun invoke(firstName: String,
                            lastName: String) =
            Toon(firstName, lastName, Result.Empty)
        operator fun invoke(firstName: String,
                            lastName: String,
                            email: String) =
            Toon(firstName, lastName, Result(email))
    }
}