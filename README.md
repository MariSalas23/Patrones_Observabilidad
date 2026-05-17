**Nombres:** Katherin Juliana Moreno Carvajal, Mariana Salas Gutiérrez

# 1. Descripción

El presente proyecto implementa una arquitectura basada en microservicios instrumentados con OpenTelemetry, cuyo objetivo principal es garantizar observabilidad completa de extremo a extremo sobre las solicitudes HTTP realizadas entre servicios distribuidos.

El sistema está compuesto por dos microservicios desarrollados con Spring Boot:

* **food-service:** recibe las peticiones HTTP del cliente, realiza validaciones y consume otro microservicio.
* **db-service:** consulta e inserta información en PostgreSQL.

El flujo principal implementado es el siguiente:

* El usuario realiza una petición HTTP al food-service.
* El food-service procesa y valida la solicitud.
* El food-service llama al db-service.
* El db-service consulta PostgreSQL.
* Toda la comunicación queda registrada con el mismo TraceId.
* Los spans y métricas son exportados mediante OpenTelemetry Collector.

# 2. Tecnologías utilizadas

## 2.1. Backend

  * Java 17
  * Spring Boot
  * Maven
  * Spring Web
  * Spring JDBC
  * Base de datos
  * PostgreSQL

## 2.2. Observabilidad

  * OpenTelemetry Java Agent
  * OpenTelemetry SDK
  * OpenTelemetry Collector
  * Jaeger
  * New Relic

## 2.3. Infraestructura

* Docker
* Docker Compose

# 3. Flujo distribuido de una petición

Usuario -> food-service -> db-service -> PostgreSQL

 # 4. Estructura del proyecto

```
Observabilidad/
│
├── food-service/
│   ├── application/
│   ├── domain/
│   ├── infrastructure/
│   │   ├── client/
│   │   ├── controller/
│   │   └── telemetry/
│   └── Dockerfile
│
├── db-service/
│   ├── application/
│   ├── domain/
│   ├── infrastructure/
│   │   ├── controller/
│   │   ├── repository/
│   │   └── telemetry/
│   └── Dockerfile
│
├── postgres/
│   └── init.sql
│
├── otel-collector-config.yaml
│
├── docker-compose.yml
│
└── README.md
```
# 5. Preguntas

## 5.1. ¿Dónde inició el request?

El request inició en el microservicio `food-service`, específicamente en el endpoint:

```http
GET /comidas
```

Esto se evidencia tanto en los logs como en Jaeger, donde el primer span de la traza corresponde al `food-service`. Además, en los logs se observa:

```plaintext
REQUEST ENTRÓ A FOOD-SERVICE
```

Posteriormente, el `food-service` realiza una llamada HTTP al `db-service`, manteniendo el mismo `TraceId` durante toda la petición distribuida.

## 5.2. ¿Cuál servicio tardó más?

El servicio que presentó mayor tiempo de respuesta fue `food-service`.

En los logs correlacionados se registró:

```plaintext
TIEMPO REQUEST FOOD-SERVICE: 700 ms
```

Mientras que el `db-service` presentó:

```plaintext
TIEMPO REQUEST DB-SERVICE: 69 ms
```

Esto demuestra que la mayor parte de la latencia se generó en el procesamiento total del `food-service` y en la comunicación HTTP entre servicios.

## 5.3. ¿Cuál span falló?

Los spans fallidos son identificados mediante manejo de excepciones instrumentado manualmente con OpenTelemetry utilizando:

```java
span.recordException(e);
span.setStatus(StatusCode.ERROR);
```

Cuando ocurre un error, Jaeger y New Relic muestran automáticamente el span con estado `ERROR`, permitiendo identificar exactamente en qué servicio y operación ocurrió la falla.

## 5.4. ¿Qué operación generó latencia?

La operación que generó mayor latencia fue la llamada HTTP entre `food-service` y `db-service`.

En Jaeger se observa el span correspondiente a:

```plaintext
food-service-call-db-service
```

También se identificó tiempo asociado a la consulta SQL:

```sql
SELECT * FROM comidas
```

Sin embargo, la consulta a base de datos presentó tiempos mucho menores comparados con el tiempo total de la petición.

## 5.5. ¿Cuántos requests por segundo existen?

En New Relic se visualizaron métricas de throughput mediante `Requests per minute (RPM)`.

Durante las pruebas realizadas se observó aproximadamente:

```plaintext
0.07 rpm
```

lo cual corresponde a un número bajo de requests por segundo debido a que las pruebas fueron manuales y no de carga masiva.

## 5.6. ¿Cuál es el percentil p95?

El percentil p95 obtenido en New Relic fue:

```plaintext
50.3 ms
```

Esto significa que el 95% de las solicitudes tuvieron un tiempo de respuesta menor o igual a 50.3 ms.

## 5.7. ¿Cuál servicio consume más memoria?

El servicio con mayor carga y utilización observada fue `food-service`, debido a que:

* Recibe las solicitudes HTTP externas.
* Procesa la lógica principal.
* Realiza llamadas a otros microservicios.
* Maneja propagación de contexto y spans distribuidos.

El monitoreo de memoria puede visualizarse directamente desde New Relic Infrastructure o mediante Docker Stats.

## 5.8. ¿Cuál endpoint tiene más errores?

Los endpoints monitoreados fueron:

```http
GET /comidas
POST /insertar
GET /db/comidas
POST /db/insertar
```

Durante las pruebas realizadas no se registraron errores HTTP significativos, por lo que el porcentaje de error observado en New Relic fue:

```plaintext
0%
```

No obstante, en caso de ocurrir errores, estos quedarían automáticamente asociados al endpoint correspondiente mediante:

* TraceId
* SpanId
* HTTP Status
* Logs correlacionados
* Spans con estado ERROR
