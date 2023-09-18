// 예제 13-6 player 함수

fun player(id: String,
           sound: String,
           referee: Actor<Int>) =
                object : AbstractActor<Int>(id) {
    override fun onReceive(message: Int, sender: Result<Actor<Int>>) {
        println("$sound - $message")
        if (message >= 10) {
            referee.tell(message, sender)
        } else {
            sender.forEach(
                { actor: Actor<Int> ->
                    actor.tell(message + 1, self())
                },
                { referee.tell(message, sender) }
            )
        }
    }
}