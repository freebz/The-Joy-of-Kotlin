// 13.4.4 성능 최적화하기

import java.util.concurrent.Semaphore

private val semaphore = Semaphore(1)
private val listLength = 50_000
private val workers = 8
private val rnd = java.util.Random(0)
private val testList = range(0, listLength).map { rnd.nextInt(35) }

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

fun processFailure(message: String) {
    println(message)
}

fun processSuccess(lst: List<Int>) {
    println("Input: ${testList.splitAt(40).first}")
    println("Result: ${lst.splitAt(40).first}")
}


main(arrayOf())

// Total time: 973
// Input: [31, 5, 1, 27, 19, 0, 30, 33, 19, 27, 16, 19, 32, 1, 10, 14, 6, 3, 7, 23, 22, 20, 8, 26, 14, 8, 30, 32, 19, 16, 12, 34, 7, 0, 25, 21, 23, 30, 17, 10, 11, NIL]
// Result: [2178309, 5702887, 1346269, 1, 6765, 317811, 1, 8, 6765, 317811, 1597, 6765, 3524578, 1, 89, 610, 13, 3, 21, 46368, 28657, 10946, 34, 196418, 610, 34, 1346269, 3524578, 6765, 1597, 233, 9227465, 21, 1, 121393, 17711, 46368, 1346269, 2584, 89, 144, NIL]