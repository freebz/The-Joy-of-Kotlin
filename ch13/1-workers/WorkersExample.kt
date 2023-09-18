// 13.4.2 계산을 병렬로 수행하기

import java.util.concurrent.Semaphore

private val semaphore = Semaphore(1)
private val listLength = 20_000
private val workers = 8
private val rnd = java.util.Random(0)
private val testList = range(0, listLength).map { rnd.nextInt(35) }

fun main(args: Array<String>) {
    semaphore.acquire()
    val startTime = System.currentTimeMillis()

    val client = object: AbstractActor<Result<List<Int>>>("Client") {
        override fun onReceive(message: Result<List<Int>>,
                               sender: Result<Actor<Result<List<Int>>>>) {
            message.forEach({ processSuccess(it) },
                { processFailure(it.message ?: "Unknown error") })
            println("Total time: "
                + (System.currentTimeMillis() - startTime))
            semaphore.release()
        }
    }

    val manager = Manager("Manager", testList, client, workers)
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

// Input: [23, 14, 34, 15, 14, 30, 15, 34, 24, 29, 30, 27, 16, 14, 25, 34, 13, 3, 9, 6, 31, 31, 3, 1, 7, 5, 10, 10, 22, 2, 27, 30, 11, 17, 9, 32, 14, 19, 8, 0, 33, NIL]
// Result: [9227465, 5702887, 3524578, 3524578, 514229, 5702887, 9227465, 121393, 8, 46368, 5, 2, 3524578, 514229, 1597, 1, 5702887, 55, 28657, 2, 121393, 2, 1346269, 610, 75025, 1, 13, 1, 34, 3, 75025, 9227465, 2, 514229, 377, 233, 2, 377, 1, 89, 2, NIL]
// Total time: 12071