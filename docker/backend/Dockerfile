FROM openjdk:11.0.4-jre
RUN ./gradlew jar
COPY build/libs/*.jar /app.jar
EXPOSE 8080
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
