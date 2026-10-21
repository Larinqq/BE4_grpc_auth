import com.google.protobuf.gradle.id

plugins {
    java
    id("org.springframework.boot") version "4.0.8"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.protobuf") version "0.9.5"
}

group = "ahaidewi"
version = "0.0.1-SNAPSHOT"
description = "AuthServer"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

val jwtLibVersion = "0.13.0"
val uuid7GenVersion = "6.1.1"
extra["springGrpcVersion"] = "1.0.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.grpc:spring-grpc-server-spring-boot-starter")
    implementation("io.jsonwebtoken:jjwt:${jwtLibVersion}")
    implementation("io.grpc:grpc-services")
    implementation("com.github.f4b6a3:uuid-creator:${uuid7GenVersion}")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

//    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
//    testImplementation("org.springframework.boot:spring-boot-starter-security-test")
//    testImplementation("org.springframework.grpc:spring-grpc-test")
//    testCompileOnly("org.projectlombok:lombok")
//    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//    testAnnotationProcessor("org.projectlombok:lombok")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.grpc:spring-grpc-dependencies:${property("springGrpcVersion")}")
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc") {
                    option("@generated=omit")
                }
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs(
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/grpc"
            )
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
