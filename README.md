Running the application
(ensure maven is installed with the path variable mvn defined)

$mvn clean install -DskipTests && mvn spring-boot:start

Once the application is running, the best way to interact with it is to visit:
http://localhost:8080/swagger-ui/index.html
Or 
Load the api docs on your Rest application of choice:
http://localhost:8080/v3/api-docs

To stop the application and free up the port again

$mvn spring-boot:stop

To run the tests

$mvn test


