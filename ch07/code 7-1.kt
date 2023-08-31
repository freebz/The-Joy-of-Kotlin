// 7.1 데이터가 없는 경우와 관련한 문제점

val goofy = toons.getOption("Goofy").flatMap { it.email }

println(goofy.getOrElse({ "No data" }))


    val toon = getName().flatMap(toons::getOption)
                        .flatMap(Toon::email)

    println(toon.getOrElse("No data"))
}


    val toon = getName().flatMap(toons::getOption)
                        .flatMap(Toon::email)

    println(toon.getOrElse("No data"})
}

fun getName(): Option<String> = try {
    validate(readLine())
} catch (e: IOException) {
    Option()
}

fun validate(name: String?): Option<String> = when {
    name?.isNotEmpty() ?: false -> Option(name)
    else -> Option()
}