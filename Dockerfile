FROM openjdk:11
VOLUME /tmp
ADD ./target/bkn-booking-0.0.1-SNAPSHOT.jar bkn-booking.jar
ENTRYPOINT ["java","-jar","/bkn-booking.jar"]