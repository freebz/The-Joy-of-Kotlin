// 예제 13-11 Manager의 유틸리티 함수들

class Manager(id: String, list: List<Int>, ...
    ...

    fun start() {
        onReceive(0, self())
        sequence(initial.map { this.initWorker(it) })
            .forEach(onSuccess = { this.initWorkers(it) },
                onFailure =
                    { this.tellClientEmptyResult(it.message ?: "Unknown error") })
    }

    private fun initWorker(t: Pair<Int, Int>):
        Result<() -> Unit> =
            Result({ Worker("Worker " + t.second).tell(t.first, self()) })

    private fun initWorkers(lst: List<() -> Unit>) {
        lst.forEach { it() }
    }

    private fun tellClientEmptyResult(string: String) {
        client.tell(Result.failure("$string caused by empty input list."))
    }

    override fun onReceive(message: Int, sender: Result<Actor<Int>>) {
        context.become(Behavior(workList, resultList))
    }
    ...
)