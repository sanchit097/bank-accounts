# banking account service

# [ ****Problem****]()

design a service that support all the functionality mentioned in requirement [document](CodeTest_Java_Backend - Account
API.pdf)

# [ **Features**]()

**Create User Account**

One can create new Account for user using this Service. This is internal url and should be accessed only by Admin. This
is put under /internal root so that different Authentication/Authorization can be applied to it.

`curl --location --request POST 'http://localhost:8081/netBanking/internal/create' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Test user" ,
"address":{
"streetName":"plantagevej",
"houseNo": "20",
"city": "copenhagen",
"country": "denmark",
"pinCode": "1121"

},
"ssn":1234,
"emailId": "testuser@gmail.com",
"mobileNo": 991210312 }'`

**Deposit to user Account**

User can deposit money to existing account and if account is not found will return validation exception

`curl --location --request POST 'http://localhost:8081/netBanking/deposit' \
--header 'Content-Type: application/json' \
--data-raw '{
"accountNumber": 1,
"amount": 100.00 }'`

**Withdraw money to user Account**

User can withdraw money from existing account and if amount is greater that available balance will return validation
exception

`curl --location --request POST 'http://localhost:8081/netBanking/withdrawal' \
--header 'Content-Type: application/json' \
--data-raw '{
"accountNumber": 1,
"amount": 300.00 }'`

**Available balance check**

User can query for available balance using account number

```curl --location --request GET 'http://localhost:8081/netBanking/1/showBalance'

**response**

{
"balance": 100.00,
"userName": "Test user"
}
```

**Check last 10 transaction details**

User can query latest 10 transaction history (type of transaction , balance before and after)

`````curl --location --request GET 'http://localhost:8081/netBanking/1/showHistory

**response**


[
{
"balance": 100.00,
"userName": "Test user",
"beforeBalance": 200.00,
"transactionType": "withdrawal"
},
{
"balance": 200.00,
"userName": "Test user",
"beforeBalance": 100.00,
"transactionType": "deposit"
},
{
"balance": 100.00,
"userName": "Test user",
"beforeBalance": 0.00,
"transactionType": "deposit"
}
]
`````

# [Technical Features]()

* Easily navigable service package structure.
* Separation of concern using 3 layer architecture (Controller, Service, repository)
* Using separate models for Presentation(models) and Internal(domains) usage.
* Centralized Exception handling using `ApplicationExceptionHandler`. 
* Structured Error response for all exceptions along with correct Http status code. 
* Centralized understandable error codes.` ErrorCode`. 
* unit test using `junit 5`.
* API testing with `RestAssured`.
* lombok is used to define setter and getter and also for constructors.
* Builder Pattern is used to create immutable objects. Setters are used where ever necessary like for JPA and Marshaling/Unmarshaling.
* Validations 
* Separate controller of internal endpoints. Separate Service is used for  used-case to enable Single
  Responsibility Principle. 
* Functional programming is used.

# **[Further Improvements]()**

* Java Docs
* logs can be added
* More validations can be added, Null checks and mandatory value validation
* MapStruct could be used for mappers
* More junit and test coverage can be done
* Transaction history endpoint can take limit as input and can fetch respective data.
* same User can have multiple type of accounts/ more than 1 account. (for simplicity 1 account per ssn added in assignment)
* code can be done using spring reactive

# [Technology Used]()

* Spring Boot
* Java 11 - streams
* JPA/Spring data
* Lombok
* H2 in-memory db
* Junit 5
* RestAssured

# [**Unit Testing**]()

As there is not much logic in the service. I have decided to only write some unit test for controller and service class and test coverage is not done.
instead have added integration test (API testing) which can be found inside test package IT folder.

Command mvn clean install would have failed test because integration tests required running service instance. Thus first start the service and then run the tests using below

`mvn test`

# [Running the application]()

Execute below command to start the application.

`mvn spring-boot:run`

Service would be started on localhost:8081. Different APIs of the service could be tested directly from Postman.



