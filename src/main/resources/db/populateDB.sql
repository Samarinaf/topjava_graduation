DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM votes;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 1000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}userPassword'),    --1000
       ('Admin', 'admin@gmail.com', '{noop}adminPassword'); --1001

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1000),
       ('ADMIN', 1001);

INSERT INTO restaurants (name)
VALUES ('BlueRestaurant'),   --1002
       ('RedRestaurant'),    --1003
       ('BlackRestaurant'),  --1004
       ('YellowRestaurant'); --1005

INSERT INTO dishes (name, price, date, restaurant_id)
VALUES ('Coffee', 2.95, '2021-04-12', 1002),                           --1006
       ('Tea', 1.25, '2021-04-12', 1003),                              --1007
       ('Oatmeal', 4.35, '2021-04-11', 1004),                          --1008
       ('Omelet', 4.75, '2021-04-12', 1002),                           --1009
       ('Orange Juice', 2.45, '2021-04-12', 1004),                     --1010
       ('Salmon Pepper Rice', 7.15, '2021-04-12', 1003),               --1011
       ('Chicken Noodle Soup', 6.75, '2021-04-11', 1005),              --1012
       ('Greek Salad', 6.35, '2021-04-11', 1005),                      --1013
       ('Cheese Tomato & Red Onion Panini', 5.95, '2021-04-12', 1002), --1014
       ('Roast Ham & Pickle Sandwich', 5.45, '2021-04-11', 1004);      --1015

INSERT INTO votes (date, user_id, restaurant_id)
VALUES ('2021-04-11', 1000, 1002), --1016 / user
       ('2021-04-12', 1000, 1005), --1017 / user
       ('2021-04-11', 1001, 1003), --1018 / admin
       ('2021-04-12', 1001, 1004); --1019 / admin
