# Rental Management System

## Compile Java files

```cmd
javac -encoding UTF-8 -d out *.java model/*.java controller/*.java view/*.java
```

## Run the application

```cmd
java -cp out Main
```

## Run tests

```cmd
java -cp out model.UserTest
java -cp out PropertyTest
java -cp out PropertyOwnerTest
java -cp out TenantTest
java -cp out RentalContractTest
java -cp out IntegrationTest
```
