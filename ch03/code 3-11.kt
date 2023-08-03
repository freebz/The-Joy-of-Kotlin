// 3.2.7 함수 참조 사용하기

fun double(n: Int): Int = n * 2

val multiplyBy2: (Int) -> Int = { n -> double(n) }


val multiplyBy2: (Int) -> Int = { double(it) }


val multiplyBy2: (Int) -> Int = ::double


class MyClass {
    fun double(n: Int): Int = n * 2
}

val foo = MyClass()
val multiplyBy2: (Int) -> Int = foo::double


import other.package.double

val multiplyBy2: (Int) -> Int = ::double


val multiplyBy2: (MyClass, Int) -> Int = MyClass::double // { obj: MyClass, n: Int -> (obj::double)(n) }과 같다.


class MyClass {
    companion object {
        fun double(n: Int): Int = n * 2
    }
}

val multiplyBy2: (Int) -> Int = (MyClass)::double


val multiplyBy2: (Int) -> Int = MyClass.Companion::double


val multiplyBy2: (Int) -> Int = MyClass::double // 컴파일러가 인스턴스 메서드 double을 찾을 수 없다!