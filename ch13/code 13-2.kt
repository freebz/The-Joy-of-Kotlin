// 13.4.4 성능 최적화하기

private fun fibonacci(number: Int): Int {
    tailrec fun fibonacci(acc1: Int, acc2: Int, x: Int): Int = when (x) {
        0 -> 1
        1 -> acc1 + acc2
        else -> fibonacci(acc2, acc1 + acc2, x - 1)
    }
    return fibonacci(0, 1, number)
}


println(testList.map { fibonacci(it) }.splitAt(40).first)


fun main(args: Array<String>) {
    semaphore.acquire()
    val startTime = System.currentTimeMillis()
    val client = object: AbstractActor<List<Int>>("Client") {
        override fun onReceive(message: List<Int>,
                               sender: Result<Actor<List<Int>>>) {
            println("Total time: "
                + (System.currentTimeMillis() - startTime))
            println("Input: ${testList.splitAt(40).first}")
            println("Result: ${message.splitAt(40).first}")
            semaphore.release()
        }
    }
    val receiver = Receiver("Receiver", client)
    val manager = Manager("Manager", testList, receiver, workers)
    manager.start()
    semaphore.acquire()
}


class Manager(
              id: String, list: List<Int>,
              private val client: Actor<Int>,
              private val workers: Int) : AbstractActor<Pair<Int, Int>>(id) {
    private val initial: List<Pair<Int, Int>>
    private val workList: List<Pair<Int, Int>>
    private val resultHeap: Heap<Pair<Int, Int>>
    private val managerFunction: (Manager) -> (Behavior) -> (Pair<Int, Int>) -> Unit
    private val limit: Int
}


managerFunction = { manager ->
    { behavior ->
        { p ->
            val result =
                streamResult(behavior.resultHeap + p,
                             behavior.expected, List())
            result.third.forEach { client.tell(it) }
            if (result.second > limit) {
                this.client.tell(-1)
            } else {
                manager.context
                        .become(Behavior(behavior.workList
                        .tailSafe()
                        .getOrElse(List()), result.first, result.second))
            }
        }
    }
}


private fun streamResult(result: Heap<Pair<Int, Int>>,
                         expected: Int, list: List<Int>):
                            Triple<Heap<Pair<Int, Int>>, Int, List<Int>> {
    val triple = Triple(result, expected, list)
    val temp = result.head
        .flatMap { head ->
            result.tail().map { tail ->
                if (head.second == expected)
                    streamResult(tail, expected + 1, list.cons(head.first))
                else
                    triple
            }
        }
    return temp.getOrElse(triple)
}


override fun onReceive(message: Pair<Int, Int>,
                       sender: Result<Actor<Pair<Int, Int>>>) {
    context.become(Behavior(workList, resultHeap, 0))
}


internal inner class Behavior
    internal constructor(internal val workList: List<Pair<Int, Int>>,
                         internal val resultHeap: Heap<Pair<Int, Int>>,
                         internal val expected: Int) :
                            MessageProcessor<Pair<Int, Int>> {
    ...
}


fun start() {
    onReceive(Pair(0, 0), self())
    sequence(initial.map { this.initWorker(it) })
        .forEach({ this.initWorkers(it) },
                 { client.tell(-1) })
}