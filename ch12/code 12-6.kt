// 12.4.3 I/O 조합하기

// 연습문제 12-4

operator fun plus(io: IO): IO


operator fun plus(io: IO): IO = IO {
    f()
    io.f()
}


companion object {
    val empty: IO = IO {}
}


fun getName() = "Mickey"

val instruction1 = IO { print("Hello, ") }
val instruction2 = IO { print(getName()) }
val instruction3 = IO { print("!\n") }

val script: IO = instruction1
               + instruction2
               + instruction3

script()


instruction1.plus(instruction2).plus(instruction3)()


val script = List(
    IO { print("Hello, ") },
    IO { print(getName()) },
    IO { print("!\n") }
)


val program: IO = script.foldRight(IO.empty) { io -> { io + it } }

val program: IO = script.foldLeft(IO.empty) { acc -> { acc + it } }

program()