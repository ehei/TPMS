FROM openjdk:11.0.4-jre as build
RUN ./gradlew jar
COPY build/libs/server-0.0.1-SNAPSHOT.jar.jar /app.jar
EXPOSE 8080
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]

WORKDIR ./tpm2
FROM nginx:1.16.0-alpine
RUN npm install
RUN npm run build
COPY --from=build /app/build /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx/nginx.conf /etc/nginx/conf.d
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]


