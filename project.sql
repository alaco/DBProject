/* Allie LaCompte 
   DB Project Winter 2018
   Create and populate Postgres restaurant DB */

CREATE SCHEMA IF NOT EXISTS project;
SET search_path TO project;

CREATE DOMAIN RATING_TYPE DECIMAL(2,1)
CONSTRAINT rating_type_check CHECK (VALUE>=1 AND VALUE<=5);

CREATE TABLE RATER
(
	USERID CHAR(4) PRIMARY KEY,
	EMAIL VARCHAR(254) NOT NULL,
	NAME VARCHAR(70) NOT NULL,
	JOIN_DATE DATE DEFAULT CURRENT_DATE,
	TYPE VARCHAR(11) NOT NULL,
	CONSTRAINT type_check CHECK (TYPE in ('blog', 'online', 'food critic')),
	REPUTATION RATING_TYPE DEFAULT 1
);


CREATE TABLE RESTAURANT
(
	RESTAURANTID CHAR(4) PRIMARY KEY,
	NAME VARCHAR(40) NOT NULL,
	TYPE VARCHAR(20) NOT NULL,
	URL VARCHAR(100)
);


CREATE TABLE  RATING
(
	USERID CHAR(4) REFERENCES RATER
		ON DELETE CASCADE ON UPDATE CASCADE,
	DATE_TIME TIMESTAMP NOT NULL,
	PRICE RATING_TYPE NOT NULL,
	FOOD RATING_TYPE NOT NULL,
	MOOD RATING_TYPE NOT NULL,
	STAFF RATING_TYPE NOT NULL,
	COMMENTS TEXT,
	RESTAURANTID CHAR(4) REFERENCES RESTAURANT
		ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (USERID, DATE_TIME)
);


