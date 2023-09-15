// 12.1 부수 효과를 컨텍스트 안에 가둔다는 것은 무슨 뜻인가

val inverse: (Int) -> Result<Double> = { x ->
    when {
        x != 0 -> Result(1.toDouble() / x)
        else -> Result.failure("Division by 0")
    }
}


val ri: Result<Int> = ...
val rd: Result<Double> = ri.flatMap(inverse)