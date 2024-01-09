FROM openjdk:17
ADD target/ProjecteHospitalPRA.jar ProjecteHospitalPRA.jar
ENTRYPOINT ["java","-jar","/ProjecteHospitalPRA.jar"]