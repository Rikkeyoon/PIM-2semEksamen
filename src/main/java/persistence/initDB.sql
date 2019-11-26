/**
 * Author:  Rikke, Allan, carol
 * Created: 10. nov. 2019
 */
CREATE SCHEMA IF NOT EXISTS pim;
USE pim;

DROP TABLE IF EXISTS category_attributes;
DROP TABLE IF EXISTS attribute_values;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS attributes;


CREATE TABLE categories (
    category_name VARCHAR(45) NOT NULL,
    PRIMARY KEY(category_name)
);

CREATE TABLE products (
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(45) NOT NULL,
    description VARCHAR(200) NOT NULL,
    category_name VARCHAR(45) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(category_name) REFERENCES categories(category_name)
);

CREATE TABLE images (
    product_id INT NOT NULL,
    url VARCHAR(255) NOT NULL,
    primaryImage BIT NOT NULL,
    PRIMARY KEY(product_id, URL),
    FOREIGN KEY(product_id) REFERENCES products(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE attributes (
    id INT AUTO_INCREMENT NOT NULL,
    attribute_name VARCHAR(45) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE attribute_values(
	attribute_id INT NOT NULL,
	product_id INT NOT NULL,
	attribute_value VARCHAR(200) NOT NULL,
    PRIMARY KEY(attribute_id, product_id),
    FOREIGN KEY(attribute_id) REFERENCES attributes(id),
    FOREIGN KEY(product_id) REFERENCES products(id)
);

CREATE TABLE category_attributes (
	category_name VARCHAR(45) NOT NULL,
    attribute_id INT NOT NULL,
    FOREIGN KEY(category_name) REFERENCES categories(category_name),
    FOREIGN KEY(attribute_id) REFERENCES attributes(id)
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

CREATE OR REPLACE VIEW products_with_categories_and_attributes AS
SELECT p.id, p.name, p.description, p.category_name, a.attribute_name, av.attribute_value
FROM products p JOIN category_attributes c 
ON p.category_name = c.category_name 
JOIN attributes a ON c.attribute_id = a.id 
LEFT JOIN attribute_values av ON p.id = av.product_id AND av.attribute_id = a.id
ORDER BY p.id ASC;

CREATE OR REPLACE VIEW categories_and_attributes AS
SELECT c.category_name, attribute_name FROM categories c
JOIN category_attributes ca ON c.category_name = ca.category_name
JOIN attributes a ON ca.attribute_id = a.id;

INSERT INTO attributes(attribute_name) VALUE ("Alkohol Procent");
INSERT INTO attributes(attribute_name) VALUE ("Kamera");
INSERT INTO attributes(attribute_name) VALUE ("Processor");

INSERT INTO category_attributes VALUES ("Alkohol", 1);
INSERT INTO category_attributes VALUES ("Mobiler", 2);
INSERT INTO category_attributes VALUES ("Mobiler", 3);

INSERT INTO attribute_values VALUES (1, 13, "37,5 %");
INSERT INTO attribute_values VALUES (1, 11, "4,5 %");
INSERT INTO attribute_values VALUES (2, 6, "12 MP kamera med Dual Pixel teknologi");
INSERT INTO attribute_values VALUES (3, 6, "High-end octa-core processor");
INSERT INTO attribute_values VALUES (2, 9, "10 MP kamera");

INSERT INTO images VALUES (1, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/roedCykel.jpg", 1);
INSERT INTO images VALUES (2, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/groenCykel.jpg", 1);
INSERT INTO images VALUES (3, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/blaaCykel.jpg", 1);
INSERT INTO images VALUES (4, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/pinkCykel.jpg", 1);
INSERT INTO images VALUES (5, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/sortCykel.jpg", 1);
INSERT INTO images VALUES (6, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/samsungGalaxyS10.jpg", 1);
INSERT INTO images VALUES (7, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/appleIphone11.jpg", 1);
INSERT INTO images VALUES (8, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/huaweiP30.jpg", 1);
INSERT INTO images VALUES (9, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/xiaomiRedmiNote5.jpg", 1);
INSERT INTO images VALUES (10, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/sonyEricssonXperia.jpg", 1);
INSERT INTO images VALUES (11, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/tuborgClassic6Pack.jpg", 1);
INSERT INTO images VALUES (12, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/carlsberg6Pack.jpg", 1);
INSERT INTO images VALUES (13, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/sierraSilverTequila.jpg", 1);
INSERT INTO images VALUES (14, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/smirnoffVodka37_5.jpg", 1);
INSERT INTO images VALUES (15, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/bornholmerHonningSyp.jpg", 1);
INSERT INTO images VALUES (16, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/huaweiR5.jpg", 1);
INSERT INTO images VALUES (17, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/applePro.jpg", 1);
INSERT INTO images VALUES (18, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/asusZenbook.jpg", 1);
INSERT INTO images VALUES (19, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/acerChromebook.jpg", 1);
INSERT INTO images VALUES (20, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/lenovoThinkpadL590.jpg", 1);
INSERT INTO images VALUES (21, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/aupingRoyal.jpg", 1);
INSERT INTO images VALUES (22, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/vikingBirka.jpg", 1);
INSERT INTO images VALUES (23, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/jensenPrestige.jpg", 1);
INSERT INTO images VALUES (24, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/carpeDiemHarmano.jpg", 1);
INSERT INTO images VALUES (25, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/tempurFusion.jpg", 1);


