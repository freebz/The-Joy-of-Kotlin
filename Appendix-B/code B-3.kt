// B.1.2 테스트 작성하기

// 예제 00 테스트

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

internal class MyKotlinLibraryKtTest: StringSpec() {
    init {
        "maxMultiple" {
            val multiple = 2
            val list = listOf(4, 11, 8, 2, 3, 1, 14, 9, 5, 17, 6, 7)
            maxMultiple(multiple, list).shouldBe(14)
        }
    }
}