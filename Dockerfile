# https://spring.io/guides/topicals/spring-boot-docker


FROM eclipse-temurin:21 as build
WORKDIR app

# get dependencies first to cache them
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline

# rest of the source code
COPY src src

# build the .jar
RUN ./mvnw clean package -Dmaven.test.skip=true


FROM eclipse-temurin:21 as deployment
WORKDIR app
COPY --from=build /app/target/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]
