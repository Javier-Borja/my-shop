FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/my-shop-0.0.1-SNAPSHOT.jar app.jar
EXPOSE ${PORT}
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]