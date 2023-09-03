// 8.6.3 부분 리스트를 병렬로 처리하기

// 연습문제 8-23

fun <B> parFoldLeft(es: ExecutorService,
                    identity: B,
                    f: (B) -> (A) -> B,
                    m: (B) -> (B) -> B): Result<B>


divide(1024)


fun <B> parFoldLeft(es: ExecutorService,
                    identity: B,
                    f: (B) -> (A) -> B,
                    m: (B) -> (B) -> B): Result<B> =
    try {
        val result: List<B> = divide(1024).map { list: List<A> ->
            es.submit<B> { list.foldLeft(identity, f) }
        }.map<B> { fb ->
            try {
                fb.get()
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            } catch (e: ExecutionException) {
                throw RuntimeException(e)
            }
        }
        Result(result.foldLeft(identity, m))
    } catch (e: Exception) {
        Result.failure(e)
    }

// Duration serial 1 thread: 140933
// Duration parallel 2 threads: 70502
// Duration parallel 4 threads: 36337
// Duration parallel 8 threads: 20253



// 연습문제 8-24

fun <B> parMap(es: ExecutorService, g: (A) -> B): Result<List<B>>


fun <B> parMap(es: ExecutorService, g: (A) -> B): Result<List<B>> =
    try {
        val result = this.map { x ->
            es.submit<B> { g(x) }
        }.map<B> { fb ->
            try {
                fb.get()
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            } catch (e: ExecutionException) {
                throw RuntimeException(e)
            }
        }
        Result(result)
    } catch (e: Exception) {
        Result.failure(e)
    }