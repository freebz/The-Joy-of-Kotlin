// 14.4.2 2단계: 명령형 프로그램을 좀 더 함수형으로 만들기

fun readFile2String(path: String): Result<String>

fun readDocument(rootElementName: String, stringDoc: String): Result<List<Element>>

fun toStringList(list: List<Element>, format: String): List<String>

fun <A> processList(list: List<A>)

fun getRootElementName(): Result<String>

fun getXmlFilePath(): Result<String>



// 함수 합성하고 효과 적용하기

const val format = "First Name : %s\n" +
    "\tLast Name : %s\n" +
    "\tEmail : %s\n" +
    "\tSalary : %s"

fun main(args: Array<String>) {
    val path = getXmlFilePath()
    val rDoc = path.flatMap(::readFile2String)
    val rRoot = getRootElementName()
    val result = rDoc.flatMap { doc ->
        rRoot.flatMap { rootElementName ->
            readDocument(rootElementName, doc)
        }.map { list ->
            toStringList(list, format)
        }
    }
    ...
}


result.forEach(onSuccess = { processList(it) }, onFailure = { it.printStackTrace() })



// 함수 구현하기

fun getRootElementName(): Result<String> =
    Result.of { "staff" } // 실패할 수 있는 계산을 시뮬레이션함

fun getXmlFilePath(): Result<String> =
    Result.of { "file.xml" } // <-- 경로를 조정해야 함


fun readFile2String(path: String): Result<String> = Result.of { File(path).readText() }


fun readDocument(rootElementName: String, stringDoc: String): Result<List<Element>> =
    SAXBuilder().let { builder ->
        try {
            val document = builder.build(StringReader(stringDoc))
            val rootElement = document.rootElement
            Result(List(*rootElement.getChildren(rootElementName)
                                    .toTypedArray()))
        } catch (io: IOException) {
            Result.failure("Invalid root element name '$rootElementName' "
                + "or XML data $stringDoc: ${io.message}")
        } catch (jde: JDOMException) {
            Result.failure("Invalid root element name '$rootElementName' "
                + "or XML data $stringDoc: ${jde.message}")
        } catch (e: Exception) {
            Result.failure("Unexpected error while reading XML data "
                + "$stringDoc: ${e.message}")
        }
}


fun toStringList(lislt: List<Element>, format: String): List<String> =
    list.map { e -> processElement(e, format) }

fun processElement(element: Element, format: String): String =
    String.format(format, element.getChildText("firstName"),
        element.getChildText("lastName"),
        element.getChildText("email"),
        element.getChildText("salary"))


fun <A> processList(list: List<A>) = list.forEach(::println)