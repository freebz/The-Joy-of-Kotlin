// B.4 속성 기반 단위 테스트의 의존 관계

// build.gradle.kts

dependencies {
    ...

    testCompile("io.kotlintest:kotlintest-runner-junit5:${project
        .rootProject.ext["kotlintestVersion"]}")

    testRuntime("org.slf4j:slf4j-nop:${project
        .rootProject.ext["slf4jVersion"]}")
}


ext["kotlintestVersion"] = "3.1.10"
ext["slf4jVersion"] = "1.7.25"