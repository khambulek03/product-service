FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app
RUN addgroup -S productsgroup && adduser -S -G productsgroup productsuser
COPY --from=build --chown=productsuser:productsgroup /app/target/*.jar app.jar
USER productsuser:productsgroup
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]