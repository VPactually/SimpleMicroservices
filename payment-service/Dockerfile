FROM  gradle:jdk21-alpine
WORKDIR /payment-service
COPY . /payment-service
RUN ./gradlew build
CMD ["java", "-jar", "build/libs/payment-service-0.0.1-SNAPSHOT.jar"]