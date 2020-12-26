# Getting Started - BKN Booking

### Reference Documentation
For further reference, please consider the following sections:

* [Docker Container's Architecture Diagram](https://github.com/fernandooliveira19/bookings-architecture-diagram) 

### Dependencies

Dependencies used in this project


* Actuator
* Eureka Client
* Postgres

### Docker's commands

pull postgres image

* $ docker pull postgres:12-alpine

run database image

* $ docker run -p 5434:5432 --name bkn-booking-pg12 --network bkn-net -e POSTGRES_PASSWORD=Famo2369 -e POSTGRES_DB=db_bkn_booking postgres:12-alpine


create network

* $ docker network create bkn-net

clean and package

* $ .\mvnw clean package -DskipTests

build docker image

* $ docker build -t bkn-booking:v1 .

run docker container

* $ docker run -P --network bkn-net bkn-booking:v1 
