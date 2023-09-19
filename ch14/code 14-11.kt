// 14.4.5 5단계: 원소 처리 함수를 파라미터로 만들기

fun <T> readXmlFile(sPath: () -> FilePath,
                    sRootName: () -> ElementName,
                    function: (Element) -> T,
                    effect: (List<T>) -> Unit): () -> Unit {
    val path = sPath().value
    val rDoc = path.flatMap(::readFile2String)
    val rRoot = sRootName().value
    val result = rDoc.flatMap { doc ->
        rRoot.flatMap { rootElementName ->
            readDocument(rootElementName, doc) }
                .map { list -> list.map(function) }
    }
    return {
        result.forEach(onSuccess = { effect(it) },
                       onFailure = { throw it })
    }
}


const val format = "First Name : %s\n" +
                   "\tLast name : %s\n" +
                   "\tEmail : %s\n" +
                   "\tSalary : %s"

private val elementNames =
    List("firstName", "lastName", "email", "salary")

private fun processElement(element: Element): String =
    String.format(format, *elementNames.map { element.getChildText(it) }
        .toArrayList()
        .toArray())

fun main(args: Array<String>) {
    val program = readXmlFile(::getXmlFilePath,
                              ::getRootElementName,
                              ::processElement,
                              ::processList)
    ...
}