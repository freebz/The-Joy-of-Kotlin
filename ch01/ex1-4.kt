// 예제 1-4 여러 도넛을 한꺼번에 사기

// class Purchase(val donuts: List<Donut>, val payment: Payment)로
// Purchase 정의를 변경했음
fun buyDonut(quantity: Int = 1, creditCard: CreditCard): Purchase =
        Purchase(List(quantity) {
            Donut()
        }, Payment(creditCard, Donut.price * quantity))