// 7.4 Result 패턴

"Mickey"
"Minnie"
"Goofy"
비어 있는 값(그냥 엔터키만 누름)


Success(mickey@disney.com)
Failure(Minie Mouse has no mail)
Failure(Key Goofy not found in map)
Failure(Invalid name)


toon = getName()
    .flatMap(toons::getResult)
    .flatMap(Toon::email)


// Result
abstract fun toOption(): Option<A>

// Success
override fun toOption(): Option<A> = Option(value)

// Failure
override fun toOption(): Option<A> = Option()


Option<String> result =
    getName().toOption().flatMap(toons::getResult).flatMap(Toon::email)


Success(mickey@disney.com)
Empty
Empty
Failure(java.io.IOException)