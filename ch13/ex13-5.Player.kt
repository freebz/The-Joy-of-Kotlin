// 예제 13-5 Player 클래스

private class Player(id: String,
                     private val sound: String,
                     private val referee: Actor<Int>):
                        AbstractActor<Int>(id) {
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