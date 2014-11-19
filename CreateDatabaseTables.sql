/*
*	Vergeet niet de constants in de DbManager class aan te passen.
*	Sla daar je MySQL username en password op, net als je database naam (default: LMS_DB)
*/

CREATE TABLE users(
	user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	username VARCHAR(15) NOT NULL UNIQUE,
	password VARCHAR(25) NOT NULL,
	user_role VARCHAR(20) NOT NULL
);

CREATE TABLE luggage(
	luggage_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	color VARCHAR(25),
	pattern VARCHAR(20),
	brand VARCHAR(20) NOT NULL,
	passenger_id VARCHAR(11) NOT NULL,
	weight VARCHAR(11),
	size VARCHAR(8)
);

CREATE TABLE logs(
	log_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(15) NOT NULL,
	user_role VARCHAR(20) NOT NULL,
	date_time VARCHAR(25) NOT NULL,
	log_message VARCHAR(100) NOT NULL
);

CREATE TABLE customers(
	customer_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	address VARCHAR(75) NOT NULL,
	email_address VARCHAR(50),
	phone_number VARCHAR(25) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8