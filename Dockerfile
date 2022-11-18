FROM openjdk:11.0.4-jre as build
COPY build/libs/server-0.0.1-SNAPSHOT.jar.jar /app.jar
EXPOSE 8080
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
