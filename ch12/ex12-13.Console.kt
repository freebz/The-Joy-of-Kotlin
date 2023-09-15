// 예제 12-13 Console 클래스의 스택 안전한 버전

object Console {
    private val br = BufferedReader(InputStreamReader(System.`in`))

    /**
     * readLine을 val 함수로 구현하는 방법
     */
    val readLine2: () -> IO<String> = {
        IO.Suspend {
            try {
                br.readLine()
            } catch (e: IOException) {
                throw IllegalStateException(e)
            }
        }
    }

    /**
     * readLine을 더 간단하게 구현한다. 이름 충돌로 인해 함수 참조를 쓸 수 없다.
     * 함수 참조를 쓰고 싶으면 fun 함수와 val 함수의 이름을 다르게 만들어라.
     */
    val readLine = { readLine() }

    /**
     * fun으로 구현한 readLine
     */
    fun readLine(): IO<String> = IO.Suspend {
        try {
            br.readLine()
        } catch (e: IOException) {
            throw IllegalStateException(e)
        }
    }

    /**
     * val로 구현한 printLine
     */
    val printLine: (String) -> IO<Unit> = { s: Any ->
        IO.Suspend {
            println(s)
        }
    }

    /**
     * fun으로 구현한 printLine
     */
    fun printLine(s: Any): IO<Unit> = IO.Suspend { println(s) }

    /**
     * print 함수. kotlin.io.print를 전체 이름으로 썼다는 점에 유의하라.
     */
    fun print(s: Any): IO<Unit> = IO.Suspend { kotlin.io.print(s) }
    
}