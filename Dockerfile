FROM adoptopenjdk/openjdk8
WORKDIR /
ARG ProductRequestedServiceDao-0.0.1-SNAPSHOT.jar
ADD ProductRequestedServiceDao-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8005
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]