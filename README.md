# TPMS
WGU Capstone project - server - TPMS

## Setup

- ./gradlew wrapper
- ./gradlew build
- cd ./tpm2
- npm i
- cd ..


## To create the images individually:

- docker build -t tpms-backend -f .\Dockerfile.backend .
- docker build -t tpms-frontend -f .\Dockerfile.frontend .


## To run the images (this will also build the images)

- docker-compose up
