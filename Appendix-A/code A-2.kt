// A.1.3 프로젝트에 의존 관계 추가하기

val kotlintestVersion = "3.1.10"
val logbackVersion = "1.2.3"
val slf4jVersion = "1.7.25"

plugins {
    application
    kotlin("jvm") version "1.3.21" // 필요에 따라 버전을 변경한다.
}

application {
    mainClassName = "com.mydomain.mysimpleproject.MainKt"
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    testCompile("io.kotlintest:kotlintest-runner-junit5:$kotlintestVersion")
    testRuntime("org.slf4j:slf4j-nop:$slf4jVersion")
}