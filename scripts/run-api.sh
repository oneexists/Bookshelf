#!/usr/bin/env bash

echo "Starting database..."
docker start bookshelfserver

echo "Starting API..."
docker start bookshelf-api

echo "Application containers started!"
