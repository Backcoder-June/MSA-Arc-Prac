FROM eclipse-temurin:25-jdk-alpine AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN dos2unix ./gradlew
RUN ./gradlew bootJAR

FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
COPY --from=builder build/libs/*.jar /app/app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]