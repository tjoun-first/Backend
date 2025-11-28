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
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat-runtime")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //WebClient(gemini api 요청용)
    implementation("org.springframework:spring-webflux")

    //gson
    implementation("com.google.code.gson:gson:2.11.0")
	
	// --- JPA 추가 ---
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // --- DB 드라이버 추가  ---
    runtimeOnly("com.mysql:mysql-connector-j")
    
    //open api doc
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    
    //jsoup
    implementation("org.jsoup:jsoup:1.17.2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
