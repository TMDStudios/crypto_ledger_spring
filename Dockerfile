FROM openjdk:8
COPY /target/cls-0.0.1-SNAPSHOT.war cls.war
ENTRYPOINT ["java","-jar","cls.war"]