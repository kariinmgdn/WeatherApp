FROM eclipse-temurin:17.0.6_10-jdk-jammy
# Copy files for build
WORKDIR /app
COPY . /app
# Build app
RUN ./gradlew build
# Run app
CMD ["./gradlew", "bootRun"]
