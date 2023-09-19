// 14.4.4 4단계: 인자 타입 문제 해결하기

data class FilePath private constructor(val value: Result<String>) {
    companion object {
        operator fun invoke(value: String): FilePath =
            FilePath(Result.of({ isValidPath(it) }, value,
                                "Invalid file path: $value"))
        // 검증 코드로 대체해야 한다.
        private fun isValidPath(path: String): Boolean = true
    }
}


fun readXmlFile(sPath: () -> FilePath,
                sRootName: () -> ElementName,
                format: Pair<String, List<String>>,
                effect: (List<String>) -> Unit): () -> Unit {
    val path = sPath()
    val rDoc = path.flatmap(::readFile2String)
    val rRoot = sRootName().value
}


fun getRootElementName(): ElementName =
    ElementName("staff") // 실패할 수 있는 계산을 시뮬레이션한다.
fun getXmlFilePath(): FilePath =
    FilePath("/path/to/file.xml") // <-- 경로를 조정해야 한다.