import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.31"
	kotlin("plugin.spring") version "1.4.31"
	kotlin("plugin.jpa") version "1.4.31"
	id("com.avast.gradle.docker-compose") version "0.10.9"
}

group = "com.creativedock"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

val r2dbcVersion = "0.8.6.RELEASE"
val r2dbcPoolVersion = "0.8.5.RELEASE"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("commons-codec:commons-codec:1.9")
	implementation("io.r2dbc:r2dbc-pool:$r2dbcPoolVersion")
	implementation("io.r2dbc:r2dbc-postgresql:$r2dbcVersion")
	runtimeOnly(group = "org.postgresql", name = "postgresql")
	implementation(group = "org.flywaydb", name = "flyway-core", version = "6.3.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

dockerCompose {
	useComposeFiles = listOf("docker-compose.yaml")
	isRequiredBy(project.tasks.test.get())
	stopContainers = true
}

