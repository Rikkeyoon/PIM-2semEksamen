/**
 * Author:  Rikke
 * Created: 10. nov. 2019
 */

CREATE SCHEMA IF NOT EXISTS pim;
USE pim;

DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS mobil_category;

CREATE TABLE mobil_category (
	model VARCHAR(45) NOT NULL,
    PRIMARY KEY(model)
);

CREATE TABLE categories (
	category_name VARCHAR(45) NOT NULL,
    category_model VARCHAR(45) NOT NULL,
	PRIMARY KEY(category_name),
    FOREIGN KEY(category_model) REFERENCES mobil_category(model)
);

CREATE TABLE products (
	id INT NOT NULL,
    name VARCHAR(45) NOT NULL,
    description VARCHAR(200) NOT NULL,
    category_name VARCHAR(45) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(category_name) REFERENCES categories(category_name)
);