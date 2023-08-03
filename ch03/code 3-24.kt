// 3.3.11 올바른 타입 사용하기

// 표준 타입을 사용할 때 생기는 문제 피하기

data class Product(val name: String, val price: Double, val weight: Double)


data class OrderLine(val product: Product, val count: Int) {
    fun weight() = product.weight * count
    fun amount() = product.price * count
}


// Total price: 1.9
// Total weight: 13.5


val weight = orderLines.sumByDouble { it.amount() }
val price = orderLines.sumByDouble { it.weight() }



// 값 타입 정의하기

data class Price(val value: Double)

data class Weight(val value: Double)


val total = price.value + weight.value


data class Price(val value: Double) {
    operator fun plus(price: Price) = Price(this.value + price.value)
}

data class Weight(val value: Double) {
    operator fun plus(weight: Weight) = Weight(this.value + weight.value)
}


val totalPrice = Price(1.0) + Price(2.0)


data class Price(val value: Double) {
    operator fun plus(price: Price) = Price(this.value + price.value)

    operator fun times(num: Int) = Price(this.value * num)
}


data class Weight(val value: Double) {
    operator fun plus(weight: Weight) = Weight(this.value + weight.value)

    operator fun times(num: Int) = Weight(this.value * num)
}


val zeroPrice = Price(0.0)
val zeroWeight = Weight(0.0)
val priceAddition = { x, y -> x + y }


data class Product(val name: String, val price: Price, val weight: Weight)

data class OrderLine(val product: Product, val count: Int) {
    fun weight() = product.weight * count
    fun amount() = product.price * count
}


object Store {
    @JvmStatic
    fun main(args: Array<String>) {
        val toothPaste = Product("Tooth paste", Price(1.5), Weight(0.5))
        val toothBrush = Product("Tooth brush", Price(3.5), Weight(0.3))
        val orderLines = listOf(
            OrderLine(toothPaste, 2),
            OrderLine(toothBrush, 3))
        val weight: Weight =
            orderLines.fold(Weight(0.0)) { a, b -> a + b.weight() }
        val price: Price =
            orderLines.fold(Price(0.0)) { a, b -> a + b.amount() }
        println("Total price: $price")
        println("Total weight: $weight")
    }
}


data class Price private constructor (private val value: Double) {
    override fun toString() = value.toString()
    operator fun plus(price: Price) = Price(this.value + price.value)
    operator fun times(num: Int) = Price(this.value * num)

    companion object {
        val identity = Price(0.0)
        operator fun invoke(value: Double) =
            if (value > 0)
                Price(value)
            else
                throw IllegalArgumentException("Price must be positive or null")
    }
}


data class Weight private constructor (private val value: Double) {
    override fun toString() = value.toString()
    operator fun plus(weight: Weight) = Weight(this.value + weight.value)
    operator fun times(num: Int) = Weight(this.value * num)

    companion object {
        val identity = Weight(0.0)
        operator fun invoke(value: Double) =
            if (value > 0)
                Weight(value)
            else
                throw IllegalArgumentException("Weight must be positive or null")
    }
}


object Store {

    @JvmStatic
    fun main(args: Array<String>) {
        val toothPaste = Product("Tooth paste", Price(1.5), Weight(0.5))
        val toothBrush = Product("Tooth brush", Price(3.5), Weight(0.3))
        val orderLines = listOf(
            OrderLine(toothPaste, 2),
            OrderLine(toothBrush, 3))
        val weight: Weight =
            orderLines.fold(Weight.identity) { a, b ->
                a + b.weight()
            }
        val price: Price =
            orderLines.fold(Price.identity) { a, b ->
                a + b.amount()
            }
        println("Total price: $price")
        println("Total weight: $weight")
    }
}