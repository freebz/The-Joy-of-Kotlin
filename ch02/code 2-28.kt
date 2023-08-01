// 2.12 스마트 캐스트

Object payload = message.getPayload();
int length = -1;

if (payload instanceOf String) {
    String stringPayload = (String) payload;
    length = stringPayload.length();
}


val payload: Any = message.payload

val length: Int = if (payload is String)
    payload.length
else
    -1


val result: Int = when (payload) {
    is String -> payload.length
    is Int    -> payload
    else      -> -1
}


val result: String = payload as String


val result: String? = payload as? String