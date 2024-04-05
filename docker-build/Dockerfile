
FROM openjdk:17-slim

WORKDIR /BugTracker

COPY pom.xml ./
RUN mvn clean package

COPY target/*.jar ./

EXPOSE 8080

CMD ["java", "-jar", "bug-tracker.jar"]

# Add environment variables for database connection details
ENV DB_HOST=localhost
ENV DB_PORT=5432
ENV DB_NAME=bugTracker
ENV DB_USERNAME=sium
ENV DB_PASSWORD=sium123