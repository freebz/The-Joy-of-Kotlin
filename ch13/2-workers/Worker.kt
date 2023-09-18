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

    private fun slowFibonacci(number: Int): Int {
        return when (number) {
            0 -> 1
            1 -> 1
            else -> slowFibonacci(number - 1) + slowFibonacci(number - 2)
        }
    }
}