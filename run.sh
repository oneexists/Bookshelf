#!/usr/bin/env bash

running_message() {
	echo ">> Application started!"
	echo ">> API available at http://localhost:8080/api"
}

# START API, OPTION: -a
run_api() {
    start_database
    echo ">> Starting application..."
    docker start bookshelf-api
	running_message
}

# START DATABASE, OPTION: -d
start_database() {
	echo ">> Starting database..."
	docker start bookshelf-db
}

# HELP, OPTION: -h
show_help() {
    echo "--------------------------------------------"
	echo "      BOOKSHELF APPLICATION HELP MENU"
    echo "--------------------------------------------"
	echo "Usage: ./run.sh [ OPTION ]"
	echo
	echo "Running without passing an option will start"
	echo "the application API and React front-end."
	echo
	echo "Option			Action"
    echo "--------------------------------------------"
	echo "-a			start application API"
	echo "-d			start database"
	echo "-h			help menu"
	echo "-i			initialization instructions"
	echo "-s			setup application"
	echo "-k			kill/stop application"
	echo "-u			update application"
}

instructions() {
    echo "--------------------------------------------"
	echo "       INITIALIZATION INSTRUCTIONS"
    echo "--------------------------------------------"
    echo "Ensure Docker is installed."
    echo "Run the script with ./run.sh -s the first time"
    echo "to populate the environment variables needed."
    echo "Then add the environment variables to your"
    echo "Bash configuration either in the ~/.bashrc"
    echo "file or ~/.bash_profile file."
    echo
    echo "After this is completed, run the script again"
    echo "using ./run.sh -s to finish initialization of"
    echo "the database and application containers."
    echo
    echo "Once initialization is complete, the application"
    echo "can be started using ./run.sh -r and stopped by"
    echo "using ./run.sh -k to shut down the containers."
}

# STOP APP, OPTION: -k
stop_application() {
	echo ">> Shutting down application..."
	docker stop bookshelf-api
	docker stop bookshelf-db
	echo ">> Bookshelf application has shut down."
}

# UPDATE APP, OPTION: -u
update_application() {
    echo ">> Removing application container..."
    docker stop bookshelf-api
    docker rm bookshelf-api
    echo ">> Starting database..."
    docker start bookshelf-db
    echo ">> Updating application container..."
    ./mvnw package -DskipTests spring-boot:build-image
    docker run -d --name bookshelf-api --network bookshelf_net -e DB_URL=$BOOKSHELF_DB_URL -e DB_USERNAME=root -e DB_PASSWORD="$BOOKSHELF_DB_PASSWORD" -e ALLOWED_ORIGINS="$BOOKSHELF_ALLOWED_ORIGINS" -p 8080:8080 bookshelf:spring-boot
    echo ">> Application updated!"
	running_message
	run_react
}

# SETUP APPLICATION, OPTION: -s
setup() {
    if [[ -z $BOOKSHELF_DB_URL ]]; then
        configure_environment
    else
        configure_database
    fi
}

configure_environment() {
	docker volume create bookshelf_config
	docker volume create bookshelf_data
	docker network create bookshelf_net
	
    read -p "Database Password: " db_password
    while [[ -z $db_password ]]; do
        read -p "Database Password: " db_password
    done

	echo
	echo "Please add the following to your ~/.bashrc or"
	echo "~/.bash_profile and restart your terminal to apply"
	echo "the changes. Then run this script using ./run -i to"
	echo "finish the database initialization."
	echo
	echo "# BOOKSHELF APPLICATION CONFIG"
	echo 
	echo "export PATH=\"/usr/lib/jvm/java-17-openjdk-amd64/bin/java:\$PATH\""
	echo "export JAVA_HOME=\"/usr/lib/jvm/java-17-openjdk-amd64\""
	echo "export BOOKSHELF_DB_URL=jdbc:mysql://bookshelf-db:3306/bookshelf"
    echo "export BOOKSHELF_DB_USERNAME=root"
    echo "export BOOKSHELF_DB_PASSWORD=$db_password"
	echo "export BOOKSHELF_ALLOWED_ORIGINS=\"http://localhost:3000\""
}

configure_database() {
    echo ">> Creating application image..."
    ./mvnw package -DskipTests spring-boot:build-image
    echo ">> Application image created!"

    echo ">> Creating database..."
    docker run -it -d -v mysql_data:/var/lib/mysql -v bookshelf_config:/etc/mysql/conf.d --network bookshelf_net --name bookshelf-db -e MYSQL_ROOT_PASSWORD="$BOOKSHELF_DB_PASSWORD" -p 3306:3306 mysql
	sleep 5
    docker exec -i bookshelf-db mysql -u root -p"$BOOKSHELF_DB_PASSWORD" < sql/schema.sql
    echo ">> Database created!"

    echo ">> Creating application container..."
    docker run -d --name bookshelf-api --network bookshelf_net -e DB_URL=$BOOKSHELF_DB_URL -e DB_USERNAME=root -e DB_PASSWORD="$BOOKSHELF_DB_PASSWORD" -e ALLOWED_ORIGINS="$BOOKSHELF_ALLOWED_ORIGINS" -p 8080:8080 bookshelf:spring-boot
    echo ">> Application container created!"	
	running_message
	run_react
}

# RUN FULL-STACK, OPTION: none
run() {
	run_api
	run_react
}

run_react() {
	cd react-ui
	echo ">> Starting Node application..."
	npm start
}

while getopts adhikus option; do
    case $option in
        a) run_api;;
		d) start_database;;
        h) show_help;;
		i) instructions;;
        k) stop_application;;
        u) update_application;;
        s) setup;;
    esac
done

if [ $OPTIND -eq 1 ]; then 
	run
fi