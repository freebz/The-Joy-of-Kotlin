// 3.3.10 항등 함수 정의하기

val identity = { it } // <-- 구체적 타입을 지정해야 컴파일 오류가 나지 않음
                      // 예: val identity: (Int) -> Int = { it }