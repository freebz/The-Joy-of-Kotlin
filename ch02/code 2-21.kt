// 2.8.1 널이 될 수 있는 타입 다루기

val s: String? = someFunctionReturningAStringThatCanBeNull()
val l = s.length    // 오류


val s: String? = someFunctionReturningAStringThatCanBeNull()
val l = s?.length


val citi: City? = map[companyName]?.manager?.address?.city


City city = Optional.ofNullable(map.get(companyName))
                    .flatMap(Company::getManager)
                    .flatMap(Employee::getAddress)
                    .flatMap(Address::getCity)
                    .getOrElse(null);


val city: City? = map[company]!!.manager()!!.address!!.city