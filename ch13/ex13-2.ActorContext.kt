// 예제 13-2 ActorContext와 MessageProcessor 인터페이스

interface ActorContext<T> {
    fun behavior(): MessageProcessor<T>
    fun become(behavior: MessageProcessor<T>)
}

interface MessageProcessor<T> {
    fun process(message: T, sender: Result<Actor<T>>)
}