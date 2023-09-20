// A.2.3 널 값 빠르게 실패시키기

val a: BigInteger? = BigInteger.valueOf(3)


val a: BigInteger? = BigInteger.valueOf(3)

val b: BigInteger? = BigInteger.valueOf(5)

println(a?.add(b)) == BigInteger.valueOf(8)


val a: BigInteger! = BigInteger.valueOf(3)


val a: BigInteger = BigInteger.valueOf(3)