// 10.9.3 연산자 오버로딩

val tree = Tree<Int>() + 5 + 2 + 8

// compile error
val tree: Tree<Int> = Tree() + 5 + 2 + 8