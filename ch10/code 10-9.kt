// 10.10.2 함수를 하나만 사용해 트리 접기

// 연습문제 10-9

// Tree
abstract fun <B> foldInOrder(identity: B, f: (B) -> (A) -> (B) -> B): B
abstract fun <B> foldPreOrder(identity: B, f: (A) -> (B) -> (B) -> B): B
abstract fun <B> foldPostOrder(identity: B, f: (B) -> (B) -> (A) -> B): B

// Empty
override fun <B> foldInOrder(identity: B, f: (B) ->(Nothing) -> (B) -> B): B = identity
override fun <B> foldPreOrder(identity: B, f: (Nothing) -> (B) -> (B) -> B): B = identity
override fun <B> foldPostOrder(identity: B, f: (B) -> (B) -> (Nothing) -> B): B = identity

// T
override fun <B> foldInOrder(identity: B, f: (B) ->(A) -> (B) -> B): B =
    f(left.foldInOrder(identity, f))(value)(right.foldInOrder(identity, f))
override fun <B> foldPreOrder(identity: B, f: (A) -> (B) -> (B) -> B): B =
    f(value)(left.foldPreOrder(identity, f))(right.foldPreOrder(identity, f))
override fun <B> foldPostOrder(identity: B, f: (B) -> (B) -> (A) -> B): B =
    f(left.foldPostOrder(identity, f))(right.foldPostOrder(identity, f))(value)