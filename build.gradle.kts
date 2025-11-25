plugins {
	java
	war
	id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.newsmoa"
version = "0.0.1-SNAPSHOT"
description = "news moas web app backend"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat-runtime")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //WebClient(gemini api 요청용)
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    //gson
    implementation("com.google.code.gson:gson:2.11.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
