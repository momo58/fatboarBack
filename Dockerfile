FROM maven:3-alpine

COPY pom.xml fatboar-back/

COPY src/ fatboar-back/src/

WORKDIR fatboar-back/

RUN  mvn -B -DskipTests clean package

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/fatboar-back/target/Fatboar-Back-0.0.1-SNAPSHOT.jar"]
