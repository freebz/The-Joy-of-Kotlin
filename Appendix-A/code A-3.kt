// A.1.4 멀티 모듈 프로젝트 만들기

// settings.gradle.kts
include ("server", "client", "common")


// build.gradle.kts
ext["kotlintestVersion"] = "3.1.10"
ext["logbackVersion"] = "1.2.3"
ext["slf4jVersion"] = "1.7.25"

plugins {
    base
    kotlin("jvm") version "1.3.21" // 필요에 따라 버전을 변경한다.
}

allprojects {
    group = "com.mydomain.mymultipleproject"
    version = "1.0-SNAPSHOT"
    repositories {
        jcenter()
        mavenCentral()
    }
}


plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "com.mydomain.mymultipleproject.server.main.Server"
}

dependencies {
    compile(kotlin("stdlib"))
    compile(project(":common"))
}