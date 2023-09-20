// A.1.5 멀티 모듈 프로젝트에 의존 관계 추가하기

ext["slf4jVersion"] = "1.7.25"


dependencies {
    compile(kotlin("stdlib"))
    testRuntime("org.slf4j:slf4j-nop:${project.rootProject.ext["slf4jVersion"]}")
}