# TPMS
WGU Capstone project - server - TPMS

## Setup

- ./gradlew wrapper
- ./gradlew build
- cd ./tpm2
- npm i


## To create the images:

- docker build -t tpms-backend -f .\Dockerfile.backend .
- docker build -t tpms-frontend -f .\Dockerfile.frontend .


## To run the images

- docker-compose up