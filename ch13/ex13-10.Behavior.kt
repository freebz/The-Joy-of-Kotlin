// 예제 13-10 Behavior 내부 클래스

internal inner class Behavior
    internal constructor(
        internal val workList: List<Int>,
        internal val resultList: List<Int>) : MessageProcessor<Int> {
    override fun process(message: Int,
                         sender: Result<Actor<Int>>) {

        managerFunction(this@Manager)(this@Behavior)(message)
        sender.forEach(onSuccess = { a: Actor<Int> ->
            workList.headSafe()
                    .forEach({ a.tell(it, self()) }) { a.shutdown() }
        })
    }
}