// 예제 2-1 선택적인 프로퍼티가있는 전형적인 자바 객체

public final class Person {
    private final String name;
    private final Instant registered;

    public Person(String name, Instant registered) [
        this.name = name;
        this.registered = registered;
    ]

    public Person(String name) {
        this(name, Instant.now());
    }

    public String getName() {
        return name;
    }

    public Instant getRegistered() {
        return registered;
    }
}