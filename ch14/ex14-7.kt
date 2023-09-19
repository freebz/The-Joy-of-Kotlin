// 예제 14-7 JDOM으로 XML 읽기: 명령형 코틀린 버전

import org.jdom2.JDOMException
import org.jdom2.input.SAXBuilder
import java.io.File
import java.io.IOException

/**
 * 테스트하기 어렵고 예외를 던짐
 */
fun main(args: Array<String>) {
    val builder = SAXBuilder()
    val xmlFile = File("/path/to/file.xml") // 파일 경로로 바꿔야 함
    try {
        val document = builder.build(xmlFile)
        val rootNode = document.rootElement
        val list = rootNode.getChildren("staff")
        list.forEach {
            println("First Name: ${it.getChildText("firstName")}")
            println("\tLast Name: ${it.getChildText("lastName")}")
            println("\tEmail: ${it.getChildText("email")}")
            println("\tSalary: ${it.getChildText("salary")}")
        }
    } catch (io: IOException) {
        println(io.message)
    } catch (e: JDOMException) {
        println(e.message)
    }
}