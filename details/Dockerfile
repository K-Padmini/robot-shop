#
# Build
#
FROM debian:10 AS build

RUN apt-get update && apt-get -y install maven

WORKDIR /opt/details

COPY pom.xml /opt/details/
RUN mvn dependency:resolve
COPY src /opt/details/src/
RUN mvn package

#
# Run
#
FROM openjdk:8-jdk

EXPOSE 8080

WORKDIR /opt/details

COPY --from=build /opt/details/target/details-1.0.jar details.jar

CMD ["java", "-Xmn256m", "-Xmx768m", "-jar", "details.jar"]












