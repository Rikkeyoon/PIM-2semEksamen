/**
 * Author:  Rikke, Allan, carol, Nina
 * Created: 10. nov. 2019
 * Run this as a localhost database.
 */
DROP SCHEMA IF EXISTS pimtest;
CREATE SCHEMA pimtest;

CREATE SCHEMA IF NOT EXISTS pimTest_template;
USE pimTest_template;

DROP TABLE IF EXISTS category_attributes;
DROP TABLE IF EXISTS attribute_values;
DROP TABLE IF EXISTS product_tags;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS attributes;


CREATE TABLE categories (
    id INT auto_increment,
    category_name VARCHAR(45) NOT NULL,
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE products (
    id INT AUTO_INCREMENT NOT NULL,
    item_number INT UNIQUE,
    name VARCHAR(45),
    brand VARCHAR(45),
    description VARCHAR(200),
    category_id INT,
    supplier VARCHAR(45),
    seo_text VARCHAR(45),
    status INT,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE images (
    product_id INT NOT NULL,
    url VARCHAR(255) NOT NULL,
    primaryImage BIT NOT NULL,
    PRIMARY KEY(product_id, URL),
    FOREIGN KEY(product_id) REFERENCES products(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE tags(
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE product_tags (
    tag_id INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY(tag_id, product_id),
	FOREIGN KEY(tag_id) REFERENCES tags(id),
    FOREIGN KEY(product_id) REFERENCES products(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE attributes (
    id INT AUTO_INCREMENT NOT NULL,
    attribute_name VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE attribute_values(
	attribute_id INT NOT NULL,
	product_id INT NOT NULL,
	attribute_value VARCHAR(200) NOT NULL,
    PRIMARY KEY(attribute_id, product_id),
    FOREIGN KEY(attribute_id) REFERENCES attributes(id),
    FOREIGN KEY(product_id) REFERENCES products(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE category_attributes (
	category_id INT NOT NULL,
    attribute_id INT NOT NULL,
    FOREIGN KEY(category_id) REFERENCES categories(id) ON DELETE CASCADE,
    FOREIGN KEY(attribute_id) REFERENCES attributes(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO categories(category_name) VALUES ("Bicycle");
INSERT INTO categories(category_name)  VALUES ("Mobile Phone");
INSERT INTO categories(category_name)  VALUES ("Alcohol");
INSERT INTO categories(category_name)  VALUES ("Computer");
INSERT INTO categories(category_name)  VALUES ("Bed");

INSERT INTO products VALUES (1,132, "Red Bicycle", "Winther", "A Bicycle that is red. It has everything a kid needs when learning to ride a bike, a bikeseat, the wheels, and of course the training wheels.", 1,"Jupiter", "Bicycle, Bike, Transport, Sport",100);
INSERT INTO products VALUES (2,207, "Green Bicycle", "Winther", "A Bicycle that is green. Perfect for the everyday cyclist who needs a modern bike, without all the unnecessary fuss. It is the best bike on the market, if you are a person living in the city.", 1,"Jupiter", "Bicycle, Bike, Transport, Sport",100);
INSERT INTO products VALUES (3,345, "Blue Bicycle", "Winther", "A Bicycle that is blue. The prettiest bike in town, nothing compares to this bicycle, it has everything you need in a bike.", 1,"Jupiter", "Bicycle, Bike, Transport, Sport",100);
INSERT INTO products VALUES (4,426, "Pink Bicycle", "Winther", "A Bicycle that is pink. It has everything a kid needs when learning to ride a bike, with this bike you will be fast as lightning. ", 1,"Jupiter", "Bicycle, Bike, Transport, Sport",100);
INSERT INTO products VALUES (5,574, "Black Bicycle", "Winther", "A Bicycle that is black. No one will see you in the dark, it is a bicycle for blending in completely. It is fast, lightweight and easy to use.", 1,"Jupiter", "Bicycle, Bike, Transport, Sport",100);

INSERT INTO products VALUES (6,666, "Samsung Galaxy S10", "Samsung", "Samsungs newest mobile phone with a powerful processor. The Samsung Galaxy S10 is powered by a 3400mAh non-removable battery. The Samsung Galaxy S10 supports wireless charging.", 2,"Samsung Electronics", "Smartphone, Android",100);
INSERT INTO products VALUES (7,796, "Apple iphone 11", "Apple", "Apples newest mobile phone with a fantastic camera. A transformative triple‑camera system that adds tons of capability without complexity. An unprecedented leap in battery life", 2,"Apple Corp", "Smartphone, IOS",100);
INSERT INTO products VALUES (8,842, "Huawei P30", "Huawei", "Powerful and cheap phone with a lot of smart features. Huawei P30 smartphone has a OLED display. It weighs only 165 grams. It is backed by a 3650 mAh battery. ", 2,"Huawei Util", "Smartphone, Android",100);
INSERT INTO products VALUES (9,963, "Xiaomi redmi note 5", "Xiaomi", "Average performance phone from Xiaomi. The phone runs on Android 7.1.2 Nougat with Xiaomi's custom MIUI 9 on top. It has a non-removable 4000mAh battery and supports quick charging.", 2,"Xiome Tech", "Smartphone, Android",100);
INSERT INTO products VALUES (10,1073, "Sony Ericsson Xperia", "Sony Ericson", "Revolutionizing phone from Sony Erricson. Android 2.3 running smartphone with a slide out landscape QWERTY keyboard and a 1GHz Snapdragon processor under the hood.", 2,"Ericsson Manufacturing", "Smartphone, Android",100);

INSERT INTO products VALUES (11,1124, "Tuborg Classic 6 pack", "Tuborg", "Classic great taste for every occasion. It's brewed on lager malt, a slightly roasted, bright type of malt which results in the well-known mild, fresh taste and aroma of flowers and grain.", 3,"Tuborg Bryghus","Beer, Party",100);
INSERT INTO products VALUES (12,1269, "Carlsberg 6 pack", "Carlsberg", "Probably the best beer in the world. At Carlsberg, the pursuit of better beer is in our DNA. Our perfectly balanced Danish Pilsner is wonderfully crisp and refreshing.", 3,"Carlsberg Aps","Beer, Party",100);
INSERT INTO products VALUES (13,1309, "Sierra Silver Tequila", "Sierra Silver", "Tequila is a Mexican brandy, produced by the juice from blue agave. Sierra Tequila Silver is a rested, clear Tequila. Its full, fruity, fresh aroma that's dominated by a slight chilli note.", 3,"Navada Lakeview brewery","Tequila, Party",100);
INSERT INTO products VALUES (14,1419, "Smirnoff Vodka 37,5%", "Smirnoff", "The classic vodka for every party, can be mixed with almost everything. Smirnoff (/ˈsmɪərnɒf/; Russian: [smʲɪrˈnof]) is a brand of vodka owned and produced by the British company Diageo.", 3,"Smirnoff AS","Vodka, Party",100);
INSERT INTO products VALUES (15,1526, "Bornholmer Honningsyp", "Bornholm", "Honningsyp is a schnapps from Bornholm, after 2008 it experienced a renaissance due to sales in food- and specialityshops to tourists and Bornholmers.", 3,"Bornholmers","Bornholm, Love",100);

INSERT INTO products VALUES (16,1694, "Huawei R5", "Huawei", "Powerful and stylish computer from Huawei. Touch Screen: Ultra-slim bezel at only 5.2mm, 1920x1080 Full High Definition (FHD), 16:9 wide IPS LCD display.", 4,"Huawei Util","Laptop, Windows",100);
INSERT INTO products VALUES (17,1753, "Apple Pro", "Apple", "Mac Pro is designed for pros who need the ultimate in CPU performance. Appples flagship laptop has everything a laptop requires. Backlit keyboard, Touch Bar, Touch ID, and Force Touch trackpad. ", 4,"Apple Corp","Laptop, MAC OSx",100);
INSERT INTO products VALUES (18,1826, "Asus Zenbook", "Asus", "Powerful computer for work with many smart features from Asus. Like most Zenbooks, this is a delight for the eyes. Opening the lid provides more of that blue-and-gold motif. ", 4,"Asus Inc","Laptop, Windows",100);
INSERT INTO products VALUES (19,1963, "Acer Chromebook", "Acer", "Acer's chromebook with a long battery life and secure anti virus. A Chromebook is a laptop or tablet running the Linux-based Chrome OS as its operating system.", 4,"Acer Corp","Laptop, Windows",100);
INSERT INTO products VALUES (20,2042, "Lenovo thinkpad L590", "Lenovo", "Lenovo's thinkpad serie delivers a reliable performance for a good price. The Lenovo ThinkPad L Series offers well-equipped business laptops for those who operate on a budget.", 4,"IBM","Laptop, Windows",100);

INSERT INTO products VALUES (21,2139, "Auping Royal", "Auping", "Fantastic bed from fra Auping with 5 motors in each bottom as well as a classy bed frame. It mkes living easy, comfortable, and it looks amazing.", 5,"Royal Bed Import","Motor Bed, Special Bed",100);
INSERT INTO products VALUES (22,2248, "Viking Birka", "Viking", "Continental bed from Viking, with 7 zones and 2 pocketspings mattress. The Birka series is the ideal choice when choosing choosing something economical that still offers great support.", 5,"Nordic Bedcompany","Continental Bed",100);
INSERT INTO products VALUES (23,2302, "Jensen Prestige", "Jensen", "Continental bed with elevation, 5 zoned mattress with a softline top mattress. It has the Jensen Original zonesystem with ekstra soft shoulderzone.", 5,"Nordic Becompany","Continental Bed",100);
INSERT INTO products VALUES (24,2412, "Carpe Diem Harmano", "Carpe Diem", "Carpe Diem Harmano delivers the best from Carpe Diem. It is a great continentalbed with the classical doublemattresses. It comes with our patentet CARPE DIEM BEDS CONTOUR POCKET SYSTEM.", 5,"Carpe Diem me Hombre","Continental Seng",100);
INSERT INTO products VALUES (25,2583, "Tempur Fusion", "Tempur", "TEMPUR® Fusion Box makes the choice easy. You get both the pressure relieving advantages and the spring mattresses mobility. Includes 7 cm TEMPUR topmattress with washable cover.", 5,"Royal Bed Import","Spring Mattress",100);

CREATE OR REPLACE VIEW products_with_categories_and_attributes AS
SELECT p.id,p.item_number, p.name, p.brand, p.description, p.category_id, 
p.supplier, p.seo_text, p.status, a.attribute_name, av.attribute_value
FROM products p JOIN category_attributes c 
ON p.category_id = c.category_id
JOIN attributes a ON c.attribute_id = a.id 
LEFT JOIN attribute_values av ON p.id = av.product_id AND av.attribute_id = a.id
ORDER BY p.id ASC;

CREATE OR REPLACE VIEW categories_and_attributes AS
SELECT c.id AS category_id, attribute_name FROM categories c
JOIN category_attributes ca ON c.id = ca.category_id
JOIN attributes a ON ca.attribute_id = a.id;

INSERT INTO attributes(attribute_name) VALUE ("Bike size");
INSERT INTO attributes(attribute_name) VALUE ("Number of gears");
INSERT INTO attributes(attribute_name) VALUE ("Bikeseat");
INSERT INTO attributes(attribute_name) VALUE ("Number of wheels");
INSERT INTO attributes(attribute_name) VALUE ("Lights front and back");
INSERT INTO attributes(attribute_name) VALUE ("Camera");
INSERT INTO attributes(attribute_name) VALUE ("Screen-size");
INSERT INTO attributes(attribute_name) VALUE ("Homebutton");
INSERT INTO attributes(attribute_name) VALUE ("Battery life");
INSERT INTO attributes(attribute_name) VALUE ("Colour");
INSERT INTO attributes(attribute_name) VALUE ("Alcohol Percentage");
INSERT INTO attributes(attribute_name) VALUE ("Volume");
INSERT INTO attributes(attribute_name) VALUE ("Gluten");
INSERT INTO attributes(attribute_name) VALUE ("Protein");
INSERT INTO attributes(attribute_name) VALUE ("Carbs");
INSERT INTO attributes(attribute_name) VALUE ("Processor");
INSERT INTO attributes(attribute_name) VALUE ("Computer feel");
INSERT INTO attributes(attribute_name) VALUE ("Ram");
INSERT INTO attributes(attribute_name) VALUE ("Mattressheight");
INSERT INTO attributes(attribute_name) VALUE ("Matttresswidth");
INSERT INTO attributes(attribute_name) VALUE ("Mattresslength");
INSERT INTO attributes(attribute_name) VALUE ("Bedframe colour");

INSERT INTO category_attributes VALUES (1, 1);
INSERT INTO category_attributes VALUES (1, 2);
INSERT INTO category_attributes VALUES (1, 3);
INSERT INTO category_attributes VALUES (1, 4);
INSERT INTO category_attributes VALUES (1, 5);
INSERT INTO category_attributes VALUES (1, 10);
INSERT INTO category_attributes VALUES (2, 6);
INSERT INTO category_attributes VALUES (2, 7);
INSERT INTO category_attributes VALUES (2, 8);
INSERT INTO category_attributes VALUES (2, 9);
INSERT INTO category_attributes VALUES (2, 10);
INSERT INTO category_attributes VALUES (3, 11);
INSERT INTO category_attributes VALUES (3, 12);
INSERT INTO category_attributes VALUES (3, 13);
INSERT INTO category_attributes VALUES (3, 14);
INSERT INTO category_attributes VALUES (3, 15);
INSERT INTO category_attributes VALUES (4, 16);
INSERT INTO category_attributes VALUES (4, 17);
INSERT INTO category_attributes VALUES (4, 18);
INSERT INTO category_attributes VALUES (5, 20);
INSERT INTO category_attributes VALUES (5, 21);
INSERT INTO category_attributes VALUES (5, 22);

INSERT INTO attribute_values VALUES (1, 1, "XXS");
INSERT INTO attribute_values VALUES (1, 2, "L");
INSERT INTO attribute_values VALUES (1, 3, "L");
INSERT INTO attribute_values VALUES (1, 4, "XXS");
INSERT INTO attribute_values VALUES (1, 5, "L");
INSERT INTO attribute_values VALUES (2, 1, "3");
INSERT INTO attribute_values VALUES (2, 2, "7");
INSERT INTO attribute_values VALUES (2, 3, "7");
INSERT INTO attribute_values VALUES (2, 4, "21");
INSERT INTO attribute_values VALUES (2, 5, "3");
INSERT INTO attribute_values VALUES (3, 1, "Yes");
INSERT INTO attribute_values VALUES (3, 2, "Yes");
INSERT INTO attribute_values VALUES (3, 3, "Yes");
INSERT INTO attribute_values VALUES (3, 4, "Yes");
INSERT INTO attribute_values VALUES (3, 5, "Yes");
INSERT INTO attribute_values VALUES (4, 1, "3");
INSERT INTO attribute_values VALUES (4, 2, "2");
INSERT INTO attribute_values VALUES (4, 3, "2");
INSERT INTO attribute_values VALUES (4, 4, "4");
INSERT INTO attribute_values VALUES (4, 5, "2");
INSERT INTO attribute_values VALUES (5, 1, "Yes");
INSERT INTO attribute_values VALUES (5, 2, "Yes");
INSERT INTO attribute_values VALUES (5, 3, "Yes");
INSERT INTO attribute_values VALUES (5, 4, "Yes");
INSERT INTO attribute_values VALUES (5, 5, "Yes");
INSERT INTO attribute_values VALUES (10, 1, "red");
INSERT INTO attribute_values VALUES (10, 2, "Green");
INSERT INTO attribute_values VALUES (10, 3, "Blue");
INSERT INTO attribute_values VALUES (10, 4, "Pink");
INSERT INTO attribute_values VALUES (10, 5, "Sort");
INSERT INTO attribute_values VALUES (6, 6, "12 MP camera with Dual Pixel technologi");
INSERT INTO attribute_values VALUES (6, 7, "10 MP camera");
INSERT INTO attribute_values VALUES (6, 8, "10 MP camera");
INSERT INTO attribute_values VALUES (6, 9, "10 MP camera");
INSERT INTO attribute_values VALUES (6, 10, "10 MP camera");
INSERT INTO attribute_values VALUES (7, 6, "A screen covering the whole phone");
INSERT INTO attribute_values VALUES (7, 7, "A screen all the way to the edge");
INSERT INTO attribute_values VALUES (7, 8, "A screen all the way to the edge");
INSERT INTO attribute_values VALUES (7, 9, "Large screen");
INSERT INTO attribute_values VALUES (7, 10, "X-Small screen");
INSERT INTO attribute_values VALUES (8, 6, "No");
INSERT INTO attribute_values VALUES (8, 7, "No");
INSERT INTO attribute_values VALUES (8, 8, "No");
INSERT INTO attribute_values VALUES (8, 9, "No");
INSERT INTO attribute_values VALUES (8, 10, "Yes");
INSERT INTO attribute_values VALUES (9, 6, "36 Hours");
INSERT INTO attribute_values VALUES (9, 7, "36 Hours");
INSERT INTO attribute_values VALUES (9, 8, "24 Hours");
INSERT INTO attribute_values VALUES (9, 9, "28 Hours");
INSERT INTO attribute_values VALUES (9, 10, "48 Hours");
INSERT INTO attribute_values VALUES (10, 6, "Silver");
INSERT INTO attribute_values VALUES (10, 7, "Purple");
INSERT INTO attribute_values VALUES (10, 8, "Black");
INSERT INTO attribute_values VALUES (10, 9, "Blue");
INSERT INTO attribute_values VALUES (10, 10, "Black");
INSERT INTO attribute_values VALUES (11, 13, "37,5 %");
INSERT INTO attribute_values VALUES (11, 11, "4,6 %");
INSERT INTO attribute_values VALUES (11, 12, "4,6 %");
INSERT INTO attribute_values VALUES (11, 14, " 37,5%");
INSERT INTO attribute_values VALUES (11, 15, "32 %");
INSERT INTO attribute_values VALUES (12, 11, "6 x 33cl");
INSERT INTO attribute_values VALUES (12, 12, "6 x 33cl");
INSERT INTO attribute_values VALUES (12, 13, "1 L");
INSERT INTO attribute_values VALUES (12, 14, "1 L");
INSERT INTO attribute_values VALUES (12, 15, "1 L");
INSERT INTO attribute_values VALUES (13, 11, "Yes");
INSERT INTO attribute_values VALUES (13, 12, "Yes");
INSERT INTO attribute_values VALUES (13, 13, "No");
INSERT INTO attribute_values VALUES (13, 14, "No");
INSERT INTO attribute_values VALUES (13, 15, "No");
INSERT INTO attribute_values VALUES (14, 11, "No");
INSERT INTO attribute_values VALUES (14, 12, "No");
INSERT INTO attribute_values VALUES (14, 13, "No");
INSERT INTO attribute_values VALUES (14, 14, "No");
INSERT INTO attribute_values VALUES (14, 15, "No");
INSERT INTO attribute_values VALUES (15, 11, "A lot");
INSERT INTO attribute_values VALUES (15, 12, "A lot");
INSERT INTO attribute_values VALUES (15, 13, "A lot");
INSERT INTO attribute_values VALUES (15, 14, "A lot");
INSERT INTO attribute_values VALUES (15, 15, "Way to many");
INSERT INTO attribute_values VALUES (16, 16, "High-end octa-core processor");
INSERT INTO attribute_values VALUES (16, 17, "High-end octa-core processor");
INSERT INTO attribute_values VALUES (16, 18, "High-end octa-core processor");
INSERT INTO attribute_values VALUES (16, 19, "High-end octa-core processor");
INSERT INTO attribute_values VALUES (16, 20, "High-end octa-core processor");
INSERT INTO attribute_values VALUES (7, 16, "13");
INSERT INTO attribute_values VALUES (7, 17, "13");
INSERT INTO attribute_values VALUES (7, 18, "15");
INSERT INTO attribute_values VALUES (7, 19, "11");
INSERT INTO attribute_values VALUES (7, 20, "15");
INSERT INTO attribute_values VALUES (17, 16, "Aluminium");
INSERT INTO attribute_values VALUES (17, 17, "Aluminium");
INSERT INTO attribute_values VALUES (17, 18, "Plastic");
INSERT INTO attribute_values VALUES (17, 19, "Plastic");
INSERT INTO attribute_values VALUES (17, 20, "Plastic");
INSERT INTO attribute_values VALUES (18, 16, "4GB");
INSERT INTO attribute_values VALUES (18, 17, "8GB");
INSERT INTO attribute_values VALUES (18, 18, "8GB");
INSERT INTO attribute_values VALUES (18, 19, "4GB");
INSERT INTO attribute_values VALUES (18, 20, "4GB");
INSERT INTO attribute_values VALUES (19, 21, "20cm");
INSERT INTO attribute_values VALUES (19, 22, "80cm");
INSERT INTO attribute_values VALUES (19, 23, "70cm");
INSERT INTO attribute_values VALUES (19, 24, "70cm");
INSERT INTO attribute_values VALUES (19, 25, "40cm");
INSERT INTO attribute_values VALUES (20, 21, "140cm");
INSERT INTO attribute_values VALUES (20, 22, "180cm");
INSERT INTO attribute_values VALUES (20, 23, "160cm");
INSERT INTO attribute_values VALUES (20, 24, "170cm");
INSERT INTO attribute_values VALUES (20, 25, "140cm");
INSERT INTO attribute_values VALUES (21, 21, "200cm");
INSERT INTO attribute_values VALUES (21, 22, "200cm");
INSERT INTO attribute_values VALUES (21, 23, "200cm");
INSERT INTO attribute_values VALUES (21, 24, "200cm");
INSERT INTO attribute_values VALUES (21, 25, "200cm");
INSERT INTO attribute_values VALUES (22, 21, "Sandcoloured");
INSERT INTO attribute_values VALUES (22, 22, "Silver");
INSERT INTO attribute_values VALUES (22, 23, "Silver");
INSERT INTO attribute_values VALUES (22, 24, "Black");
INSERT INTO attribute_values VALUES (22, 25, "Silver");
INSERT INTO attribute_values VALUES (6, 16, "Yes");
INSERT INTO attribute_values VALUES (6, 17, "Yes");
INSERT INTO attribute_values VALUES (6, 18, "Yes");
INSERT INTO attribute_values VALUES (6, 19, "Yes");
INSERT INTO attribute_values VALUES (6, 20, "Yes");


INSERT INTO images VALUES (1, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/roedCykel.jpg", 1);
INSERT INTO images VALUES (1, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576248953/red1_c3w6u8.jpg", 0);
INSERT INTO images VALUES (1, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576248962/red2_vnpujw.jpg", 0);
INSERT INTO images VALUES (2, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/groenCykel.jpg", 1);
INSERT INTO images VALUES (2, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576260771/green1_tsvajk.jpg", 0);
INSERT INTO images VALUES (2, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576260772/green2_yassih.jpg", 0);
INSERT INTO images VALUES (3, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/blaaCykel.jpg", 1);
INSERT INTO images VALUES (3, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576261146/blue1_ltd3fp.jpg", 0);
INSERT INTO images VALUES (3, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576261146/blue2_zgzy8w.jpg", 0);
INSERT INTO images VALUES (4, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/pinkCykel.jpg", 1);
INSERT INTO images VALUES (4, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576261238/pink2_ssiux9.jpg", 0);
INSERT INTO images VALUES (4, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576261237/pink1_ukcwdv.jpg", 0);
INSERT INTO images VALUES (5, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/sortCykel.jpg", 1);
INSERT INTO images VALUES (5, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576261351/sort1_wxxkbq.jpg", 0);
INSERT INTO images VALUES (5, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576261352/sort2_wzhy0q.jpg", 0);
INSERT INTO images VALUES (6, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/samsungGalaxyS10.jpg", 1);
INSERT INTO images VALUES (6, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576261528/samsungGalaxyS102_pemcmz.png", 0);
INSERT INTO images VALUES (6, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576261528/samsungGalaxyS101_uwkm0r.png", 0);
INSERT INTO images VALUES (6, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576261529/samsungGalaxyS103_nlqpab.png", 0);
INSERT INTO images VALUES (7, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/appleIphone11.jpg", 1);
INSERT INTO images VALUES (7, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576262133/iphone112_hxizny.png", 0);
INSERT INTO images VALUES (7, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576262133/iphone113_mxcen9.png", 0);
INSERT INTO images VALUES (7, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576262133/iphone111_r8l8v6.png", 0);
INSERT INTO images VALUES (7, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576262134/iphone114_bm0wqn.png", 0);
INSERT INTO images VALUES (8, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/huaweiP30.jpg", 1);
INSERT INTO images VALUES (8, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576263262/huaweiP301_uqnli6.png", 0);
INSERT INTO images VALUES (8, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576263263/huaweiP303_jznpsv.png", 0);
INSERT INTO images VALUES (8, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576263263/huaweiP302_h28758.png", 0);
INSERT INTO images VALUES (9, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/xiaomiRedmiNote5.jpg", 1);
INSERT INTO images VALUES (9, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576263859/xiaomiRedmiNote51_edhhxi.png", 0);
INSERT INTO images VALUES (9, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576263859/xiaomiRedmiNote52_zxcwi1.png", 0);
INSERT INTO images VALUES (9, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576263858/xiaomiRedmiNote5_rhy6h4.jpg", 0);
INSERT INTO images VALUES (10, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/sonyEricssonXperia.jpg", 1);
INSERT INTO images VALUES (10, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576264653/sonyEricssonXperia1_q3qsej.png", 0);
INSERT INTO images VALUES (10, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576264654/sonyEricssonXperia2_pu4rto.png", 0);
INSERT INTO images VALUES (11, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/tuborgClassic6Pack.jpg", 1);
INSERT INTO images VALUES (11, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576265110/tuborgClassic_pcvtdq.png", 0);
INSERT INTO images VALUES (12, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/carlsberg6Pack.jpg", 1);
INSERT INTO images VALUES (12, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576265295/carlsberg6Pack1_c1dj2i.png", 0);
INSERT INTO images VALUES (13, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/sierraSilverTequila.jpg", 1);
INSERT INTO images VALUES (14, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/smirnoffVodka37_5.jpg", 1);
INSERT INTO images VALUES (14, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576265969/smirnoffVodka37_5_1_aklcwy.png", 0);
INSERT INTO images VALUES (15, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/bornholmerHonningSyp.jpg", 1);
INSERT INTO images VALUES (15, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576266202/bornholmerHonningSyp1_c3jns7.png", 0);
INSERT INTO images VALUES (16, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/huaweiR5.jpg", 1);
INSERT INTO images VALUES (16, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576266524/huaweiR51_jag03z.png", 0);
INSERT INTO images VALUES (16, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576266524/huaweiR52_li3zvw.png", 0);
INSERT INTO images VALUES (17, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/applePro.jpg", 1);
INSERT INTO images VALUES (17, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576266777/applePro1_m8lxyl.png", 0);
INSERT INTO images VALUES (17, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576266777/applePro3_gxcduc.png", 0);
INSERT INTO images VALUES (17, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576266778/applePro2_p7giwf.png", 0);
INSERT INTO images VALUES (18, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/asusZenbook.jpg", 1);
INSERT INTO images VALUES (18, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576266991/asusZenbook1_tncftm.png", 0);
INSERT INTO images VALUES (18, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576266991/asusZenbook2_alyjbk.png", 0);
INSERT INTO images VALUES (19, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/acerChromebook.jpg", 1);
INSERT INTO images VALUES (19, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576267481/acerChromebook1_xguavg.png", 0);
INSERT INTO images VALUES (20, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/lenovoThinkpadL590.jpg", 1);
INSERT INTO images VALUES (20, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576267712/lenovoThinkpadL5902_e3u5gg.png", 0);
INSERT INTO images VALUES (20, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576267713/lenovoThinkpadL5901_tfzlvw.png", 0);
INSERT INTO images VALUES (21, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/aupingRoyal.jpg", 1);
INSERT INTO images VALUES (21, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576268037/aupingRoyal2_gjmnmv.png", 0);
INSERT INTO images VALUES (21, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576268037/aupingRoyal1_ukvxl1.png", 0);
INSERT INTO images VALUES (22, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/vikingBirka.jpg", 1);
INSERT INTO images VALUES (22, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576268173/vikingBirka1_aa5y9x.png", 0);
INSERT INTO images VALUES (23, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/jensenPrestige.jpg", 1);
INSERT INTO images VALUES (23, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576268327/jensenPrestige1_et0baa.png", 0);
INSERT INTO images VALUES (24, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331133/carpeDiemHarmano.jpg", 1);
INSERT INTO images VALUES (24, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576268922/carpeDiemHarmano1_ars64o.png", 0);
INSERT INTO images VALUES (25, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/tempurFusion.jpg", 1);
INSERT INTO images VALUES (25, "https://res.cloudinary.com/dmk5yii3m/image/upload/v1576268944/tempurFusion1_vzefv1.png", 0);

INSERT INTO tags VALUES(1, "Red");
INSERT INTO tags VALUES(2, "Green");
INSERT INTO tags VALUES(3, "Blue");
INSERT INTO tags VALUES(4, "Pink");
INSERT INTO tags VALUES(5, "Black");
INSERT INTO tags VALUES(6, "Tech");
INSERT INTO tags VALUES(7, "Food");
INSERT INTO tags VALUES(8, "Furniture");
INSERT INTO tags VALUES(9, "Wheels");
INSERT INTO tags VALUES(10, "Pedal");
INSERT INTO tags VALUES(11, "Lights");
INSERT INTO tags VALUES(12, "Electronic");
INSERT INTO tags VALUES(13, "Phone");
INSERT INTO tags VALUES(14, "Smartphone");
INSERT INTO tags VALUES(15, "Screen");
INSERT INTO tags VALUES(16, "Battery");
INSERT INTO tags VALUES(17, "Edible");
INSERT INTO tags VALUES(18, "Tasty");
INSERT INTO tags VALUES(19, "Deliciuos");
INSERT INTO tags VALUES(20, "Gourmet");
INSERT INTO tags VALUES(21, "Soft");
INSERT INTO tags VALUES(22, "Comfy");
INSERT INTO tags VALUES(23, "Comfortable");
INSERT INTO tags VALUES(24, "Sleeping");
INSERT INTO tags VALUES(25, "Zzzz");
INSERT INTO tags VALUES(26, "Night");
INSERT INTO tags VALUES(27, " Warm");
INSERT INTO tags VALUES(28, "Cozy");
INSERT INTO tags VALUES(29, "Mattress");

INSERT INTO product_tags VALUES(1,1);
INSERT INTO product_tags VALUES(2,2);
INSERT INTO product_tags VALUES(3,3);
INSERT INTO product_tags VALUES(4,4);
INSERT INTO product_tags VALUES(5,5);
INSERT INTO product_tags VALUES(9,1);
INSERT INTO product_tags VALUES(9,2);
INSERT INTO product_tags VALUES(9,3);
INSERT INTO product_tags VALUES(9,4);
INSERT INTO product_tags VALUES(9,5);
INSERT INTO product_tags VALUES(10,1);
INSERT INTO product_tags VALUES(10,2);
INSERT INTO product_tags VALUES(10,3);
INSERT INTO product_tags VALUES(10,4);
INSERT INTO product_tags VALUES(10,5);
INSERT INTO product_tags VALUES(11,1);
INSERT INTO product_tags VALUES(11,2);
INSERT INTO product_tags VALUES(11,3);
INSERT INTO product_tags VALUES(11,4);
INSERT INTO product_tags VALUES(11,5);
INSERT INTO product_tags VALUES(6,6);
INSERT INTO product_tags VALUES(6,7);
INSERT INTO product_tags VALUES(6,8);
INSERT INTO product_tags VALUES(6,9);
INSERT INTO product_tags VALUES(6,10);
INSERT INTO product_tags VALUES(12,6);
INSERT INTO product_tags VALUES(12,7);
INSERT INTO product_tags VALUES(12,8);
INSERT INTO product_tags VALUES(12,9);
INSERT INTO product_tags VALUES(12,10);
INSERT INTO product_tags VALUES(13,6);
INSERT INTO product_tags VALUES(13,7);
INSERT INTO product_tags VALUES(13,8);
INSERT INTO product_tags VALUES(13,9);
INSERT INTO product_tags VALUES(13,10);
INSERT INTO product_tags VALUES(14,6);
INSERT INTO product_tags VALUES(14,7);
INSERT INTO product_tags VALUES(14,8);
INSERT INTO product_tags VALUES(14,9);
INSERT INTO product_tags VALUES(14,10);
INSERT INTO product_tags VALUES(15,6);
INSERT INTO product_tags VALUES(15,7);
INSERT INTO product_tags VALUES(15,8);
INSERT INTO product_tags VALUES(15,9);
INSERT INTO product_tags VALUES(15,10);
INSERT INTO product_tags VALUES(16,6);
INSERT INTO product_tags VALUES(16,7);
INSERT INTO product_tags VALUES(16,8);
INSERT INTO product_tags VALUES(16,9);
INSERT INTO product_tags VALUES(16,10);
INSERT INTO product_tags VALUES(5,8);
INSERT INTO product_tags VALUES(3,9);
INSERT INTO product_tags VALUES(5,10);
INSERT INTO product_tags VALUES(7,11);
INSERT INTO product_tags VALUES(7,12);
INSERT INTO product_tags VALUES(7,13);
INSERT INTO product_tags VALUES(7,14);
INSERT INTO product_tags VALUES(7,15);
INSERT INTO product_tags VALUES(17,11);
INSERT INTO product_tags VALUES(17,12);
INSERT INTO product_tags VALUES(17,13);
INSERT INTO product_tags VALUES(17,14);
INSERT INTO product_tags VALUES(17,15);
INSERT INTO product_tags VALUES(18,11);
INSERT INTO product_tags VALUES(18,12);
INSERT INTO product_tags VALUES(18,13);
INSERT INTO product_tags VALUES(18,14);
INSERT INTO product_tags VALUES(18,15);
INSERT INTO product_tags VALUES(19,11);
INSERT INTO product_tags VALUES(19,12);
INSERT INTO product_tags VALUES(19,13);
INSERT INTO product_tags VALUES(19,14);
INSERT INTO product_tags VALUES(19,15);
INSERT INTO product_tags VALUES(20,11);
INSERT INTO product_tags VALUES(20,12);
INSERT INTO product_tags VALUES(20,13);
INSERT INTO product_tags VALUES(20,14);
INSERT INTO product_tags VALUES(20,15);
INSERT INTO product_tags VALUES(2,11);
INSERT INTO product_tags VALUES(2,12);
INSERT INTO product_tags VALUES(1,13);
INSERT INTO product_tags VALUES(1,14);
INSERT INTO product_tags VALUES(6,16);
INSERT INTO product_tags VALUES(6,17);
INSERT INTO product_tags VALUES(6,18);
INSERT INTO product_tags VALUES(6,19);
INSERT INTO product_tags VALUES(6,20);
INSERT INTO product_tags VALUES(12,16);
INSERT INTO product_tags VALUES(12,17);
INSERT INTO product_tags VALUES(12,18);
INSERT INTO product_tags VALUES(12,19);
INSERT INTO product_tags VALUES(12,20);
INSERT INTO product_tags VALUES(15,16);
INSERT INTO product_tags VALUES(15,17);
INSERT INTO product_tags VALUES(15,18);
INSERT INTO product_tags VALUES(15,19);
INSERT INTO product_tags VALUES(15,20);
INSERT INTO product_tags VALUES(16,16);
INSERT INTO product_tags VALUES(16,17);
INSERT INTO product_tags VALUES(16,18);
INSERT INTO product_tags VALUES(16,19);
INSERT INTO product_tags VALUES(16,20);
INSERT INTO product_tags VALUES(8,21);
INSERT INTO product_tags VALUES(8,22);
INSERT INTO product_tags VALUES(8,23);
INSERT INTO product_tags VALUES(8,24);
INSERT INTO product_tags VALUES(8,25);
INSERT INTO product_tags VALUES(21,21);
INSERT INTO product_tags VALUES(21,22);
INSERT INTO product_tags VALUES(21,23);
INSERT INTO product_tags VALUES(21,24);
INSERT INTO product_tags VALUES(21,25);
INSERT INTO product_tags VALUES(22,21);
INSERT INTO product_tags VALUES(22,22);
INSERT INTO product_tags VALUES(22,23);
INSERT INTO product_tags VALUES(22,24);
INSERT INTO product_tags VALUES(22,25);
INSERT INTO product_tags VALUES(23,21);
INSERT INTO product_tags VALUES(23,22);
INSERT INTO product_tags VALUES(23,23);
INSERT INTO product_tags VALUES(23,24);
INSERT INTO product_tags VALUES(23,25);
INSERT INTO product_tags VALUES(24,21);
INSERT INTO product_tags VALUES(24,22);
INSERT INTO product_tags VALUES(24,23);
INSERT INTO product_tags VALUES(24,24);
INSERT INTO product_tags VALUES(24,25);
INSERT INTO product_tags VALUES(25,21);
INSERT INTO product_tags VALUES(25,22);
INSERT INTO product_tags VALUES(25,23);
INSERT INTO product_tags VALUES(25,24);
INSERT INTO product_tags VALUES(25,25);
INSERT INTO product_tags VALUES(26,21);
INSERT INTO product_tags VALUES(26,22);
INSERT INTO product_tags VALUES(26,23);
INSERT INTO product_tags VALUES(26,24);
INSERT INTO product_tags VALUES(26,25);
INSERT INTO product_tags VALUES(27,21);
INSERT INTO product_tags VALUES(27,22);
INSERT INTO product_tags VALUES(27,23);
INSERT INTO product_tags VALUES(27,24);
INSERT INTO product_tags VALUES(27,25);
INSERT INTO product_tags VALUES(28,21);
INSERT INTO product_tags VALUES(28,22);
INSERT INTO product_tags VALUES(28,23);
INSERT INTO product_tags VALUES(28,24);
INSERT INTO product_tags VALUES(28,25);
INSERT INTO product_tags VALUES(29,21);
INSERT INTO product_tags VALUES(29,22);
INSERT INTO product_tags VALUES(29,23);
INSERT INTO product_tags VALUES(29,24);
INSERT INTO product_tags VALUES(29,25);