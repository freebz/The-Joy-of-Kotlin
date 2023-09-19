// 예제 14-9 완전히 함수형인 XML 리더 프로그램

import com.fpinkotlin.common.List
import com.fpinkotlin.common.Result
import org.jdom2.Element
import org.jdom2.JDOMException
import org.jdom2.input.SAXBuilder
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.StringReader

fun readXmlFile(sPath: () -> Result<String>,
                sRootName: () -> Result<String>,
                format: Pair<String, List<String>>,
                effect: (List<String>) -> Unit): () -> Unit {

    val path = spath()
    val rDoc = path.flatMap(::readFile2String)
    val rRoot = sRootName()
    val result = rDoc.flatMap { doc ->
        rRoot.flatMap { rootElementName ->
            readDocument(rootElementName, doc) }
                .map { list -> toStringList(list, format) }
    }

    return {
        result.forEach(onSuccess = { effect(it) },
                       onFailure = { it.printStackTrace() })
    }
}

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

fun toStringList(list: List<Element>, ormat: Pair<String, List<String>>): List<String> =
    list.map { e -> processElement(e, format) }

fun processElement(element: Element, ormat: Pair<String, List<String>>):
    String {
        val formatString = format.first
        val parameters = format.second.map { element.getChildText(it) }
        return String.format(formatString, *parameters.toArrayList().toArray())
    }