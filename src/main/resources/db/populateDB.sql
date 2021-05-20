DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM votes;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 1000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'userPassword'),    --1000
       ('Admin', 'admin@gmail.com', 'adminPassword'); --1001

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1000),
       ('ADMIN', 1001);

INSERT INTO restaurants (name)
VALUES ('BlueRestaurant'),   --1002
       ('RedRestaurant'),    --1003
       ('BlackRestaurant'),  --1004
       ('YellowRestaurant'); --1005

INSERT INTO dishes (name, price, dish_date, restaurant_id)
VALUES ('Coffee', 295, '2021-04-12', 1002),                           --1006
       ('Tea', 125, '2021-04-12', 1003),                              --1007
       ('Oatmeal', 435, '2021-04-11', 1004),                          --1008
       ('Omelet', 475, '2021-04-12', 1002),                           --1009
       ('Orange Juice', 248, '2021-04-11', 1004),                     --1010
       ('Salmon Pepper Rice', 715, '2021-04-12', 1003),               --1011
       ('Chicken Noodle Soup', 674, '2021-04-11', 1005),              --1012
       ('Greek Salad', 635, '2021-04-11', 1005),                      --1013
       ('Cheese Tomato & Red Onion Panini', 595, '2021-04-12', 1002), --1014
       ('Roast Ham & Pickle Sandwich', 543, '2021-04-11', 1004);      --1015

INSERT INTO votes (vote_date, user_id, restaurant_id)
VALUES ('2021-04-11', 1000, 1002), --1016 / user
       ('2021-04-12', 1000, 1005), --1017 / user
       ('2021-04-11', 1001, 1003), --1018 / admin
       ('2021-04-12', 1001, 1004); --1019 / admin
