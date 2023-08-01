// 2.13 동등성과 동일성

int a = 2;
System.out.println(a == 2); // true
Integer b = Integer.valueOf(1);
System.out.println(b == Integer.valueOf(1)); // true
System.out.println(b == new Integer(1)); // false
System.out.println(b.equals(new Integer(1))); // true
Integer c = Integer.valueOf(512);
System.out.println(c == Integer.valueOf(512)); // false
System.out.println(c.equals(Integer.valueOf(512))); // true
String s = "Hello";
System.out.println(s == "Hello"); // true
String s2 = "Hello, World!".substring(0, 5);
System.out.println(s2 == "Hello"); // false
System.out.println(s2.equals("Hello")); // true