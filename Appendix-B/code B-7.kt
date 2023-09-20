// B.5 속성 기반 테스트 작성하기

// 예제 01 테스트
import io.kotlintest.properties.forAll
import io.kotlintest.specs.StringSpec

class MyClassTest: StringSpec() {
    init {
        "test1" {
            forAll {
                // 속성을 여기서 체크한다.
            }
        }
    }
}


import io.kotlintest.properties.forAll
import io.kotlintest.specs.StringSpec

class MyClassTest: StringSpec() {
    init {
        "test1" {
            forAll(3,000) {
                // 속성을 여기서 체크한다.
            }
        }
    }
}


// 예제 01 테스트
class MyKotlinLibraryTest: StringSpec() {
    init {
        "isMaxMultiple" {
            forAll { multiple: Int, max:Int, value: Int ->
                isMaxMultiple(multiple)(max, value).let { result ->
                    result >= value
                        && result % multiple == 0 || result == max
                        && ((result % multiple == 0 && result >= value)
                            || result % multiple != 0)
                }
            }
        }
    }
}


class MyKotlinLabraryTest: StringSpec() {
    init {
        "isMaxMultiple" {
            forAll(Gen.int(), Gen.int(), Gen.int())
                { multiple: Int, max:Int, value: Int ->
                    isMaxMultiple(multiple)(max, value).let { result ->
                        result >= value
                            && result % multiple == 0 || result == max
                            && ((result % multiple == 0 && result >= value)
                                || result % multiple != 0)
                    }
                }
        }
    }
}


// Attempting to shring failed arg -2147483648
// Shrink #1: 0 fail
// Shrink result => 0

// ...

// java.lang.AssertionError: Property failed for
// Arg 0: 0 (shrunk from -2147483648)
// Arg 1: 0 (shrunk from -2147483648)
// Arg 2: 0 (shrunk from 2147483648)
// after 1 attempts
// Caused by:  expected: true but was: false
// Expected :true
// Actual   :false


// 예제 02 테스트
class MyKotlinLibraryTest: StringSpec() {
    init {
        "isMaxMultiple" {
            forAll(Gen.positiveIntegers(), Gen.int(), Gen.int())
                { multiple: Int, max:Int, value: Int ->
                    isMaxMultiple(multiple)(max, value).let { result ->
                        result >= value
                            && result % multiple == 0 || result == max
                            && ((result % multiple == 0 && result >= value)
                                || result % multiple != 0)
                    }
                }
        }
    }
}