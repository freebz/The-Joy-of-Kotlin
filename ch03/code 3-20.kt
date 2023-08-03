// 3.3.7 클로저 구현하기

val taxRate = 0.09
fun addTax(price: Double) = price + price * taxRate


val taxRate = 0.09
fun addTax(taxRate: Double, price: Double) = price + price * taxRate

println(addTax(taxRate, 12.0))


val taxRate = 0.09

val addTax = { taxRate: Double, price: Double -> price + price * taxRate }

println(addTax(taxRate, 12.0))


val taxRate = 0.09

val addTax = { taxRate: Double ->
                { price: Double ->
                    price + price * taxRate
                }
            }

println(addTax(taxRate)(12.0))