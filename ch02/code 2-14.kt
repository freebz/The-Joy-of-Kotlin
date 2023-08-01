// 2.4 컬렉션의 두 유형

var list = listOf(1, 2, 3)


import kotlin.collections.listOf


import kotlin.collections.*


val list1 = listOf(1, 2, 3)
val list2 = list1 + 4
val list3 = list1 + list2
println(list1)
println(list2)
println(list3)

// [1, 2, 3]
// [1, 2, 3, 4]
// [1, 2, 3, 1, 2, 3, 4]


val list1 = mutableListOf(1, 2, 3)
val list2 = list1.add(4)
val list3 = list1.addAll(list1)
println(list1)
println(list2)
println(list3)

// 1, 2, 3, 4, 1, 2, 3, 4]
// true
// true


val list1: List<Int> = mutableListOf(1, 2, 3)
val list2: List<Int> = list1.add(1) // <-- 컴파일 오류
val list3: List<Int> = list1.addAll(list2) // <-- 컴파일 오류
println(list1)
println(list2)
println(list3)