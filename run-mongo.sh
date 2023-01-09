docker run --rm -d --name mongo-test \
-e MONGO_INITDB_ROOT_USERNAME=admin \
-e MONGO_INITDB_ROOT_PASSWORD=admin \
-p 27017:27017 mongo:4.2.23-bionic
