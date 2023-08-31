// 예제 6-3 널이 될 수 있는 타입을 활용해 전형적인 코틀린 스타일로 작성한 경우

fun main(args: Array<String>) {
    val toons: Map<String, Toon> = mapOf(
        "Mickey" to Toon("Mickey", "Mouse", "mickey@disney.com"),
        "Minnie" to Toon("Minnie", "Mouse"),
        "Donald" to Toon("Donald", "Duck", "donald@disney.com"))
    
    val mickey = toons["Mickey"]?.email ?: "No data"
    val minnie = toons["Minnie"]?.email ?: "No data"
    val goofy = toons["Goofy"]?.email ?: "No data"

    println(mickey)
    println(minnie)
    println(goofy)
}