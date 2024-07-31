CREATE DATABASE IF NOT EXISTS dbsummer DEFAULT CHARACTER SET Utf8 COLLATE Utf8_general_ci;
GRANT ALL PRIVILEGES ON dbsummer.* TO 'sumadmin'@'localhost' IDENTIFIED BY '78910';
USE dbsummer;

CREATE TABLE IF NOT EXISTS USERS (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
uname VARCHAR( 50 ),
usurname VARCHAR( 50 ),
age INT,
city VARCHAR( 50 ) default 'Athens'
);

INSERT INTO USERS (uname, usurname, age) VALUES ("Elvis", "King", 25);
INSERT INTO USERS (uname, usurname, age, city) VALUES ("George", "Loukas", 55, "LA");



