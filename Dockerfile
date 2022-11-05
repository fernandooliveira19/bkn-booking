FROM openjdk:11
VOLUME /tmp
ADD ./target/bkn-booking-1.0.3-SNAPSHOT.jar bkn-booking.jar
ENTRYPOINT ["java","-jar","/bkn-booking.jar"]