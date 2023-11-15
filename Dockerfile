FROM maven:3.9.5-eclipse-temurin-21-alpine

MAINTAINER Luis Brienze <lfbrienze@gmail.com>

WORKDIR /usr/src
COPY . .

ENV SERVER_PORT 8080

RUN apk update && apk add bash && apk add curl

RUN mvn clean install -B -DskipTests -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn

HEALTHCHECK --interval=5s --timeout=3s CMD curl --fail --silent localhost:$SERVER_PORT/actuator/health | grep UP || exit 1

ENTRYPOINT ["java", "-jar", "target/shopping-list-api-0.0.1-SNAPSHOT.jar"]