// 14.2 함수와 효과 재시도하기

fun get(path: String): String =
    Random().nextInt(10).let {
        when {
            it < 8 -> throw IOException("Error accessing file $path")
            else   -> "content of $path"
        }
}

var retries = 0
var result: String? = null
(0 .. 3).forEach rt@ {
    try {
        result = get("/my/path")
        return@rt
    } catch(e: IOException) {
        if (retries < 3) {
            Thread.sleep(100)
            retries += 1
        } else {
            throw e
        }
    }
}

println(result)


fun <A, B> retry(f: (A) -> B,
                 times: Int,
                 delay: Long = 10): (A) -> Result<B>


val functionWithRetry = retry(::get, 10, 100)
functionWithRetry("/my/file.txt")
    .forEach({ println(it) }, { println(it.message) })


fun <A, B> retry(f: (A) -> B, times: Int, delay: Long = 10) = rt@ { a: A ->
    (0 .. times).fold("Not executed") { _, n ->
        try {
            print("Try $n: ")
            return@rt "Success $n: ${f(a)}"
        } catch (e: Exception) {
            Thread.sleep(delay)
            "${e.message}"
        }
    }
}


fun <A, B> retry(f: (A) -> B,
                 times: Int,
                 delay: Long = 10): (A) -> Result<B> {
    fun retry(a: A, result: Result<B>, e: Result<B>, tms: Int): Result<B> =
        result.orElse {
            when (tms) {
                0    -> e
                else -> {
                    Thread.sleep(delay)
                    // 재시도 횟수를 기록한다.
                    println("retry ${times - tms}")
                    retry(a, Result.of { f(a) }, result, tms - 1)
                }
            }
    }
    return { a -> retry(a, Result.of { f(a) }, Result(), times - 1)}
}


fun show(message: String) =
    Random().nextInt(10).let {
        when {
            it < 8 -> throw IllegalStateException("Failure !!!")
            else -> println(message)
        }
}

fun main(args: Array<String>) {
    retry(::show, 10, 20)("Hello, World!").forEach(onFailure =
        { println(it.message) })
}