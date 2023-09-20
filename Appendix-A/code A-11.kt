// A.2.7 자바에서 널 가능성 지정하기

@NotNull
public static List<@NotNull Integer> getIntegerList() {
    return null;
}

// java.lang.IllegalStateException: @NotNull method test/MyClass.getIntegerList must not return null


@NotNull
public static List<@NotNull Integer> getIntegerList() {
    return Arrays.asList(1, 2, 3, null);
}