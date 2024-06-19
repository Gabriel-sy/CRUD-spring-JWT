#Pegando imagem do maven
FROM maven:3.8.5-openjdk-17 AS build

#Copiando pasta src para /app/src
COPY src /app/src
#Copiando pom.xml para /app
COPY pom.xml /app


WORKDIR /app
#Build do projeto
RUN mvn clean install

#Pegando imagem da jdk
FROM openjdk:17

COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]