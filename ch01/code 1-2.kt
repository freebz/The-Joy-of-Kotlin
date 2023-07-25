// 1.2.2 안전성 원칙을 간단한 식에 적용하기

class Purchase(val donut: Donut, val payment: Payment)


fun buyDonut(creditCard: CreditCard): Purchase {
    val donut = Donut()
    val payment = Payment(creditCard, Donut.price)
    return Purchase(donut, payment)
}


{ index -> Donut() }


{ _ -> Donut() }


buyDonut(creditCard = cc)


public static Purchase buyDonuts(CreditCard creditCard) {
    return buyDonuts(1, creditCard);
}
public static Purchase buyDonuts(int quantity, CreditCard creditCard) {
    return new Purchase(Collections.nCopies(quantity, new Donut()),
                        new Payment(creditCard, Donut.price * quantity));
}


class DonutShopKtTest {
    @Test
    fun testBuyDonuts() {
        val creditCard = CreditCard()
        val purchase = buyDonuts(5, creditCard)
        assertEquals(Donut.price * 5, purchase.payment.amount)
        assertEquals(creditCard, purchase.payment.creditCard)
    }
}