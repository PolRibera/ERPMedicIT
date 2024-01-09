FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ARG CONF_FILE
COPY ${JAR_FILE} /home/app.jar
COPY ${CONF_FILE} /home/application.yml
ENTRYPOINT ["java","-jar","/home/app.jar"]