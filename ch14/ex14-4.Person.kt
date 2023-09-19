// 예제 14-4 프로퍼티를 객체로 읽는 메서드와 프로퍼티를 객체 리스트로 읽는 메서드

data class Person(val id: Int,
                  val firstName: String,
                  val lastName: String) {
    companion object {
        fun readAsPerson(propertyName: String,
                         propertyReader: PropertyReader): Result<Person> {
            val rString = propertyReader.readAsPropertyString(propertyName)
            val rPropReader = rString.map { stringPropertyReader(it) }
            return rPropReader.flatMap { readPerson(it) }
        }
        fun readAsPersonList(propertyName: String,
                             propertyReader: PropertyReader): Result<List<Person>> =
            propertyReader.readAsList(propertyName, { it }).flatMap { list ->
                sequence(list.map { s ->
                    readPerson(PropertyReader
                        .stringPropertyReader(PropertyReader.toPropertyString(s)))
                })
        }
        private fun readPerson(propReader: PropertyReader): Result<Person> =
            propReader.readAsInt("id")
                .flatMap { id ->
                    propReader.readAsString("firstName")
                        .flatMap { firstName ->
                            propReader.readAsString("lastName")
                                .map { lastname -> Person(id, firstName, lastName) }
                        }
        }
    }
}