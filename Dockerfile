FROM maven:3.5.4-jdk-8-alpine as builder

RUN mkdir -p /build
WORKDIR /build
COPY ./pom.xml /build
COPY ./src /build

RUN mvn dependency:resolve && mvn compile
