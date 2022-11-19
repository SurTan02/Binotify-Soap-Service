FROM maven:3-openjdk-11
WORKDIR /usr/src/app
COPY /services/ ./
RUN mvn clean compile assembly:single
CMD ["java", "-jar" ,"target/services-1.0-SNAPSHOT-jar-with-dependencies.jar"]