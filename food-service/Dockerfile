FROM eclipse-temurin:17

WORKDIR /app

COPY target/food-service-0.0.1-SNAPSHOT.jar app.jar

COPY opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

EXPOSE 8080

CMD ["java", "-javaagent:/otel/opentelemetry-javaagent.jar", "-Dotel.service.name=food-service", "-Dotel.exporter.otlp.endpoint=http://otel-collector:4318", "-Dotel.exporter.otlp.protocol=http/protobuf", "-jar", "app.jar"]