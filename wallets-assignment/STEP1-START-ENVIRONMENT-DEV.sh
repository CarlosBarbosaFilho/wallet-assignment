#!/bin/bash

set -e

CONFIG_FILE="docker-compose-dev.yml"

echo "🚀 Clear and build all maven modules."
mvn clean install -DskipTests

echo "🚀 Ending all services"
docker compose -f "$CONFIG_FILE" down -v

echo "🐳 Up all services with Docker Compose..."
docker compose -f "$CONFIG_FILE" up -d --build

echo "✅ Environment docker initializer successfully"