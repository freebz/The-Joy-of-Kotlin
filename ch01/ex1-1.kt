// 예제 1-1 부소 효과가 있는 코틀린 프로그램

fun buyDonut(creditCard: CreditCart): Donut {
  val donut = Donut()
  creditCard.charge(Donut.price)
  return donut
}