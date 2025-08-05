plugins {
	java
	id("org.springframework.boot") version "3.4.8"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.neptunesoftware"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.google.code.gson:gson:2.13.0")
	implementation("org.projectlombok:lombok:1.18.38")
	implementation("com.sun.xml.ws:jaxws-rt:4.0.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
