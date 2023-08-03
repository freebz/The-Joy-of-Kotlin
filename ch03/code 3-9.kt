// 3.2.5 객체 표기법과 함수 표기법 비교

class Payment(val creditCard: CreditCard, val amount: Int) {
    fun combine(payment: Payment): Payment =
        if (creditCard == payment.creditCard)
            Payment(creditCard, amount + payment.amount)
        else
            throw IllegalStateException("Cards don't match.")

    companion object {
        fun groupByCard(payments: List<Payment>): List<Payment> =
            payments.groupBy { it.creditCard }
                    .values
                    .map { it.reduce(Payment::combine) }
    }
}


fun combine(payment1: Payment, payment2: Payment): Payment =
    if (payment1.creditCard == payment2.creditCard)
        Payment(payment1.creditCard, payment1.amount + payment2.amount)
    else
        throw IllegalStateException("Cards don't match.")


val newPayment = combine(this, otherPayment)


fun combine(payment: Payment): Payment =
    if (creditCard == payment.creditCard)
        Payment(creditCard, amount + payment1.amount)
    else
        throw IllegalStateException("Cards don't match.")


val newPayment = payment1.combine(payment2).combine(payment3)


import ...Payment.Companion.combine

val newPayment = combine(combine(payment1, payment2), payment3)