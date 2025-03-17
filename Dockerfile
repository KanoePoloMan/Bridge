FROM openjdk:21-ea-28-jdk-slim-bookworm
ARG JAR_FILE=Bridge-1.0-SNAPSHOT.jar
# ARG DOCKER_COMPOSE_FILE=docker-compose.yml
COPY ${JAR_FILE} app.jar
# COPY ${DOCKER_COMPOSE_FILE} docker-compose.yml
# RUN apt-get update && apt-get install -y docker
# EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]