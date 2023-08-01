// 예제 2-2 인텔리J IDEA가 생성한 자바 데이터 객체

public final class Person {
    private final String name;
    private final Instant registered;

    public Person(String name, Instant registered) {
        this.name = name;
        this.registered = registered;
    }

    public Person(String name) {
        this(name, Instant.now());
    }

    public String getName() {
        return name;
    }

    public Instant getRegistered() {
        return registered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
               Objects.equals(registered, person.registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, registered);
    }
}