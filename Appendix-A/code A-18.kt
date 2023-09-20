// A.4.4 코틀린 함수를 자바 메서드인 것처럼 호출하기

// MyFile.kt
fun method1() = "method 1"

class MyClass {
    companion object {
        fun method2() = "method 2"
        @JvmStatic
        fun method3() = "method 3"
    }
}

object MyObject {
    fun method4() = "method 4"
    @JvmStatic
    fun method5() = "method 5"
}


// java code
String s1 = MyFileKt.method1();
String s2 = MyClass.Companion.method2();
String s3a = MyClass.method3();
String s3b = MyClass.Companion.method3();
String s4 = MyObject.INSTANCE.method4();
String s5a = MyObject.method5();
String s5b = MyObject.INSTANCE.method5();



// 자바에서 확장 함수 호출하기

// MyFile.kt
fun List<String>.concat(): String = this.fold("") { acc, s -> "$acc$s" }


// java code
String s = MyFileKt.concat(Arrays.asList("a", "b", "c"));



// 다른 이름으로 함수 호출하기

fun List<String>.concat(): String = this.fold("") { acc, s -> "$acc$s" }
fun List<Int>.concat(): String = this.fold("") { acc, i -> "$acc$i" }


MyFileKt.concat(List<String> list)
MyFileKt.concat(List<Integer> list)


MyFileKt.concat(List list)
MyFileKt.concat(List list)


fun List<String>.concat(): String = this.fold("") { acc, s -> "$acc$s" }

@JvmName("concatIntegers")
fun List<Int>.concat(): String = this.fold("") { acc, i -> "$acc$i" }


@get:JvmName("retrieveName")
@get:JvmName("storeName")
var name: String?



// 기본 값과 파라미터 다루기

@JvmOverloads
fun computePrice(price: Double, tax: Double = 0.20) = price * (1.0 + tax)


Double computePrice(Double price)
Double computePrice(Double price, Double tax)


@JvmOverloads
fun computePrice(price: Double,
                 tax: Double = 0.20,
                 shipping: Double = 8.75) = price * (1.0 + tax) + shipping


Double computePrice(Double price)
Double computePrice(Double price, Double tax)
Double computePrice(Double price, Double tax, Double shipping)


class MyClass @JvmOverloads constructor(name: String, age: Int = 18)



// 예외를 던지는 함수 다루기

// kotlin code
fun readFile(filename: String): String = File(filename).readText()


// java code
try {
    System.out.println(MyFileKt.readFile("myFile"));
} catch (IOException e) {
    e.printStackTrace();
}

// Error: exception java.io.IOException is never thrown in body of corresponding try statement


@Throws(IOException::class)
fun readFile(filename: String): String = File(filename).readText()


@Throws(IOException::class, IndexOutOfBoundsException::class)
fun readFile(filename: String): String = File(filename).readText()