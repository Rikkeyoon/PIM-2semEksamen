/**
 * Author:  Rikke, Allan
 * Created: 10. nov. 2019
 */
CREATE SCHEMA IF NOT EXISTS pim;
USE pim;

DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS mobil_category;

CREATE TABLE categories (
    category_name VARCHAR(45) NOT NULL,
    PRIMARY KEY(category_name)
);

CREATE TABLE products (
    id INT NOT NULL,
    name VARCHAR(45) NOT NULL,
    description VARCHAR(200) NOT NULL,
    category_name VARCHAR(45) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(category_name) REFERENCES categories(category_name)
);

INSERT INTO categories VALUES ("Cykler");
INSERT INTO categories VALUES ("Mobiler");
INSERT INTO categories VALUES ("Alkohol");
INSERT INTO categories VALUES ("Computer");
INSERT INTO categories VALUES ("Seng");


INSERT INTO products VALUES (1, "Rød Cykel", "En Cykel der er rød", "Cykler");
INSERT INTO products VALUES (2, "Grøn Cykel", "En Cykel der er grøn", "Cykler");
INSERT INTO products VALUES (3, "Blå Cykel", "En Cykel der er blå", "Cykler");
INSERT INTO products VALUES (4, "Pink Cykel", "En Cykel der er pink", "Cykler");
INSERT INTO products VALUES (5, "SORT Cykel", "En Cykel der er SORT", "Cykler");

INSERT INTO products VALUES (6, "Samsung Galaxy S10", "Samsungs nyeste telefon med kraftig processor", "Mobiler");
INSERT INTO products VALUES (7, "Apple iphone 11", "Apples nyeste telefon med fantastisk kamera.", "Mobiler");
INSERT INTO products VALUES (8, "Hauwei P30", "Kragtig og billig telefon med mange smarte features", "Mobiler");
INSERT INTO products VALUES (9, "Xiaomi redmi note 5", "Middel performance telefon fra Xiaomi", "Mobiler");
INSERT INTO products VALUES (10, "Sony Ericsson Xperia", "Revolutionerende telefon fra Sony Erricson", "Mobiler");

INSERT INTO products VALUES (11, "Tuborg Classic 6 pack", "Klassisk god smag, til alle lejligheder", "Alkohol");
INSERT INTO products VALUES (12, "Carlsberg 6 pack", "Probably the best beer in the would", "Alkohol");
INSERT INTO products VALUES (13, "Sierra Silver Tequila", "Tequila er en mexicansk brændevin, der fremstilles af saften fra blå agave.", "Alkohol");
INSERT INTO products VALUES (14, "Smirnoff Vodka 37,5%", "Den klassiske vodka til alle fester, kan blandes med næsten alt", "Alkohol");
INSERT INTO products VALUES (15, "Bornholmer Honningsyp", "Honningsyp er en bornholmsk drik, som efter 2008 oplevede en renæssance pga. salg i fødevare- og specialbutikker til turister og bornholmere.", "Alkohol");

INSERT INTO products VALUES (16, "Hauwei R5", "Kraftig og stilfuld computer fra Hauwei", "Computer");
INSERT INTO products VALUES (17, "Apple Pro", "Appples flagship bærbar har alt hvad en bærbar kræver", "Computer");
INSERT INTO products VALUES (18, "Asus Zenbook", "Kraftig arbejds computer med mange smarte features fra Asus", "Computer");
INSERT INTO products VALUES (19, "Acer Cromebook", "Acer' cromebook med lang batteri levetid og sikker anti virus", "Computer");
INSERT INTO products VALUES (20, "Lenovo thinkpad L590", "Lenovo's thinkpad serie levere pålidelig ydelse til en god pris", "Computer");

INSERT INTO products VALUES (21, "Auping Royal", "Fantastisk seng fra Auping med 5 motorer i hver bund samt stilfuld sengeramme.", "Seng");
INSERT INTO products VALUES (22, "Viking Birka", "Kontinental seng fra Viking, med 7 zoner og 2 pocketfjedre madras", "Seng");
INSERT INTO products VALUES (23, "Jensen Prestige", "Kontinental seng med elevation, 5 zoner madras med softline topmadras", "Seng");
INSERT INTO products VALUES (24, "Carpe Diem Harmano", "Carpe Diem Harmano leverer det bedste fra Darpe Diem.", "Seng");
INSERT INTO products VALUES (25, "Tempur Fusion", "TEMPUR® Fusion Box gør valget dejlig enkelt. Du får både de trykaflastende fordele og springmadrassens bevægelighed.", "Seng");