CREATE TABLE LOCATION
(
	LOCATIONID CHAR(4) PRIMARY KEY,
	FIRST_OPEN_DATE DATE,
	MANAGER_NAME VARCHAR(70),
	PHONE_NUMBER VARCHAR(15) NOT NULL,
	STREET_ADDRESS VARCHAR(75) NOT NULL,
	HOUR_OPEN TIME NOT NULL,
	HOUR_CLOSE TIME NOT NULL,
	RESTAURANTID CHAR(4) REFERENCES RESTAURANT
		ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE MENUITEM
(
	ITEMID CHAR(4) PRIMARY KEY,
	NAME VARCHAR(100) NOT NULL,
	TYPE VARCHAR(8) NOT NULL,
	CONSTRAINT type_check CHECK (TYPE in ('food', 'beverage')),
	CATEGORY VARCHAR(7),
	CONSTRAINT category_check CHECK (CATEGORY in ('starter', 'main', 'dessert')),
	DESCRIPTION VARCHAR(300),
	PRICE DECIMAL(6,2) NOT NULL,
	RESTAURANTID CHAR(4) REFERENCES RESTAURANT
		ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE RATINGITEM
(
	USERID CHAR(4) REFERENCES RATER
		ON DELETE CASCADE ON UPDATE CASCADE,
	DATE_TIME TIMESTAMP,
	ITEMID CHAR(4) REFERENCES MENUITEM
		ON DELETE CASCADE ON UPDATE CASCADE,
	RATING RATING_TYPE,
	COMMENT TEXT,
	PRIMARY KEY (USERID, DATE_TIME, ITEMID)
);




INSERT INTO RESTAURANT
VALUES
('1110', 'Taj Indian Cuisine', 'Indian', 'https://tajindiancuisine.com/'),
('1111', 'Pure Kitchen', 'Vegetarian', 'https://www.purekitchenottawa.com/'),
('1112', 'The Manx Pub', 'Pub', 'http://manxpub.com/'),
('1113', 'The Lieutenant''s Pump', 'Pub', 'http://www.lieutenantspump.ca/'),
('1114', 'Sushi 88', 'Japanese', 'http://www.sushi88.ca/'),
('1115', 'Coriander Thai', 'Thai', 'http://www.corianderthaiottawa.com/'),
('1116', 'Season''s Pizza', 'Pizza', 'http://seasonspizzaottawa.ca/'),
('1117', 'Saigon Boy Noodle House', 'Vietnamese', NULL),
('1118', 'The Royal Oak', 'Pub', 'http://royaloakpubs.com/'),
('1119', 'Sushifresh', 'Japanese', 'http://www.sushifresh.ca/'),
('1120', 'Lemongrass Thai Food', 'Thai', 'http://lemongrassthaiottawa.ca/'),
('1121', 'Clocktower Brew Pub', 'Pub', 'http://clocktower.ca/');


INSERT INTO LOCATION
VALUES
('3333', '17-Feb-1987', 'Indu Bakshi', '6137266955', '3009 Carling Ave, Ottawa, ON K2B 7Y6', '4:00pm', '9:30pm', '1110'),
('3334', NULL, NULL, '6132332828', '282 Kent St, Ottawa, ON K2P 2A4', '11:30am', '9:00pm', '1115'),
('3335', NULL, NULL, '6137895291', '55 York St, Ottawa, ON K1N 9B7', '11:30am', '8:30pm', '1119'),
('3336', '15-Jan-2016', NULL, '6132337873', '340 Elgin Street, Ottawa, ON K2P 1M5', '8:00am', '10:00pm', '1111'),
('3337', '01-Mar-2015', 'Caitlin Doan', '6136805500', '357 Richmond Road, Ottawa, ON K2A 0E7', '8:00am', '10:00pm', '1111'),
('3338', '20-Aug-1988', NULL, '6132378080', '725 Somerset St W, Ottawa, ON K1R 6P7', '11:00am', '3:00am', '1116'),
('3339', NULL, NULL, '6137244561', '200 Elgin St, Ottawa, ON K2P 1L5', '11:00am', '1:00am', '1121'),
('3340', NULL, NULL, '6136805983', '418 Richmond Rd, Ottawa, ON K2A 0G2', '11:30am', '12:00am', '1121'),
('3341', NULL, NULL, '6132418783', '89 Clarence St, Ottawa, ON K1N 5P5', '11:30am', '1:00am', '1121'),
('3342', '19-Jun-2003', NULL, '6132333288', '690 Somerset St W, Ottawa, ON K1R 6P4', '4:30pm', '10:00pm', '1114'),
('3343', '04-Jul-1993', NULL, '6132312070', ' 370 Elgin St, Ottawa, ON K2P 1N1', '11:30am', '1:00am', '1112'),
('3344', NULL, NULL, '6132308080', '648 Somerset Street West, Ottawa, ON K1R 5K4', '11:00am', '9:00pm', '1117'),
('3345', '21-Nov-1984', 'John Couse', '6132382949', '361 Elgin St, Ottawa, ON K2P 1M9', '11:30am', '2:00am', '1113'),
('3346', NULL, NULL, '6134228269', '180 Kent St, Ottawa, ON K1P 0B6', '11:00am', '2:00am', '1118'),
('3347', NULL, NULL, '6137286661', '1217 Wellington St W, Ottawa, ON K1Y 2Z9', '11:00am', '2:00am', '1118'),
('3348', NULL, NULL, '6132321057', '188 Bank St, Ottawa, ON K2P 1W8', '11:00am', '2:00am', '1118'),
('3349', NULL, NULL, '6132360190', '318 Bank St, Ottawa, ON K2P 1X7', '11:00am', '2:00am', '1118'),
('3350', '13-Sep-2001', NULL, '6137293282', '1055 Somerset St W, Ottawa, ON K1Y 3C4', '11:30am', '10:00pm', '1120');


INSERT INTO RATER
VALUES
('6666', 'alaco005@uottawa.ca', 'alaco005', '17-Oct-2017', 'online', 4.0),
('6667', 'linh@uottawa.ca', 'linh', '27-Jan-2018', 'food critic', 4.0),
('6668', 'li@uottawa.ca', 'li', '14-Oct-2017', 'food critic', 5.0),
('6669', 'bob@gmail.com', 'bobby123', '27-Apr-2016', 'blog', 2.0),
('6670', 'nora90@gmail.com', 'nora', '4-Oct-2016', 'blog', 3.5),
('6671', 'eric85@gmail.com', 'ericT', '11-May-2017', 'food critic', 4.5),
('6672', 'emily088@hotmail.com', 'em088', '17-Dec-2017', 'online', 3.0),
('6673', 'jessica82@gmail.com', 'jess_82', '31-Jul-2017', 'food critic', 2.5),
('6674', 'sarah9839@hotmail.com', 'sarah', '31-Mar-2015', 'food critic', 3.5),
('6675', 'megan324970@gmail.com', 'megan32', '8-Nov-2015', 'online', 2.0),
('6676', 'john@gmail.com', 'john', '21-Feb-2016', 'blog', 2.5),
('6677', 'chris356@hotmail.com', 'chris3', '18-Dec-2016', 'online', 4.0),
('6678', 'tom@hotmail.com', 'tommy', '3-Jul-2016', 'food critic', 3.0),
('6679', 'tim@gmail.com', 'tim', '2016-03-21', 'food critic', 2.0),
('6680', 'lauren92@gmail.com', 'lauren', '24-May-2014', 'online', 4.5);


INSERT INTO MENUITEM
VALUES
(7040, 'Radical Cauliflower Wings', 'food', 'starter', 'korean bbq, scallions, cilantro, sesame seeds or buffalo hot sauce, scallions, ranch dressing', 15.00, '1111'),
(7041, 'Carefree Tacos', 'food', 'starter', 'chef''s feature filling, fresh accoutrements, corn tortilla', 9.00, '1111'),
(7042, 'Phenomenal', 'food', 'main', 'kale, red cabbage, red peppers, sweet potato, peanuts, peanut sauce, tempeh, choice of cheese', 14.00, '1111'),
(7043, 'Royale with Cheese', 'food', 'main', 'mushroom walnut patty, pickles, onion, lettuce, russian dressing, cheddar or cashew cheese', 17.00, '1111'),
(7044, 'Fantastic', 'food', 'main', 'ginger lime tofu, broccoli, red peppers, kale, red cabbage, scallions, cilantro, roasted peanuts, peanut sauce, rice noodles', 14.00, '1111'),
(7045, 'Electrify', 'beverage', NULL, 'beet, pineapple, cucumber, apple, turmeric, ginger, lemon', 10.00, '1111'),
(7046, 'Pink Moon', 'beverage', NULL, 'gin, grapefruit, pineapple, lime', 8.00, '1111'),
(7047, 'Shrimp Summer Roll', 'food', 'starter', NULL, 5.00, '1117'),
(7048, 'Beef Rare Rice Noodle Soup', 'food', 'main', NULL, 8.95, '1117'),
(7049, 'Pork Spring Rolls on Rice Vermicelli', 'food', 'main', NULL, 11.25, '1117'),
(7050, 'Lychee with Ice', 'beverage', NULL, NULL, 5.00, '1117'),
(7051, 'Deep Fried Banana', 'food', 'dessert', 'Drizzled with honey and topped with peanuts', 3.75, '1117'),
(7052, 'Poa Pia Goong', 'food', 'starter', '2 Spring rolls stuffed with shrimp.', 3.25, '1120'),
(7053, 'TomYum Goong', 'food', 'starter', 'Hot and sour lemongrass soup with shrimp and mixed vegetables.', 3.75, '1120'),
(7054, 'Panang', 'food', 'main', 'Red peanut curry with chicken/beef/vegetables or tofu.', 11.75, '1120'),
(7055, 'Gaeng Keow Wan', 'food', 'main', 'Green curry with chicken/beef/vegetables or tofu.', 12.75, '1120'),
(7056, 'Mango/Coconut Juice', 'beverage', NULL, NULL, 2.50, '1120'),
(7057, 'Edamame', 'food', 'starter', 'Baby soy beans', 4.95, '1114'),
(7058, 'Miso Soup', 'food', 'starter', 'Tofu, seaweed, and green onion', 2.95, '1114'),
(7059, 'Garden salad', 'food', 'starter', 'Seasonal greens with out Japanese ginger dressing', 2.95, '1114'),
(7060, 'Inari Nigiri', 'food', 'main', NULL, 3.95, '1114'),
(7061, 'Salmon Avocado Roll', 'food', 'main', NULL, 6.95, '1114'),
(7062, 'Rainbow Roll', 'food', 'main', 'Crab, avocado, cucumber wrapped in assorted sashimi', 13.50, '1114'),
(7063, 'Vegetable Samosas', 'food', 'starter', 'Pastries made of flour and stuffed with spiced potatoes and green peas.', 5.95, '1110'),
(7064, 'Paneer Pakoras', 'food', 'starter', 'Indian cheese cubes fried with gram flour and spices with tamarind & mint chutney', 12.95, '1110'),
(7065, 'Alu Gobi Masala', 'food', 'main', 'Cauliflower, potatoes and onions cooked in Indian spices.', 16.95, '1110'),
(7066, 'Tarka Dhal', 'food', 'main', 'Yellow lentils cooked with onion, tomatoes and ginger with a mix of Taj star spices.', 15.95, '1110'),
(7067, 'Gulab Jamun', 'food', 'dessert', 'Popular Indian dessert made with milk, soaked in honey syrup and sprinkled with pistachios/almonds.', 5.95, '1110'),
(7068, 'Nachos', 'food', 'starter', 'Corn tortilla chips / cheese / salsa / tomato / green onion / jalapeños. Served with sour cream.', 16.00, '1118'),
(7069, 'Pita Plate', 'food', 'starter', 'Hummus / Tzatziki / vegetables / Kalamata olives / Naan bread.', 14.00, '1118'),
(7070, 'Fish N Chips', 'food', 'main', 'A tasty haddock fillet in our crispy batter. Served with coleslaw, tartar sauce and pub fries.', 15.00, '1118'),
(7071, 'Windsor Chicken Sandwich', 'food', 'main', 'Grilled chicken / guacamole / Applewood smoked bacon / tomato / Swiss cheese / pesto mayonnaise / Ciabatta.', 17.00, '1118'),
(7072, 'Irish Coffee', 'beverage', NULL, 'Coffee with Irish Whiskey and Irish Mist, topped with whipped cream.', 8.19, '1118'),
(7073, 'Quesadilla', 'food', 'starter', 'with smoked onion, roasted corn, black bean & cheddar. Served with sour cream & tomatillo salsa', 10.00, '1112'),
(7074, 'Lebanese Spiced Chickpea Burger', 'food', 'main', 'w/ a pickled turnip & wild cucumber salsa, lemon-garlic spread and baby spinach', 14.00, '1112'),
(7075, 'Salmon Burger', 'food', 'main', 'with hoisin bbq glaze, Vietnamese pickled vegetables and lime-chili aioli. Served with a baby spinach salad tossed in sesame-soy dressing.', 16.00, '1112'),
(7076, 'Beau''s Kissmeyer', 'beverage', NULL, 'Nordic pale ale.', 7.00, '1112'),
(7077, 'Wakame', 'food', 'starter', 'Seaweed salad', 4.00, '1119'),
(7078, 'Agedashi Tofu', 'food', 'starter', 'Fried Tofu in Sweet soy sauce, Green onion, Katsuobushi (Fish flakes), Lemon', 6.00, '1119'),
(7079, 'Gyoza (Dumpling)', 'food', 'starter', 'Deep fried 6pcs (choice of Beef or Veggie)', 5.00, '1119'),
(7080, 'Unagi Maki', 'food', 'main', 'Grilled Eel', 6.50, '1119'),
(7081, 'Sake Sahimi', 'food', 'main', 'Salmon', 4.25, '1119'),
(7082, 'Sunrise Roll', 'food', 'main', 'Sundried tomato, Avocado, Cream cheese, Cucumber', 7.50, '1119'),
(7083, 'Fried Taiyaki', 'food', 'dessert', 'Fried Japanese Cake filled with Red bean paste Cinnamon, Maple Syrup', 4.50, '1119'),
(7084, 'San Pellegrino', 'beverage', NULL, 'Lemon Can/ Orange Can/ Blood Orange Can', 2.25, '1119'),
(7085, 'Poa Pia Sod', 'food', 'starter', 'Fresh rice paper wrap with shrimp, vegetables, and vermicelli, served with peanut sauce.', 9.00, '1115'),
(7086, 'Thai Poa Pia Pak', 'food', 'starter', 'Two deep-fried spring rolls stuffed with vegetables.', 6.00, '1115'),
(7087, 'Tom Kha Goong', 'food', 'starter', 'Hot and sour coconut milk soup with plump shrimp and fresh mushrooms.', 6.00, '1115'),
(7088, 'Gaeng Keow Wan', 'food', 'main', 'Green curry with your choice of chicken, beef, or tofu and vegetables.', 15.00, '1115'),
(7089, 'Chu Chee Ta-Lae/Goong', 'food', 'main', 'Red curry with lime leaves and your choice of mixed seafood or shrimp.', 16.00, '1115'),
(7090, 'Thai Dessert', 'food', 'dessert', 'Tapioca with coconut milk.', 4.00, '1115'),
(7091, 'Canned Pop', 'beverage', NULL, 'Choose a delicious pop!', 2.50, '1115'),
(7092, 'Focaccia Bruschetta', 'food', 'starter', 'Garlic butter, tomato olive relish, parmesan cheese, balsamic drizzle', 7.25, '1113'),
(7093, 'Pumped-Up Nachos', 'food', 'starter', 'Jalapeño, black olives, onion, tomato, peppers, salsa, sour cream, guacamole', 16.75, '1113'),
(7094, 'Roasted Ginger Salmon', 'food', 'main', 'with ginger soy butter sauce, wild arugula, and diced peppers', 16.95, '1113'),
(7095, 'Trish''s Very Veggies Wrap', 'food', 'main', 'Avocado, cheese, lettuce, cucumber, tomato with cilantro mayonnaise', 11.95, '1113'),
(7096, 'Chocolate Pudding Cake', 'food', 'dessert', 'with French vanilla ice cream', 6.50, '1113'),
(7097, 'Sticky Toffee Pudding', 'food', 'dessert', 'with French vanilla ice cream', 6.50, '1113'),
(7098, 'Beyond the Pale Aroma Therapy', 'beverage', NULL, NULL, 8.50, '1113'),
(7099, 'Tooth & Nail Tenacity', 'beverage', NULL, NULL, 8.50, '1113'),
(7100, 'Root Beer', 'beverage', NULL, NULL, 1.50, '1116'),
(7101, 'Potato Skin', 'food', 'starter', 'With mushrooms, bacon and cheese.', 13.45, '1116'),
(7102, 'French Fries', 'food', 'starter', NULL, 5.95, '1116'),
(7103, 'Special Garlic Bread', 'food', 'starter', 'With cheese and bacon.', 5.25, '1116'),
(7104, 'Mexicam Pizza', 'food', 'main', 'Mushrooms, green peppers, onions, olives, hot banana peppers and tomatoes.', 19.65, '1116'),
(7105, 'Canadian Pizza', 'food', 'main', 'Pepperoni, mushrooms and bacon.', 18.95, '1116'),
(7106, 'Pub Chips & Dip', 'food', 'starter', 'Thick cut potato chips dusted with your choice of Sriracha, maple bacon, mango habanero or salt & pepper. Served with our house onion & sour cream dip', 9.00, '1121'),
(7107, 'Kimchi Fries', 'food', 'starter', 'Our in house fries topped with Korean pulled pork, kimchi, sambal mayo, sriracha, green onion, cilantro & sesame seeds', 12.00, '1121'),
(7108, 'Sundried Pesto Pasta', 'food', 'main', 'Linguine with goat cheese, sundried tomato pesto, roasted red peppers, roasted mushrooms, grape tomatoes, arugula & fresh herbs. Served with garlic bread', 16.50, '1121'),
(7109, 'Falafel Wrap', 'food', 'main', 'Falafel, marinated tomatoes, pickles, tahini mayo & shredded lettuce in a warm naan wrap', 14.00, '1121'),
(7110, 'Juice', 'beverage', NULL, 'Orange, pineapple, grapefruit, cranberry, apple, tomato, or clamato', 3.00, '1121'),
(7111, 'Spinach & Artichoke Dip', 'food', 'starter', 'Warm spinach, artichoke & cheese dip served with crispy wonton chips and corn tortilla chips', 15.50, '1121');


INSERT INTO RATING
VALUES
('6666', '2017-12-30 07:20', 2.5, 3.5, 4.0, 3.5, 'Pretty good food. Pricey.', '1110'),
('6666', '2018-03-22 05:08', 4.0, 5.0, 5.0, 4.5, 'The best!', '1111'),
('6666', '2018-03-28 10:38', 4.0, 5.0, 4.5, 4.0, 'Delicious!', '1112'),
('6666', '2017-11-22 19:45', 3.0, 2.0, 4.0, 3.5, 'Good drink selection, subpar food.', '1113'),
('6666', '2018-02-13 12:59', 5.0, 5.0, 4.5, 4.5, 'Best sushi in Ottawa.', '1114'),
('6666', '2017-12-13 11:20', 3.0, 4.5, 3.0, 3.5, 'Tasty.', '1115'),
('6666', '2017-12-23 08:31', 4.0, 3.5, 1.0, 3.5, 'Good deal.', '1116'),
('6666', '2017-12-08 07:23', 3.0, 4.0, 3.5, 4.0, NULL, '1117'),
('6666', '2017-10-18 05:25', 2.0, 2.0, 3.0, 3.0, 'Overpriced. Poor drink selection. Mediocre food at best.', '1118'),
('6666', '2018-02-04 17:52', 2.0, 2.0, 2.0, 1.0, 'Still awful', '1118'),
('6666', '2018-01-17 10:31', 3.5, 4.5, 3.5, 3.5, 'Very fresh.', '1119'),
('6666', '2017-10-18 06:14', 4.5, 3.5, 1.0, 3.0, 'Inexpensive!', '1120'),
('6666', '2017-12-25 01:08', 3.0, 3.5, 3.5, 3.5, 'Better than average pub food.', '1121'),

('6667', '2018-02-25 12:12', 4.5, 3.5, 4.0, 4.5, 'Excellent!', '1111'),
('6667', '2018-02-12 13:33', 5.0, 3.0, 4.0, 3.0, 'Good.', '1112'),
('6667', '2018-03-17 11:57', 3.0, 1.0, 4.5, 3.0, NULL, '1114'),

('6668', '2018-02-24 00:25', 4.5, 4.0, 4.5, 2.5, 'Great!', '1111'),
('6668', '2018-01-20 01:39', 4.0, 3.5, 3.0, 3.0, NULL, '1112'),
('6668', '2018-01-06 07:13', 1.0, 3.0, 2.5, 5.0, 'Not good', '1121'),
('6668', '2018-03-15 17:44', 5.0, 2.0, 1.5, 4.0, NULL, '1119'),

('6669', '2017-07-06 11:21', 2.5, 2.0, 1.0, 5.0, 'Food was terrible but the staff was great.', '1112'),
('6669', '2017-02-01 11:59', 5.0, 4.0, 3.5, 3.0, 'Good, inexpensive food. Staff could have been friendlier.', '1113'),
('6669', '2016-09-17 08:51', 2.5, 4.5, 3.0, 2.0, 'Good food but expensive.', '1118'),
('6669', '2017-05-03 16:29', 1.5, 3.5, 4.0, 5.0, 'Expensive and mediocore.', '1121'),

('6670', '2018-02-27 12:15', 4.0, 4.0, 4.0, 4.0, 'nom nom nom nom nom', '1110'),
('6670', '2017-10-02 04:34', 3.5, 5.0, 5.0, 4.5, 'My favourite food in the city! Can be a little expensive and there''s one super weird server but overall an amazing restaurant.', '1111'),
('6670', '2017-10-22 04:52', 4.0, 5.0, 5.0, 5.0, 'amazing food, cozy atmosphere, great beer. I probably only think it''s expensive because I end up drinking a lot of beer.', '1112'),
('6670', '2017-02-01 23:46', 4.0, 2.0, 5.0, 3.5, 'food sucks but the beer and atmosphere are fab. Amazing patio in the summer. Servers are kind of hit and miss. I sometimes have a crush on one of them.', '1113'),
('6670', '2017-04-15 07:41', 4.0, 5.0, 5.0, 5.0, 'sushi 88 rocks. Amazing fresh food, awesome combo options, cozy restaurant, nice people. Sake.', '1114'),
('6670', '2017-09-04 17:12', 1.0, 1.0, 1.0, 1.0, 'everything sucks', '1115'),
('6670', '2016-12-06 01:20', 1.0, 2.0, 3.0, 4.0, 'ohh yeah baby', '1116'),
('6670', '2017-01-25 08:28', 4.0, 2.0, 2.0, 2.0, 'never been there so I assume its horrible', '1117'),
('6670', '2017-03-22 20:12', 1.0, 1.0, 1.0, 1.0, 'garbage', '1118'),
('6670', '2018-01-21 09:02', 5.0, 5.0, 5.0, 5.0, 'best lunch combos!', '1119'),
('6670', '2018-01-30 17:48', 4.0, 5.0, 5.0, 5.0, 'nom nom nom', '1120'),
('6670', '2017-12-30 20:04', 4.0, 4.0, 5.0, 5.0, 'I love those lettuce wraps!!!!!!!!!!!!!!!!!!!!!!!!!!!!!', '1121'),

('6671', '2017-09-16 16:27', 4.5, 4.0, 4.0, 4.0, NULL, '1110'),
('6671', '2017-12-23 18:19', 4.5, 5.0, 5.0, 5.0, 'Great staff/environment, and the food was excellent!', '1111'),
('6671', '2017-09-25 00:24', 4.0, 5.0, 5.0, 5.0, 'This is my favourite brunch spot. It''s a bit pricey, but the food is well worth it.', '1112'),
('6671', '2018-02-21 17:09', 4.0, 4.0, 4.5, 4.0, 'Great spot for drinks, and the food is average pub food.', '1113'),
('6671', '2017-06-14 13:10', 4.5, 5.0, 4.5, 5.0, 'Best sushi in town!', '1114'),
('6671', '2017-07-13 02:21', 4.5, 4.0, 4.0, 4.0, 'The food is good and they are one of the cheapest for Thai food.', '1120'),
('6671', '2018-02-25 17:21', 4.0, 4.0, 3.5, 4.0, 'Good food and at a reasonable price.', '1115'),
('6671', '2017-07-24 16:56', 4.0, 4.5, 3.5, 4.0, 'They have a great 2 for 1 deal that comes with drinks and dips.', '1116'),
('6671', '2018-01-22 00:23', 4.5, 4.5, 4.0, 4.5, 'Great spot for inexpensive pho at a good price.', '1117'),
('6671', '2017-12-06 18:12', 3.0, 2.5, 3.0, 3.5, 'Only good as a last resort. They have a poor selection for beer, their food is way overpriced, and the quality of the food is not great.', '1118'),
('6671', '2018-03-03 14:49', 4.0, 4.0, 4.0, 4.0, NULL, '1119'),
('6671', '2017-07-07 14:07', 4.0, 4.0, 4.0, 4.0, 'They brew their own beer, so it''s the only beer available. It''s not bad, but it''s not great either.', '1121'),

('6672', '2018-02-13 22:21', 1.0, 5.0, 5.0, 5.0, 'The best! But, expensive...', '1112'),
('6672', '2018-02-09 19:41', 4.5, 3.0, 2.0, 2.0, NULL, '1114'),
('6672', '2018-01-04 05:20', 3.0, 5.0, 4.5, 5.0, 'Great pizza!', '1116'),
('6672', '2018-03-03 11:07', 1.5, 5.0, 1.5, 3.5, 'Excellent sushi!!!', '1119'),

('6673', '2018-03-18 15:21', 1.5, 5.0, 5.0, 5.0, 'Great!', '1112'),
('6673', '2017-10-17 12:53', 1.0, 5.0, 2.5, 2.5, NULL, '1113'),
('6673', '2018-01-02 16:12', 3.5, 5.0, 4.0, 3.0, 'Freshest sushi', '1119'),

('6674', '2017-06-25 03:19', 4.0, 4.0, 4.0, 4.0, 'As advertised, the sushi is quiet fresh here.', '1119'),
('6674', '2015-12-16 03:07', 4.0, 5.0, 4.5, 5.0, 'This place has the best food out there. I''ve never had a better ceasar salad.', '1113'),
('6674', '2017-07-24 04:18', 5.0, 4.0, 4.5, 4.0, 'They brew the best beer in Canada, by far.', '1121'),

('6675', '2016-01-09 00:28', 4.5, 4.0, 4.0, 4.0, 'A bit pricey, but the Naan was great. That was all that I ate.', '1110'),
('6675', '2016-12-23 21:09', 2.0, 3.0, 3.5, 5.0, 'A friend recommded I go here. The food was really expensive, and there was no meat or Smirnoff Ice.', '1111'),
('6675', '2017-08-26 19:25', 4.0, 3.5, 4.0, 4.5, 'Food is ok, I mainly go for the green candies.', '1117'),
('6675', '2018-01-24 03:39', 4.0, 4.5, 5.0, 5.0, 'The food is not great here, but they have the best Coors Light on draft.', '1118'),

('6676', '2017-12-05 10:00', 5.0, 5.0, 5.0, 5.0, 'This place has the best pizza in the city', '1116'),
('6676', '2017-04-03 08:03', 4.0, 4.0, 3.5, 4.0, 'Food was good, could have used a little more corriander.', '1115'),
('6676', '2016-09-27 23:02', 4.5, 4.0, 4.0, 4.0, 'The food is good and they are one of the cheapest for Thai food.', '1120'),
('6676', '2016-03-25 20:53', 4.5, 5.0, 4.5, 5.0, 'Good sushi, best waitress!', '1114'),
('6676', '2018-01-29 08:52', 4.0, 5.0, 5.0, 5.0, 'Food was ok. This place has no Manx.', '1112'),

('6677', '2018-02-17 21:24', 1.0, 1.0, 4.5, 3.5, NULL, '1110'),
('6677', '2017-09-25 02:26', 1.5, 5.0, 1.5, 2.5, 'Very expensive but delicious food.', '1112'),
('6677', '2018-02-16 20:19', 2.5, 2.0, 4.5, 4.0, 'So-so', '1117'),
('6677', '2017-12-27 04:59', 2.0, 1.5, 3.0, 2.5, 'Won''t go again.', '1120'),

('6678', '2016-07-18 12:17', 3.0, 3.0, 2.5, 3.0, 'Too spicy', '1110'),
('6678', '2017-04-26 03:56', 3.0, 2.0, 3.0, 4.0, 'Not my cup of tea', '1111'),
('6678', '2017-03-26 09:00', 2.5, 3.5, 3.0, 3.0, 'Too expensive for pub food', '1112'),
('6678', '2017-07-27 03:37', 4.0, 4.0, 3.5, 4.0, 'Great food, large patio, too many craft beer.', '1113'),
('6678', '2016-11-09 14:51', 2.5, 3.5, 4.0, 4.0, 'No all you can eat menu', '1114'),
('6678', '2017-07-06 23:56', 3.0, 3.0, 3.0, 3.0, 'Pretty good', '1115'),
('6678', '2018-03-24 02:07', 5.0, 5.0, 4.0, 4.0, 'The best garlic bread', '1116'),
('6678', '2016-08-23 07:20', 3.0, 4.0, 3.0, 3.0, 'Wish they had a larger size of pho', '1117'),
('6678', '2017-02-05 07:10', 4.0, 5.0, 5.0, 4.0, 'My favourite place in town, great food, great beer selection', '1118'),
('6678', '2017-06-27 04:20', 2.0, 3.0, 4.0, 3.0, 'Won''t go again', '1119'),
('6678', '2018-03-25 13:33', 5.0, 4.0, 3.0, 3.0, 'Good value for your money', '1120'),
('6678', '2016-12-10 13:52', 4.5, 4.0, 4.0, 4.0, 'Almost as good as the Oak', '1121'),

('6679', '2017-06-17 01:20', 4.0, 5.0, 5.0, 4.5, 'Love this place!!!', '1111'),
('6679', '2017-10-07 20:50', 4.0, 5.0, 4.5, 4.5, 'Best brunch!', '1112'),

('6680', '2016-08-23 04:57', 3.0, 4.0, 3.0, 3.5, 'Great naan bread', '1110'),
('6680', '2018-01-02 14:28', 4.0, 5.0, 4.5, 4.0, 'Delicious cauliflower wings, great food overall', '1111'),
('6680', '2015-02-13 11:09', 4.5, 4.5, 4.0, 4.0, 'One server is particularly sassy, cute eclectic coffee mugs, great veggie breakfast burrito', '1112'),
('6680', '2017-12-17 17:09', 4.0, 4.0, 4.0, 3.0, 'Great happy hour - triples for 10 dollars, tasty nachos and zucchini sticks', '1113'),
('6680', '2014-10-11 06:53', 3.5, 4.5, 3.0, 4.5, 'Great veggie-friendly options', '1114'),
('6680', '2014-09-22 16:54', 3.5, 3.5, 3.0, 2.5, 'Decent pad thai', '1115'),
('6680', '2016-07-07 11:27', 3.0, 3.5, 2.0, 3.5, 'Staff is friendly but slow', '1116'),
('6680', '2014-07-14 05:11', 3.0, 3.5, 3.0, 2.5, 'Good pho', '1117'),
('6680', '2018-01-27 13:26', 2.0, 3.0, 3.5, 3.0, 'An OK place if everywhere else is full', '1118'),
('6680', '2016-01-12 20:20', 1.5, 3.5, 2.0, 3.0, 'Too expensive for what you get', '1119'),
('6680', '2017-04-19 23:36', 5.0, 2.0, 2.0, 2.5, 'Inexpensive but not great, cleaniless is questionable', '1120'),
('6680', '2015-04-10 21:31', 3.5, 3.0, 4.0, 3.5, 'Good beers', '1121');