// A.2.5 다른 타입 변환 구현하기

val x = java.util.LinkedList(listOf(1,2,3,4,5)) // x는 java.util.LinkedList<kotlin.Int!> 타입


val intSingleton = java.util.Collections.singletonList(1) // kotlin.collections.(Mutable)List<kotlin.Int!>! 타입


// java code
package test;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static List<Integer> getIntegerList() {
        return Arrays.asList(1, 2, 3, null);
    }
}


val list: MutableList<Int> = test.Test.getIntegerList()

println(list)

// [1, 2, 3, null]


val list: MutableList<Int> = test.Test.getIntegerList()

list.forEach { println(it + 1) }