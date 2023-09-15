// 12.2 데이터 읽기

// 12.2.1 콘솔에서 데이터 읽기

// 연습문제 12-2

data class Person (val id: Int, val firstName: String, val lastName: String)

fun person(input: Input): Result<Pair<Person, Input>>


import com.fpinkotlin.common.List
import com.fpinkotlin.common.Stream

fun readPersonsFromConsole(): List<Person> =
    Stream.unfold(ConsoleReader(), ::person).toList()

fun main(args: Array<String>) {
    readPersonsFromConsole().forEach(::println)
}


fun person(input: Input): Result<Pair<Person, Input>> =
    input.readInt("Enter ID:").flatMap { id ->
        id.second.readString("Enter first name:")
            .flatMap { firstName ->
                firstName.second.readString("Enter last name:")
                    .map { lastName ->
                        Pair(Person(id.first,
                                    firstName.first,
                                    lastName.first), lastName.second)
                    }
            }
    }


// pseudo code
for {
    id in input.readInt("Enter ID:")
    firstName in id.second.readString("Enter first name:")
    lastName in firstName.second.readString("Enter last name:")
} return Pair(Person(id.first, firstName.first, lastName.first), lastName.second)


a.flatMap { b ->
    flatMap { c ->
        map { d ->
            getSomething(a, b, c, d)
        }
    }
}


a.flatMap { b ->
    flatMap { c ->
        flatMap { d ->
            getSomething(a, b, c, d)
        }
    }
}