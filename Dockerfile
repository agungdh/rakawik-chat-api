FROM eclipse-temurin:25-jdk-noble AS builder
WORKDIR /workspace
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM gcr.io/distroless/java25-debian13:nonroot
COPY --from=builder /workspace/build/libs/*.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
