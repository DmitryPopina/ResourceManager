Resource manager is a REST application for manipulating resources and it's allocations (made for educational purpose).
First, you need to log in.<br>
GET http://localhost:8080/users/public/login <br>
As admin: <br>
{
"password": "admin",
"username": "admin"
} <br>
or user: <br>
{
"password": "pass",
"username": "user1"
}
Then, paste token to Authorisation header in this way: <br>
Bearer 'token' <br><br>
Then you can: <br>
List Users: GET http://localhost:8080/users/ <br>
Add new User: POST http://localhost:8080/users/public/signUp 
{
"id": 1,
"username": "user",
"password": "pass",
"email": "user1@email.com",
"role": "ROLE_USER"
} <br>
List Resource: GET http://localhost:8080/resource/ <br>
Add new Resource: POST http://localhost:8080/resource/ 
{
"id": 1,
"name": "resource1",
"owner": 2
} <br>
Update Resource: PUT  http://localhost:8080/resource/{id resource}
{
"id": 1,
"name": "resource11",
"owner": 2
} <br><br>
List Resource Allocations: GET http://localhost:8080/resource-allocations/ <br>
Add new Allocation: POST http://localhost:8080/resource-allocations/
{
"id": 1,
"allocationDate": "20.07.2022",
"allocationStartTime": "10:00:00",
"allocationEndTime": "11:00:00"
}<br><br>
Take allocation: PUT http://localhost:8080/resource-allocations/{resource}/allocate/{allocation}/{user} <br>
Free allocation: PUT http://localhost:8080/resource-allocations/{resource}/deallocate/{allocation}/