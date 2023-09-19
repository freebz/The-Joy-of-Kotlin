// 14.4.6 6단계: 엘리먼트 이름의 오류 처리하기

fun <T> readXmlFile(sPath: () -> FilePath,
                    sRootName: () -> ElementName,
                    function: (Element) -> Result<T>,
                    effect: (List<T>) -> Unit): () -> Unit {
    val path = sPath().value
    val rDoc = path.flatMap(::readFile2String)
    val rRoot = sRootName().value
    val result = rDoc.flatMap { doc ->
        rRoot.flatMap { rootElementName ->
                readDocument(rootElementName, doc) }
            .flatMap { list ->
                sequence(list.map(function)) }
    }
    return {
        result.forEach(onSuccess = { effect(it) },
                       onFailure = { throw it })
    }
}
...


/**
 * 이름 붙은 자식 엘리먼트의 텍스트 내용을 반환한다.
 * 이름에 해당하는 자식이 없으면 null을 반환한다.
 * <code>getChild().getText()</code>를 호출하면 NullPointerException이 발생할 수 있는데,
 * 그 대신 이 함수를 사용하면 편리하다.
 * 
 * @param cname 자식 엘리먼트 이름
 * @return text 이름에 해당하는 자식 엘리먼트의 내용. 해당하는 자식이 없는 경우 null
 */
public String getChildText(final String cname) {
    final Element child = getChild(cname);
    if (child == null) {
        return null;
    }
    return child.getText();
}


fun processElement(element: Element): Result<String> =
    try {
        Result(String.format(format, *elementNames.map { getChildText(element, it) }
                                                  .toArrayList()
                                                  .toArray()))
    } catch (e: Exception) {
        Result.failure(
            "Exception while formatting element. " +
            "Probable cause is a missing element name in element " +
            "list $elementNames")
    }

fun getChildText(element: Element,
                 name: String): String =
    element.getChildText(name) ?:
        "Element $name is not a child of ${element.name}"


fun main(args: Array<String>) {
    val program = readXmlFile(::getmlFilePath,
                              ::getRootElementName,
                              ::processElement,
                              ::processList)
    try {
        program()
    } catch (e: Exception) {
        println("An exception occurred: ${e.message}")
    }
}