// 예제 13-4 referee 객체 생성하기

val referee = object : AbstractActor<Int>("Referee") {
    override fun onReceive(message: Int, sender: Result<Actor<Int>>) {
        println("Game ended after $message shots")
    }
}