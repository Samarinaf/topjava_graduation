DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM votes;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 1000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1000),
       ('ADMIN', 1001);

INSERT INTO restaurants (name)
VALUES ('BlueRestaurant'), --1002
       ('RedRestaurant'), --1003
       ('BlackRestaurant'), --1004
       ('YellowRestaurant'); --1005

INSERT INTO dishes (name, price, date, restaurant_id)
VALUES ('Pasta', 780, '2021-04-12', 1002), --BlueRestaurant
       ('Coffee', 250,'2021-04-12', 1002); --BlueRestaurant

INSERT INTO votes (date, user_id, restaurant_id)
VALUES ('2021-04-12', 1000, 1002),
       ('2021-04-13', 1000, 1005),
       ('2021-04-14', 1000, 1003);
