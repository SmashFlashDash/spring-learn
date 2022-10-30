DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;

CREATE TABLE books(
id INT AUTO_INCREMENT PRIMARY KEY,
author_id INT NOT NULL,
title VARCHAR(250) NOT NULL,
price_old VARCHAR(250) DEFAULT NULL,
price VARCHAR(250) DEFAULT NULL

);

CREATE TABLE authors (
id INT AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(50),
last_name VARCHAR(50)
);

-- сначала authors потом books
-- ALTER TABLE books ADD FOREIGN KEY(author_id) REFERENCES authors(id)