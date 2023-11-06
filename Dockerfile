
FROM maven:3-amazoncorretto-17 AS build

COPY . .

RUN mvn clean package

FROM openjdk:17-jdk-slim
COPY --from=build /target/educar-api-0.0.1-SNAPSHOT.jar educar.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","educar.jar"]