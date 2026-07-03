FROM openjdk:21-ea-1-jdk-slim
VOLUME /tmp
ADD ./target/bkn-booking-1.0.5-SNAPSHOT.jar bkn-booking.jar
ENTRYPOINT ["java","-jar","/bkn-booking.jar"]