FROM openjdk:11-jre
VOLUME /tmp
COPY ./ridetrack-core/target/ridetrack-core-1.0-SNAPSHOT.jar ridetrack.jar
ENTRYPOINT ["java", "-jar", "/ridetrack.jar"]
EXPOSE 8080
