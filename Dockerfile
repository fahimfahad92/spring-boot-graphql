FROM eclipse-temurin:21-jre-alpine-3.21
EXPOSE 8080
ARG JAR_FILE=target/spring-boot-graphql-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#FROM eclipse-temurin:21-jdk as builder
#
##RUN addgroup demogroup; adduser  --ingroup demogroup --disabled-password demo
##USER demo
#
#WORKDIR /opt/app
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#COPY ./src src/
#
#################################################################################
#
#FROM builder as package
#
#WORKDIR /opt/app
#
#RUN --mount=type=bind,source=pom.xml,target=pom.xml \
#    ./mvnw clean install -DskipTests
#
#################################################################################
#
#FROM eclipse-temurin:21-jre AS final
#WORKDIR /opt/app
#EXPOSE 8080
#COPY --from=package /opt/app/target/*.jar /opt/app/app.jar
#ENTRYPOINT ["java", "-jar", "/opt/app/app.jar" ]

