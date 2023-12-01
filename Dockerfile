FROM openjdk:17
ADD target/crud-docker.jar crud-docker.jar
ENTRYPOINT ["java","-jar","/crud-docker.jar"]