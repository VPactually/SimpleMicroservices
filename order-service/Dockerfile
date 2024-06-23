FROM  gradle:jdk21-alpine
WORKDIR /order-service
COPY . /order-service
RUN ./gradlew build
CMD ["java", "-jar", "build/libs/order-service-0.0.1-SNAPSHOT.jar"]