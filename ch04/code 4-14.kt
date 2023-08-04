// 4.3.5 즉시 계산의 위험성

// range와 forEach를 사용하는 경우
range(1, n).forEach((i) -> {
    // 작업
})

for(var i=0; i < n; i++) {
    // 작업
}