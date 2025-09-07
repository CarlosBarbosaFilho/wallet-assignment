#!/bin/bash

set -e

DOCKER_USER="carlosbarbosafilho"
DOCKER_PASS='Mmax@016803'
IMAGE_NAME="$DOCKER_USER/wallet-assignment-bank"
CONFIG_FILE="docker-compose-hml.yml"

echo "🔑 Login in Docker Hub..."
echo  "$DOCKER_PASS" |  docker login -u "$DOCKER_USER"  --password-stdin

echo "🚀 Building images..."
docker build -t "$IMAGE_NAME" .

echo "📤 Publishing in Docker Hub..."
docker push "$IMAGE_NAME"

echo "✅ Images published successfully!"

echo "🚀 Clear and build all maven modules."
mvn clean install -DskipTests

echo "🚀 Ending all services"
docker compose -f "$CONFIG_FILE" down -v --remove-orphans

echo "🐳 Up all services with Docker Compose..."
docker compose -f "$CONFIG_FILE" up -d --build

echo "✅ Environment docker initializer successfully"