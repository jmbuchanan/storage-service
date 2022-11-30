FROM openjdk:11-jre-slim-buster
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=local-k8s","-jar","/app.jar"]
