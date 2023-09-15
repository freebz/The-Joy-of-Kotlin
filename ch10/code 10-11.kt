// 10.11 트리 매핑하기

// 연습문제 10-11

fun <B: Comparable<B>> map(f: (A) -> B): Tree<B> =
    foldInOrder(Empty) { t1: Tree<B> ->
        { i: A ->
            { t2: Tree<B> ->
                Tree(t1, f(i), t2)
            }
        }
    }