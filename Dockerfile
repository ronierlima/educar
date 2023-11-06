
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/educar-api-0.0.1-SNAPSHOT.jar educar.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","educar.jar"]