// A.4.5 코틀린 타입을 자바 타입으로 변환하기

// A.4.6 함수 타입

@JvmField
val isEven: (Int) -> Boolean = { it % 2 == 0 }


// java code
System.out.println(MyFileKt.isEven.invoke(2));

// kotlin.jvm.functions.Function1<Integer, Boolean> 타입


IntPredicate p = MyFileKt.isEven::invoke;