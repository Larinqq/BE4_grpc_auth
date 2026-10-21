FROM bellsoft/liberica-openjdk-alpine:21 AS builder
WORKDIR /project

COPY gradle/ gradle/
COPY gradlew build.gradle.kts settings.gradle.kts ./

RUN ./gradlew dependencies --no-daemon || true

COPY src/ src/

RUN ./gradlew clean bootJar -x test --no-daemon

FROM bellsoft/liberica-openjre-alpine:21
WORKDIR /app

COPY --from=builder /project/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
