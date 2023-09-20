// A.2.8 게터와 세터 호출하기

// java code
public class MyClass {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}


// kotlin code
val myClass = MyClass()
myClass.value = 1
println(myClass.value)

myClass.setValue(2)
println(myClass.getValue())


// java code
public class MyClass {
    public int getValue() {
        return 0;
    }
}


// java code
public class MyClass {
    boolean started = true;
    boolean working = false;

    public boolean isStarted() {
        return started;
    }

    public boolean getWorking() {
        return working;
    }
}


// kotlin code
val myClass = MyClass()

myClass.started = true
myClass.working = false

println(myClass.started)
println(myClass.isStarted)
println(myClass.working)


val result: Unit = myClass.setWorking(false)