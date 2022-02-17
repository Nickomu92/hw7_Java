CREATE DATABASE hw7_java;

USE hw7_java;

-- Таблица, представляющая сущность "Пользователь" 
CREATE TABLE users
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	surname VARCHAR(30) NULL,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NULL
);

CREATE TABLE countries
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    country_name VARCHAR(50) NOT NULL UNIQUE,
    country_code SMALLINT NOT NULL UNIQUE
);

CREATE TABLE cities
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(200) NOT NULL,
    city_code SMALLINT NOT NULL UNIQUE,
    country_id INT NULL,
    FOREIGN KEY(country_id) REFERENCES countries(id)
);

CREATE TABLE addresses
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(200) NULL,
    house VARCHAR(10) NULL,
    apartment VARCHAR(10) NULL,
	city_id INT NULL NOT NULL,
    FOREIGN KEY(city_id) REFERENCES cities(id)
);

-- Таблица, представляющая сущность "Номер телефона"
CREATE TABLE phone_numbers
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	-- Абонентский номер телефона (7 последних цифр), уникальный
    phone_number INT NOT NULL UNIQUE,
    user_id INT NULL,
    address_id INT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(address_id) REFERENCES addresses(id)
);

INSERT INTO users(surname, firstname, lastname) 
VALUES
('Иванов', 'Иван', 'Иванович'),
('Петров', 'Петр', 'Петрович'),
('Александрова', 'Александра', 'Александровна'),
('Иванова', 'Иванна', 'Ивановна'),
('Александров', 'Александр', 'Александрович'),
('Николаев', 'Николай', 'Николаевич'),
('Евгеньев', 'Евгений', 'Евгеньевич'),
('Евгеньева', 'Евгения', 'Евгеньевна'),
('Славина', 'Владислава', 'Владиславовна'),
('Славин', 'Владислав', 'Владиславович');

INSERT INTO countries(country_name, country_code)
VALUES
('Украина', 380),
('Польша', 48),
('Германия', 49),
('Австрия', 43),
('Великобритания', 44),
('США', 1),
('Швеция', 46),
('Швейцария', 41),
('Италия', 39),
('Индия', 91);

INSERT INTO cities(city_name, city_code, country_id)
VALUES 
('Киев', 044, 1),
('Харьков', 057, 1),
('Одесса', 048, 1),
('Львов', 032, 1),
('Запорожье', 061, 1),
('Днепр', 056, 1),
('Рим', 06, 9),
('Берлин', 30, 3),
('Варшава', 22, 2),
('Краков', 12, 2);

INSERT INTO addresses(street, house, apartment, city_id)
VALUES
('пр-т Соборный', '1 A', '1', 5),
('пр-т Металлургов', '2-Б', '2', 5);

