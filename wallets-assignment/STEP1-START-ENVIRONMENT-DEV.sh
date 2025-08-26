#!/bin/bash

set -e

echo "🚀 Limpando e construindo todos os módulos Maven..."
mvn clean install -DskipTests

echo "🚀 Finalizando todos os serviços..."
docker compose down -v

echo "🐳 Subindo todos os serviços com Docker Compose..."
docker compose -f docker-compose-dev.yml up -d --build

