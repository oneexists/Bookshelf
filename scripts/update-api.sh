#!/usr/bin/env bash

echo "Starting database server..."
docker start bookshelfserver

echo "Removing API container..."
docker stop bookshelf-api
docker rm bookshelf-api

echo "Building new API image..."
cd ..
./mvnw package -DskipTests spring-boot:build-image

echo "Creating new API container..."
docker run -d --name bookshelf-api --network bookshelfnet -e DB_URL=$DB_URL -e DB_USERNAME=$DB_USERNAME -e DB_PASSWORD=$DB_PASSWORD -e ALLOWED_ORIGINS=$ALLOWED_ORIGINS -p 8080:8080 bookshelf:spring-boot

echo "Bookshelf API updated!"
