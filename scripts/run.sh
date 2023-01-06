#!/usr/bin/env bash

run=true
react=false

start_database() {
	echo "Starting database..."
	docker start bookshelfserver
}

running_message() {
	echo "Application started!"
	echo "API available at http://localhost:8080/api"
}

update_api() {
	run=false

	start_database

	echo "Removing API container..."
	docker stop bookshelf-api
	docker rm bookshelf-api

	echo "Building new API image..."
	cd ..
	./mvnw package -DskipTests spring-boot:build-image

	echo "Creating new API container..."
	docker run -d --name bookshelf-api --network bookshelfnet -e DB_URL=$DB_URL -e DB_USERNAME=$DB_USERNAME -e DB_PASSWORD=$DB_PASSWORD -e ALLOWED_ORIGINS=$ALLOWED_ORIGINS -p 8080:8080 bookshelf:spring-boot

	echo "Bookshelf API updated!"
	running_message

	if [ "$react" = true ]
	then
		run_react
	fi
}

show_help() {
	run=false
	echo "Usage: ./run.sh [ OPTION ]"
	echo
	echo "Option	Action"
	echo "-u	update api image"
	echo "-h	help"
	echo "-k	kill/shut down application"
	echo "-r	run with React front-end"
}

start_api() {
	start_database

	echo "Starting API..."
	docker start bookshelf-api

	running_message
}

start_react() {
	cd ../react-ui
	echo "Starting Node application..."
	npm start
}

stop_api() {
	run=false
	docker stop bookshelf-api
	docker stop bookshelfserver
	echo "Application shut down."
}

while getopts uhkr option; do
	case $option in
		u) update_api;;
		h) show_help;;
		k) stop_api;;
		r) react=true;;
	esac
done

if [ "$run" = true ]
then
	start_api
fi

if [ "$node" = true ]
then
	start_node
fi
