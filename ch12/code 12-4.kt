// 12.2.2 파일 읽기

// 연습문제 12-3

fun readPersonFromFile(path: String): Result<List<Person>> =
    FileReader(path).map {
        it.use {
            Stream.unfold(it, ::person).toList()
        }
    }

fun main(args: Array<String>) {
    val path = "<path>/data.txt"
    readPersonFromFile(path).forEach({ list: List<Person> ->
        list.forEach(::println)
    }, onFailure = ::println)
}


fun readPersonFromFile(path: String): Result<List<Person>> =
    FileReader(path).map { input1 ->
        input1.use { input2 ->
            Stream.unfold(input2, ::person).toList()
        }
    }