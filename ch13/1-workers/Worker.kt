class Worker(id: String) : AbstractActor<Int>(id) {
    override fun onReceive(message: Int, sender: Result<Actor<Int>>) {
        sender.forEach (onSuccess = { a: Actor<Int> ->
            a.tell(slowFibonacci(message), self())
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