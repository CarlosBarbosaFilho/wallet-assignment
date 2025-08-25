FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . /app


RUN mvn clean install -DskipTests


# Runtime ms-clients
FROM eclipse-temurin:17-jre-jammy AS ms-clients
WORKDIR /app
COPY --from=builder /app/ms-clients/target/ms-clients-1.0-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

# Runtime o ms-wallets
FROM eclipse-temurin:17-jre-jammy AS ms-wallets
WORKDIR /app
COPY --from=builder /app/ms-wallets/target/ms-wallets-1.0-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]

#  Runtime  ms-transactions
FROM eclipse-temurin:17-jre-jammy AS ms-transactions
WORKDIR /app
COPY --from=builder /app/ms-transactions/target/ms-transactions-1.0-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]

#  Runtime  ms-transactions
FROM eclipse-temurin:17-jre-jammy AS gateway
WORKDIR /app
COPY --from=builder /app/gateway/target/gateway-1.0-SNAPSHOT.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]