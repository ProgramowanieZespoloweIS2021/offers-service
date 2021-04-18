FROM maven:3.8.1-openjdk-16 AS build
COPY src /offers/src
COPY pom.xml /offers/
RUN mvn -f /offers/pom.xml clean package -DskipTests

FROM openjdk:latest
COPY --from=build /offers/target/offers-service-0.0.1-SNAPSHOT.jar /offers/offers-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/offers/offers-service-0.0.1-SNAPSHOT.jar"]
