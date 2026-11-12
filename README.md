# Basic-CRUD-Application
This is the 1st CRUD Application I built using SpringBoot and Spring JPA. It processes the HTTP request sent by the client and performs the business logic and does the CRUD operation on the Postgres Database.
It taught me how a backend server handles an HTTP request, how it accepts the input, perform the business logic and then manupulate the data in the Database.
It made me familiar with Spring Web and Spring JPA and how can we perfrom SQL queries without writing a single query.
UPDATE-1
Used Data Transfer Objects(DTOs) to loosen up the coupling between client and server and control what client can send or access.
Validated on data provided by client before entering that in the database.
Used a global exception handler to handle various exceptions.
Send detailed report to client about each request with correct status codes and a response body.
UPDATE-2
Used filters to authenticate hardcoded keys, provide a unique request id and logging the request and response details in the terminal.
