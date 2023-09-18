// 예제 13-9 Manager 클래스의 생성자와 프로퍼티

class Manager(id: String, list: List<Int>,
              private val client: Actor<Result<List>>,
              private val workers: Int) : AbstractActor(id) {
    private val initial: List<Pair<Int, Int>>

    private val workList: List<Int>

    private val resultList: List<Int>
    private val managerFunction:
        (Manager) -> (Behavior) -> (Int) -> Unit

    init {
        val splitLists = list.splitAt(this.workers)
        this.initial = splitLists.first.zipWithPosition()

        this.workList = splitLists.second
        this.resultList = List()
        managerFunction = { manager ->
            { behavior ->
                { i ->
                    val result = behavior.resultList.cons(i)
                    if (result.length == list.length) {
                        this.client.tell(Result(result))
                    } else {
                        manager.context
                               .become(Behavior(behavior.workList
                                                        .tailSafe()
                                                        .getOrElse(List()), result))
                    }
                }
            }
        }
    }
}