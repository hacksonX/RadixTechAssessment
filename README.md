Running the application
(ensure maven is installed with the path variable mvn defined)

$mvn clean install -DskipTests && mvn spring-boot:start

Once the application is running, the best way to interact with it is to visit:
http://localhost:8080/swagger-ui/index.html
Or 
Load the api docs on your Rest application of choice:
http://localhost:8080/v3/api-docs
Or use curl as follows:

1. Add a loan
$curl -X 'POST' \
  'http://localhost:8080/api/v1/loan' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "loanAmount": 10,
  "status": "ACTIVE",
  "term": 10
}'

2. Make first payment
$curl -X 'POST' \
  'http://localhost:8080/api/v1/payment' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "loanId": 5,
  "paymentAmount": 5
}'

3. Retrieve loan in current state
$curl -X 'GET' \
  'http://localhost:8080/api/v1/loan/5' \
  -H 'accept: */*'


To stop the application and free up the port again

$mvn spring-boot:stop

To run the unit tests

$mvn test


