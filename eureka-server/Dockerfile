FROM  gradle:jdk21-alpine
WORKDIR /eureka-server
COPY . /eureka-server
RUN ./gradlew build
CMD ["java", "-jar", "build/libs/eureka-server-0.0.1-SNAPSHOT.jar"]