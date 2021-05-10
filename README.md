Restaurant Voting System
========================

Design and implementation of a REST API using Hibernate, Spring/MVC/Data JPA/Security, REST(Jackson), HSQLDB, JUnit without frontend.

### The task is:

Build a voting system for deciding where to have lunch.

-  2 types of users: admin and regular users
-  Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
-  Menu changes each day (admins do the updates)
-  Users can vote on which restaurant they want to have lunch at
-  Only one vote counted per user
-  If user votes again the same day:
-  If it is before 11:00 we assume that he changed his mind.
-  If it is after 11:00 then it is too late, vote can't be changed
-  Each restaurant provides a new menu each day.

## CURL SAMPLES:
> For windows use `Git Bash`

### Regular User:

#### get lunch menu for a restaurant
`curl -i http://localhost:8080/topjava_graduation/lunch-menu/1002?date=2021-04-12 --user user@yandex.ru:userPassword`

#### get profile
`curl -i http://localhost:8080/topjava_graduation/profile --user user@yandex.ru:userPassword`

#### delete profile
`curl -i -X DELETE http://localhost:8080/topjava_graduation/profile --user user@yandex.ru:userPassword`

#### update profile
`curl -i -X PUT -d '{"id":1000,"name":"UpdatedName","email":"update@gmail.com","enabled":false,"roles":["ADMIN"],"password":"updatedPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/profile --user user@yandex.ru:userPassword`

#### get vote 1016
`curl -i http://localhost:8080/topjava_graduation/votes/1016 --user user@yandex.ru:userPassword`

#### get all votes
`curl -i http://localhost:8080/topjava_graduation/votes --user user@yandex.ru:userPassword`

#### delete vote 1016
`curl -i -X DELETE http://localhost:8080/topjava_graduation/votes/1016 --user user@yandex.ru:userPassword`

#### create vote
`curl -i -X POST -d '{"date":"2021-05-10","user":{"id":1000,"name":"User","email":"user@yandex.ru","enabled":true,"roles":["USER"]},"restaurant":{"id":1003,"name":"RedRestaurant"}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/votes --user user@yandex.ru:userPassword`

#### update vote 1016
`curl -i -X PUT -d '{"id":1016,"date":"2021-05-10","user":{"id":1000,"name":"User","email":"user@yandex.ru","enabled":true,"roles":["USER"]},"restaurant":{"id":1005,"name":"YellowRestaurant"}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/votes/1016 --user user@yandex.ru:userPassword`


### Admin:

#### get all users
`curl -i http://localhost:8080/topjava_graduation/admin/users --user admin@gmail.com:adminPassword`

#### get user 1000
`curl -i http://localhost:8080/topjava_graduation/admin/users/1000 --user admin@gmail.com:adminPassword`

#### get user by email
`curl -i http://localhost:8080/topjava_graduation/admin/users/by?email=user@yandex.ru --user admin@gmail.com:adminPassword`

#### delete user 1000
`curl -i -X DELETE http://localhost:8080/topjava_graduation/admin/users/1000 --user admin@gmail.com:adminPassword`

#### create user
`curl -i -X POST -d '{"name":"NewUser","email":"newuser@gmail.com","enabled":false,"roles":["USER"],"password":"newPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/admin/users --user admin@gmail.com:adminPassword`

#### update user 1000
`curl -i -X PUT -d '{"id":1000,"name":"UpdatedName","email":"update@gmail.com","enabled":false,"roles":["ADMIN"],"password":"updatedPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/admin/users/1000 --user admin@gmail.com:adminPassword`

#### get all dishes
`curl -i http://localhost:8080/topjava_graduation/admin/dishes --user admin@gmail.com:adminPassword`

#### get dish 1014
`curl -i http://localhost:8080/topjava_graduation/admin/dishes/1014 --user admin@gmail.com:adminPassword`

#### delete dish 1010
`curl -i -X DELETE http://localhost:8080/topjava_graduation/admin/dishes/1010 --user admin@gmail.com:adminPassword`

#### create dish
`curl -i -X POST -d '{"name":"New Dish","price":11.11,"date":"2021-05-10","restaurant":{"id":1005,"name":"YellowRestaurant"}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/admin/dishes --user admin@gmail.com:adminPassword`

#### update dish 1006
`curl -i -X PUT -d '{"id":1006,"name":"Updated Dish","price":10.1,"date":"2021-05-10","restaurant":{"id":1004,"name":"BlackRestaurant"}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/admin/dishes/1006 --user admin@gmail.com:adminPassword`

#### get all restaurants
`curl -i http://localhost:8080/topjava_graduation/admin/restaurants --user admin@gmail.com:adminPassword`

#### get restaurant 1005
`curl -i http://localhost:8080/topjava_graduation/admin/restaurants/1005 --user admin@gmail.com:adminPassword`

#### delete restaurant 1004
`curl -i -X DELETE http://localhost:8080/topjava_graduation/admin/restaurants/1004 --user admin@gmail.com:adminPassword`

#### create restaurant
`curl -i -X POST -d '{"name":"NewRestaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/admin/restaurants --user admin@gmail.com:adminPassword`

#### update restaurant 1005
`curl -i -X PUT -d '{"id":1005,"name":"UpdatedRestaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/admin/restaurants/1005 --user admin@gmail.com:adminPassword`

#### get all votes
`curl -i http://localhost:8080/topjava_graduation/admin/votes --user admin@gmail.com:adminPassword`

#### get all votes by date
`curl -i http://localhost:8080/topjava_graduation/admin/votes/by?date=2021-04-12 --user admin@gmail.com:adminPassword`
