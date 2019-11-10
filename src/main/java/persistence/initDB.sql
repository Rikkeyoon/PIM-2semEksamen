/**
 * Author:  Rikke
 * Created: 10. nov. 2019
 */

CREATE SCHEMA pim;
USE pim;

DROP TABLE IF EXISTS mobil_caterogy;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS products;

CREATE TABLE products (
	id INT NOT NULL,
    name VARCHAR(45) NOT NULL,
    description VARCHAR(200) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(category_name) REFERENCES categories(category_name)
);

CREATE TABLE categories (
	category_name VARCHAR(45) NOT NULL,
	PRIMARY KEY(category_name),
    FOREIGN KEY(category_model) REFERENCES mobil_category(model)
);

CREATE TABLE mobil_category (
	model VARCHAR(45) NOT NULL,
    PRIMARY KEY(model)
);
