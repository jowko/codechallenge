FROM maven:3.6.3-adoptopenjdk-11 as builder

WORKDIR application

COPY pom.xml pom.xml

RUN mvn -B dependency:go-offline

COPY src src

RUN mvn clean package

ARG JAR_FILE=target/*.jar
RUN mv ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR application
COPY --from=builder application/dependencies/ ./
RUN true
COPY --from=builder application/snapshot-dependencies/ ./
RUN true
COPY --from=builder application/application/ ./
RUN true
COPY --from=builder application/spring-boot-loader/ ./

ARG SERVER_PORT=8080

EXPOSE $SERVER_PORT

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
