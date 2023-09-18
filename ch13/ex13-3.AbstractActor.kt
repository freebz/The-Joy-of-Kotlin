// 예제 13-3 AbstractActor 구현

abstract class AbstractActor<T>(protected val id: String) : Actor<T> {

    override val context: ActorContext<T> = object: ActorContext<T> {
        var behavior: MessageProcessor<T> = object: MessageProcessor<T> {
            override fun process(message: T, sender: Result<Actor<T>>) {
                onReceive(message, sender)
            }
        }
        @Synchronized
        override
        fun become(behavior: MessageProcessor<T>) {
            this.behavior = behavior
        }
        override fun behavior() = behavior
    }

    private val executor: ExecutorService =
        Executors.newSingleThreadExecutor(DaemonThreadFactory())
    abstract fun onReceive(message: T, sender: Result<Actor<T>>)

    override fun self(): Result<Actor<T>> {
        return Result(this)
    }

    override fun shutdown() {
        this.executor.shutdown()
    }

    @Synchronized
    override fun tell(message: T, sender: Result<Actor<T>>) {
        executor.execute {
            try {
                context.behavior().process(message, sender)
            } catch (e: RejectedExecutionException) {
                /*
                * 이 예외는 액터가 중단되면서 모든 대기중이던 작업이
                * 취소됐다는 뜻이므로 아마 정상일 것이다.
                */
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }

}