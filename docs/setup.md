# Project Setup

## Using Docker and Build Script

The Bash script for initializing and running the application can be found [here](../run.sh).
Using the Bash run script:
- `./run.sh -h` to view the help menu
- `./run.sh -i` to view setup instructions

After completing setup:
- `./run.sh` to run the application
  - to shut down React front end, use <kbd>Ctrl + C</kbd> and `./run.sh -k` to shut down the 
    API and database containers
- `./run.sh -d` to start the database
- `./run.sh -k` to shut down the API and database containers