# Primeiro para fazer o build
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Prepara o pom para fazer o build via maven
COPY pom.xml .
# Roda o maven com opcao de caching
RUN mvn dependency:go-offline -B
# Copia e faz o build da app Spring Boot de teste
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

#Vai rodar a app Spring Boot
FROM openjdk:17-alpine

WORKDIR /app
# Copia o arquivo do build para o container que vai rodar
COPY --from=build /app/target/*.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring

RUN chown -R spring:spring /app

#Expor a porta
EXPOSE 8080
#Comando inicial
CMD ["java", "-jar", "app.jar"]