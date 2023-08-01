// 2.16.2 공변성을 써야 하는 경우와 반공변성을 써야 하는 경우

interface Bag<T> {
    fun get(): T
}


open class MyClassParent

class MyClass: MyClassParent()

interface Bag<out T> {
    fun get(): T
}

class BagImpl : Bag<MyClass> {
    override fun get(): MyClass = MyClass()
}

val bag: Bag<MyClassParent> = BagImpl()


open class MyClassParent

class MyClass: MyClassParent()

interface Bag<in T> {
    fun use(t: T): Boolean
}

class BagImpl : Bag<MyClassParent> {
    override fun use(t: MyClassParent): Boolean = true
}

val bag: Bag<MyClass> = BagImpl()