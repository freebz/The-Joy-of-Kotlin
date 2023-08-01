// 2.16.3 사용 지점 변성과 선언 지점 변성

interface Bag<T> {
    fun get(): T
    fun use(t: T): Boolean
}


open class MyClassParent

class MyClass: MyClassParent()

interface Bag<T> {
    fun get(): T
    fun use(t: T): Boolean
}

class BagImpl : Bag<MyClassParent> {
    override fun get(): MyClassParent = MyClassParent()
    override fun use(t: MyClassParent): Boolean = true
}

fun useBag(bag: Bag<MyClass>): Boolean {
    // bag으로 작업 수령
    return true
}

val bag3 = useBag(BagImpl()) // <-- 컴파일러 오류


fun useBag(bag: Bag<in MyClass>): Boolean {
    // bag으로 작업 수행
    return true
}


fun createBag(): Bag<out MyClassParent> = BagImpl2()

class BagImpl2 : Bag<MyClass> {
    override fun use(t: MyClass): Boolean = true
    override fun get(): MyClass = MyClass()
}