#FROM eclipse-temurin:21-jdk-alpine
#WORKDIR /app
#COPY target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"] 

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY . .

# 🔥 ADD THIS LINE (important fix)
RUN chmod +x mvnw

# Build
RUN ./mvnw clean package -DskipTests

# Run app
CMD ["java", "-jar", "target/royyaladiary-0.0.1-SNAPSHOT.jar"]

