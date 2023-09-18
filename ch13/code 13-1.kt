// 13.4.3 결과 순서 변경하기

class Manager(id: String, list: List<Int>,
        private val client: Actor<Result<List<Int>>>,
            private val workers: Int) : AbstractActor<Pair<Int, Int>>(id) {

    private val initial: List<Pair<Int, Int>>
    private val workList: List<Pair<Int, Int>>
    private val resultHeap: Heap<Pair<Int, Int>>
    // private val managerFunction: (Manager) -> (Behavior) -> (Int) -> Unit
    private val managerFunction:
            (Manager) -> (Behavior) -> (Pair<Int, Int>) -> Unit

    init {
        val splitLists = list.zipWithPosition().splitAt(this.workers)
        this.initial = splitLists.first
        this.workList = splitLists.second
        this.resultHeap = Heap(Comparator {
            p1: Pair<Int, Int>, p2: Pair<Int, Int> -> p1.second.compareTo(p2.second)
        })

        managerFunction = {
            { behavior ->
                { p ->
                    val result = behavior.resultHeap + p
                    if (result.size == list.length) {
                        this.client.tell(Result(result.toList()
                            .map { it.first }))
                    } else {
                        ...
                    }
                }
            }
        }
    }

    internal inner class Behavior
        internal
        constructor(internal val workList: List<Pair<Int, Int>>,
                    internal val resultHeap: Heap<Pair<Int, Int>>):
                        MessageProcessor<Pair<Int, Int>> {
        override
        fun process(message: Pair<Int, Int>,
                    sender: Result<Actor<Pair<Int, Int>>>) {
            managerFunction(this@Manager)(this@Behavior)(message)
            sender.forEach(onSuccess = { a: Actor<Pair<Int, Int>> ->
                workList.headSafe()
                    .forEach({ a.tell(it, self()) }) { a.shutdown() }
            })
        }
    }

    fun start() {
        onReceive(Pair(0, 0), self())
        sequence(initial.map { this.initWorker(it) })
            .forEach({ this.initWorkers(it) },
                { this.tellClientEmptyResult(it.message ?: "Unknown error") })
    }

    private fun initWorker(t: Pair<Int, Int>): Result<() -> Unit> =
        Result({ Worker("Worker " + t.second)
            .tell(Pair(t.first, t.second), self()) })

    override fun onReceive(message: Pair<Int, Int>,
                           sender: Result<Actor<Pair<Int, Int>>>) {
        context.become(Behavior(workList, resultHeap))
    }
}