CREATE SCHEMA IF NOT EXISTS pimTest;
USE pimTest;

DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS mobil_category;

CREATE TABLE categories (
    category_name VARCHAR(45) NOT NULL,
    PRIMARY KEY(category_name)
);

CREATE TABLE products (
    id INT NOT NULL,
    name VARCHAR(45) NOT NULL,
    description VARCHAR(200) NOT NULL,
    category_name VARCHAR(45) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(category_name) REFERENCES categories(category_name)
);

INSERT INTO categories VALUES ("Cykler");
INSERT INTO categories VALUES ("Mobiler");
INSERT INTO categories VALUES ("Alkohol");
INSERT INTO categories VALUES ("Computer");
INSERT INTO categories VALUES ("Seng");


INSERT INTO products VALUES (1, "Rød Cykel", "En Cykel der er rød", "Cykler");
INSERT INTO products VALUES (2, "Grøn Cykel", "En Cykel der er grøn", "Cykler");
INSERT INTO products VALUES (3, "Blå Cykel", "En Cykel der er blå", "Cykler");
INSERT INTO products VALUES (4, "Pink Cykel", "En Cykel der er pink", "Cykler");
INSERT INTO products VALUES (5, "SORT Cykel", "En Cykel der er SORT", "Cykler");

INSERT INTO products VALUES (6, "Samsung Galaxy S10", "Samsungs nyeste telefon med kraftig processor", "Mobiler");
INSERT INTO products VALUES (7, "Apple iphone 11", "Apples nyeste telefon med fantastisk kamera.", "Mobiler");
INSERT INTO products VALUES (8, "Hauwei P30", "Kragtig og billig telefon med mange smarte features", "Mobiler");
INSERT INTO products VALUES (9, "Xiaomi redmi note 5", "Middel performance telefon fra Xiaomi", "Mobiler");
INSERT INTO products VALUES (10, "Sony Ericsson Xperia", "Revolutionerende telefon fra Sony Erricson", "Mobiler");

INSERT INTO products VALUES (11, "Tuborg Classic 6 pack", "Klassisk god smag, til alle lejligheder", "Alkohol");
INSERT INTO products VALUES (12, "Carlsberg 6 pack", "Probably the best beer in the would", "Alkohol");
INSERT INTO products VALUES (13, "Sierra Silver Tequila", "Tequila er en mexicansk brændevin, der fremstilles af saften fra blå agave.", "Alkohol");
INSERT INTO products VALUES (14, "Smirnoff Vodka 37,5%", "Den klassiske vodka til alle fester, kan blandes med næsten alt", "Alkohol");
INSERT INTO products VALUES (15, "Bornholmer Honningsyp", "Honningsyp er en bornholmsk drik, som efter 2008 oplevede en renæssance pga. salg i fødevare- og specialbutikker til turister og bornholmere.", "Alkohol");

INSERT INTO products VALUES (16, "Hauwei R5", "Kraftig og stilfuld computer fra Hauwei", "Computer");
INSERT INTO products VALUES (17, "Apple Pro", "Appples flagship bærbar har alt hvad en bærbar kræver", "Computer");
INSERT INTO products VALUES (18, "Asus Zenbook", "Kraftig arbejds computer med mange smarte features fra Asus", "Computer");
INSERT INTO products VALUES (19, "Acer Cromebook", "Acer' cromebook med lang batteri levetid og sikker anti virus", "Computer");
INSERT INTO products VALUES (20, "Lenovo thinkpad L590", "Lenovo's thinkpad serie levere pålidelig ydelse til en god pris", "Computer");

INSERT INTO products VALUES (21, "Auping Royal", "Fantastisk seng fra Auping med 5 motorer i hver bund samt stilfuld sengeramme.", "Seng");
INSERT INTO products VALUES (22, "Viking Birka", "Kontinental seng fra Viking, med 7 zoner og 2 pocketfjedre madras", "Seng");
INSERT INTO products VALUES (23, "Jensen Prestige", "Kontinental seng med elevation, 5 zoner madras med softline topmadras", "Seng");
INSERT INTO products VALUES (24, "Carpe Diem Harmano", "Carpe Diem Harmano leverer det bedste fra Darpe Diem.", "Seng");
INSERT INTO products VALUES (25, "Tempur Fusion", "TEMPUR® Fusion Box gør valget dejlig enkelt. Du får både de trykaflastende fordele og springmadrassens bevægelighed.", "Seng");


select * from pimTest.products;