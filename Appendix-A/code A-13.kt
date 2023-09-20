// A.2.9 예약어로 된 자바 프로퍼티에 접근하기

// java code
public class MyClass {
    private InputStream in;

    public void setIn(InputStream in) {
        this.in = in;
    }

    public InputStream getIn() {
        return in;
    }
}


// kotlin code
val input = myClass.`in`