include secrets.txt

run: build
	mvn exec:java

debug: build
	mvnDebug exec:java

full: clean build
	mvn exec:java

build:
	mvn compile

.PHONY: clean
clean: 
	mvn clean

test: export DB_NAME=pokedex_test
test: export DB_USER=postgres
test: export DB_PW=postgres

test: build
	mvn test



























