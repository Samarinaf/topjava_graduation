DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
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

INSERT INTO dishes (name, price, restaurant_id)
VALUES ('Chicken', 780, 1002),
       ('Coffee', 250, 1002);
