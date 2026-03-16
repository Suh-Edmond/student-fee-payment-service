
# Student One-Time Institutional Fee Payment
Simple Fee payment service for students develop with Spring Boot using Monolithic Design Pattern. This Project is aimed at processing student's one-time fee payment
in an institution

## Tools and Technologies
* SpringBoot 3.4.3
* Java 17
* Hibernate Validator
* Spring DataJpa
* MySQL Server v5 and MySQL Connector Java
* Swagger UI
* Maven 4.0
* Tomcat Server
* Spring Devtools
* Flyway
* Junit5

## Features
To achieve this fee payment, the service provides a list of endpoints, namely:
* Institution Fee Category: This endpoint returns all available institution fee categories from which a student can pick
* Create Student Account: Create a basic account for a student who wants to make fee payment
* One-Time Fee Payment: This endpoint makes a one-time fee payment for a student
* Get Student Fee Payments: This endpoint returns all payments made by a student.
* Get Student Institution Fee

## Assumptions:
* No Security implemented: Since it's just a basic application, my design did not consider security, 
hence students don't have to be authenticated to perform this fee payment. But they must create an account

## How to run Service
* Setup dev environment ensuring Java JDK 17 is installed
* Setup a MySQL database
* create the database mentioned in the properties file `application.yaml`
* Run the application
* API Documentation: http://localhost:8000/swagger-ui/#/

## Run test
* ./mvnw clean test

## Build application
* ./mvnw clean build
