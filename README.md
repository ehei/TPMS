# TPMS
WGU Capstone project - server - TPMS

## Setup

- ./gradlew wrapper
- ./gradlew buildDependents
- ./gradlew build


## To create the images:

- docker build -t tpms-backend -f ./docker/backend .
- docker build -t tpms-frontend -f ./docker/frontend ./tpm2

## To run the images

- docker compose -f ./docker/docker-compose.yaml
