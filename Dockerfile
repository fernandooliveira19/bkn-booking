FROM maven:3.8.6-openjdk-11 as build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:11
WORKDIR /app
COPY --from=build ./app/target/*.jar ./bkn-booking.jar
ENTRYPOINT ["java","-jar","bkn-booking.jar"]