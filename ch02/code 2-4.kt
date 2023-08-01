// 2.2 클래스와 인터페이스

class Person constructor(name: String) {
    val name: String

    init {
        this.name = name
    }
}


public final class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}