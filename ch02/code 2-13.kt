// 2.2.9 유틸리티 클래스 인스턴스화 방지하기

package com.acme.util

fun create(xml: String): Person {
    ...
}


val person = com.acme.util.create(someXmlString)


import com.acme.util.*

val person = create(someXmlString)