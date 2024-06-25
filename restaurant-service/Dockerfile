FROM  gradle:jdk21-alpine
WORKDIR /restaurant-service
COPY . /restaurant-service
RUN ./gradlew build
CMD ["java", "-jar", "build/libs/restaurant-service-0.0.1-SNAPSHOT.jar"]