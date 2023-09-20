// A.2.6 자바 varargs 사용하기

public static void show(String... strings) {
    for (String string : strings) {
        System.out.println(string);
    }
}


val stringArray = arrayOf("Mickey", "Donald", "Pluto")
MyClass.show(*stringArray)