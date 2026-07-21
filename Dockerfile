FROM gcr.io/distroless/java25-debian13:nonroot

COPY build/libs/*.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
