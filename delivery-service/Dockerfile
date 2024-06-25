FROM  gradle:jdk21-alpine
WORKDIR /delivery-service
COPY . /delivery-service
RUN ./gradlew build
CMD ["java", "-jar", "build/libs/delivery-service-0.0.1-SNAPSHOT.jar"]