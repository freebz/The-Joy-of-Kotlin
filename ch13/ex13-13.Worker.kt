// 예제 13-13 작업 번호를 유지하는 Worker 액터

class Worker(id: String) :
    AbstractActor<Pair<Int, Int>>(id) {
    override fun onReceive(message: Pair<Int, Int>,
                           sender: Result<Actor<Pair<Int, Int>>>) {
        sender.forEach (onSuccess =
            { a: Actor<Pair<Int, Int>> ->
                a.tell(Pair(slowFibonacci(message.first),
                    message.second), self())
        })
    }
    ...
}