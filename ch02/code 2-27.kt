// 2.11 사용한 자원 자동으로 닫기

import java.io.File  // 임포트해야만 File을 사용할 수 있음

File("myFile.txt").inputStream()
                  .use {
                      it.bufferedReader()
                        .lineSequence()
                        .forEach (::println)
                  }


val lines: Sequence<String> = File("myFile.txt")
    .inputStream()
    .use {
        it.bufferedReader()
          .lineSequence()
    }
lines.forEach(::println)


val lines: List<String> = File("myFile.txt")
    .inputStream()
    .use {
        it.bufferedReader()
          .lineSequence()
          .toList()
    }

lines.forEach(::println)


File("myFile.txt").forEachLine { println (it) }


File("myFile.txt").useLines { it.forEach(::println) }