FROM maven:3.8.6-openjdk-11 as build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:11
WORKDIR /app
COPY --from=build ./app/target/*.jar ./bkn-booking.jar
ARG SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/bkn-booking
ARG SPRING_DATASOURCE_USERNAME=postgres
ARG SPRING_DATASOURCE_PASSWORD=PGFamo2369
ARG SPRING_JPA_HIBERNATE_DDL_AUTO=none
ENTRYPOINT ["java","-jar","bkn-booking.jar"]