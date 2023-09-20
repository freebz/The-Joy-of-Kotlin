// A.1 혼합 프로젝트를 만들고 관리하기

// A.1.1 그레이들로 간단한 프로젝트 만들기

plugins {
    application
    kotlin("jvm") version "1.3.21"
}
application {
    mainClassName = "com.mydomain.mysimpleproject.MainKt"
}
repositories {
    jcenter()
}
dependencies {
    compile(kotlin("stdlib"))
}



// MyClass.java
package com.mydomain.mysimpleproject;

public class MyClass {
    public static String getMessage(Lang language) {
        switch (language) {
            case ENGLISH:
                return "Hello";
            case FRENCH:
                return "Bonjour";
            case GERMAN:
                return "Hallo";
            case SPANISH:
                return "Hola";
            defualt:
                return "Saluton";
        }
    }
}



// Main.kt
package com.mydomain.mysimpleproject

fun main(args: Array<String>) {
    println(MyClass.getMessage(Lang.GERMAN))
}

enum class Lang { GERMAN, FRENCH, ENGLISH, SPANISH }