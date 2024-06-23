FROM  gradle:jdk21-alpine
WORKDIR /api-gateway
COPY . /api-gateway
RUN ./gradlew build
CMD ["java", "-jar", "build/libs/api-gateway-0.0.1-SNAPSHOT.jar"]