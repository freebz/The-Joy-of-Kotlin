// 예제 13-7 탁구 예제

private val semaphore = Semaphore(1)

fun main(args: Array<String>) {
    val referee = object : AbstractActor<Int>("Referee") {
        override fun onReceive(message: Int, sender: Result<Actor<Int>>) {
            println("Game ended after $message shots")
            semaphore.release()
        }
    }

    val player1 = player("Player1", "Ping", referee)
    val player2 = player("Player2", "Pong", referee)
    semaphore.acquire()
    player1.tell(1, Result(player2))
    semaphore.acquire()
    // 세마포어를 얻어야 메인 스레드가 끝난다.
}


// Ping - 1
// Pong - 2
// Ping - 3
// Pong - 4
// Ping - 5
// Pong - 6
// Ping - 7
// Pong - 8
// Ping - 9
// Pong - 10
// Game ended after 10 shots