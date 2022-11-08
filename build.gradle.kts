import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jacocoXml = "$buildDir/jacoco-xml/jacoco.xml"
val jvmArguments = listOf(
	"-XX:+UnlockExperimentalVMOptions",
	"-XX:ThreadStackSize=512k",
	"-Xms512m",
	"-Xmx1024m"
)

plugins {
	var kotlinVersion = "1.6.21"
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("jacoco")
	id("info.solidsoft.pitest") version "1.7.4"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion
	kotlin("plugin.allopen") version kotlinVersion
}

group = "com.ehei.tpms"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

jacoco {
	toolVersion = "0.8.8"
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
		xml.outputLocation.set(file(jacocoXml))
		html.outputLocation.set(layout.buildDirectory.dir("jacoco-html"))
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude("org.junit.vintage", "junit-vintage-engine")
	}
	testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.assertj:assertj-core:3.6.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	finalizedBy("jacocoTestReport")
	useJUnitPlatform()
}

configure<info.solidsoft.gradle.pitest.PitestPluginExtension> {
	junit5PluginVersion.set("1.0.0")
	useClasspathFile.set(true)
	pitestVersion.set("1.9.0")
}

tasks.pitest {
	targetClasses.set(setOf("com.ehei.tpms.*"))
	excludedClasses.set(
		setOf(
			"com.ehei.tpms.server.config.*",
			"com.ehei.tpms.server.model.*",
			"com.ehei.tpms.server.api.*",
		)
	)
	outputFormats.set(setOf("XML", "HTML"))
	mutators.set(setOf("STRONGER"))
	threads.set(Runtime.getRuntime().availableProcessors())
	avoidCallsTo.set(setOf("kotlin.jvm.internal", "org.slf4j"))
	timestampedReports.set(false)
	failWhenNoMutations.set(false)
	enableDefaultIncrementalAnalysis.set(true)
	jvmArgs = jvmArguments
	maxHeapSize = "1024m"
}
