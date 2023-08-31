// 6.2 코틀린이 널 참조를 처리하는 방식

while(enumeration.hasNext()) {
    result = process(result, enumeration.next())
}
use(result)


var result = process(enumeration.next())
while(enumeration.hasNext()) {
    result = process(result, enumeration.next())
}
use(result)


var result = null
while(enumeration.hasNext()) {
    result = process(result, enumeration.next())
}
use(result)