CREATE DATABASE hw7_java;

USE hw7_java;

-- Таблица, представляющая сущности "Страны" 
CREATE TABLE countries
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    country_name VARCHAR(50) NOT NULL UNIQUE,
    country_code VARCHAR(4) NOT NULL UNIQUE
);

-- Таблица, представляющая сущности "Города" 
CREATE TABLE cities
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(200) NOT NULL,
    city_code VARCHAR(4) NOT NULL UNIQUE,
    country_id INT NULL,
    FOREIGN KEY(country_id) REFERENCES countries(id)
);

-- Таблица, представляющая сущности "Адреса" 
CREATE TABLE addresses
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(200) NULL,
    house VARCHAR(10) NULL,
    apartment VARCHAR(10) NULL,
	city_id INT NULL NOT NULL,
    FOREIGN KEY(city_id) REFERENCES cities(id)
);

-- Таблица, представляющая сущности "Пользователи" 
CREATE TABLE users
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	surname VARCHAR(30) NULL,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NULL,
	address_id INT NULL,
	FOREIGN KEY(address_id) REFERENCES addresses(id)
);

-- Таблица, представляющая сущности "Номера телефонов"
CREATE TABLE phone_numbers
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(8) NOT NULL UNIQUE,
    operator_code VARCHAR(4) NULL DEFAULT NULL, 
    user_id INT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id)
);


INSERT INTO countries(country_name, country_code)
VALUES
('Украина', '38'),
('Польша', '48'),
('Германия', '49'),
('Австрия', '43'),
('Великобритания', '44'),
('США', '1'),
('Швеция', '46'),
('Швейцария', '41'),
('Италия', '39'),
('Индия', '91');

INSERT INTO cities(city_name, city_code, country_id)
VALUES 
('Киев', '044', 1),
('Харьков', '057', 1),
('Одесса', '048', 1),
('Львов', '032', 1),
('Запорожье', '061', 1),
('Днепр', '056', 1),
('Рим', '06', 9),
('Берлин', '30', 3),
('Варшава', '22', 2),
('Краков', '12', 2);

INSERT INTO addresses(street, house, apartment, city_id)
VALUES
('пр-т Соборный', '1 A', '1', 5),
('ул. Украинская', '2', '2', 5),
('пр-т Металлургов', '3-Б', '4', 5),
('ул. Рекордная', '4 г', '4', 5),
('бульвар Центральный', '5', '5', 5),
('Набережная', '6', '6', 5),
('Южное шоссе', '7', '7', 5),
('ул. Школьная', '8', '8', 5),
('ул. Радио', '9', '9', 5),
('ул. Чумаченка', '10 A', '10', 5);

INSERT INTO users(surname, firstname, lastname, address_id) 
VALUES
('Иванов', 'Иван', 'Иванович', 1),
('Петров', 'Петр', 'Петрович', 2),
('Александрова', 'Александра', 'Александровна', 3),
('Иванова', 'Иванна', 'Ивановна', 4),
('Николаев', 'Николай', 'Николаевич', 5),
('Александров', 'Александр', 'Александрович', 6),
('Евгеньев', 'Евгений', 'Евгеньевич', 7),
('Евгеньева', 'Евгения', 'Евгеньевна', 8),
('Славина', 'Владислава', 'Владиславовна', 9),
('Славин', 'Владислав', 'Владиславович', 10);

INSERT INTO phone_numbers(phone_number, user_id, operator_code)
VALUES
('0000001', 1, '067'),
('0000002', 2, NULL),
('0000003', 3, '098'),
('0000004', 4, NULL),
('0000005', 5, '097'),
('0000006', 5, NULL),
('0000007', 5, '097'),
('0000008', 6, NULL),
('0000009', 7, '068'),
('0000010', 8, '066'),
('0000011', 8, '066'),
('0000012', 9, NULL),
('0000013', 10, '096');

