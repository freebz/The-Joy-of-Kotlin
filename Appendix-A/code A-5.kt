// A.2 자바 라이브러리 메서드와 코틀린 코드

// A.2.1 자바 원시 타입 사용하기

val a: java.lang.Long = java.lang.Long.valueOf(3L)

// Error:(...) Kotlin: Type mismatch: inferred type is kotlin.Long! but java.lang.Long was expected


val a: java.lang.Long = java.lang.Long(3)