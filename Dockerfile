FROM maven:3-alpine

COPY pom.xml pipeline/

COPY src/ pipeline/src/

WORKDIR pipeline/

RUN  mvn -B -DskipTests clean package

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/pipeline/target/Fatboar-Back-0.0.1-SNAPSHOT.jar"]
