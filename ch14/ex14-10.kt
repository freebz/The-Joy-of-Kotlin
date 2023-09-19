// 예제 14-10 XML 리더를 테스트하기 위한 클라이언트 프로그램

import com.fpinkotlin.common.List
import com.fpinkotlin.common.Result

fun <A> processList(list: List<A>) = list.forEach(::println)

fun getRootElementName(): Result<String> =
    Result.of { "staff" } // 실패할 수 있는 계산을 시뮬레이션한다.

fun getXmlFilePath(): Result<String> =
    Result.of { "/path/to/file.xml" }  // <-- 실제로는 정상 XML 파일 경로로 변경해야 한다.

private val format = Pair("First Name : %s\n" +
                          "\tLast Name: %s\n" +
                          "\tEmail : %s\n" +
                          "\tSalary : %s", List("firstname", "lastName", "email", "salary"))

fun main(args: Array<String>) {
    val program = readXmlFile( { getXmlFilePath() },
                               { getRootElementName() },
                               format, { processList(it) })
    program()
}