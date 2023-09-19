// 14.4.7 7단계: 예전 명령형 코드를 추가로 개선하는 방법

val processElement: (List<String>) -> (String) -> (Element) -> Result<String> =
    { elementNames ->
        { format ->
            { element ->
                try {
                    Result(String.format(format,
                        *elementNames.map { getChildText(element, it) }
                                     .toArrayList()
                                     .toArray()))
                } catch (e: Exception) {
                    Result.failure("Exception while formatting element. " +
                        "Probable cause is a missing element name in" +
                        " element list $elementNames")
                }
            }
        }
    }


fun main(args: Array<String>) {
    fun <A> processList(list: List<A>) = list.forEach(::println)

    // 실패할 수도 있는 계산을 시뮬레이션한다.
    fun getRootElementName(): ElementName = ElementName("staff")

    fun getXmlFilePath(): FilePath =
        FilePath("/path/to/file.xml") // <-- adjust path

    val format = "First Name : %s\n" +
                 "\tLast Name : %s\n" +
                 "\tEmail : %s\n" +
                 "\tSalary : %s"
    
    val elementNames =
        List("firstName", "lastName", "email", "salary")

    val program = readXmlFile(::getXmlFilePath,
                              ::getRootElementName,
                              processElement(elementNames)(format),
                              ::processList)

    try {
        program()
    } catch (e: Exception) {
        println("An exception occurred: ${e.message}")
    }
}