-- Catalogs data
INSERT INTO catalogs(name, description) VALUES('CPU', 'description CPU');
INSERT INTO catalogs(name, description) VALUES('MAIN', 'description Main Boards');
INSERT INTO catalogs(name, description) VALUES('RAM', 'description RAM');
INSERT INTO catalogs(name, description) VALUES('HDD', 'description HDD');
INSERT INTO catalogs(name, description) VALUES('SSD', 'description SSD');
INSERT INTO catalogs(name, description) VALUES('GPU', 'description GPU');
INSERT INTO catalogs(name, description) VALUES('PSU', 'description PSU');

-- Branches data
INSERT INTO branches(name, description) VALUES('Intel', 'Intel');
INSERT INTO branches(name, description) VALUES('AMD', 'AMD');
INSERT INTO branches(name, description) VALUES('Corsair', 'Corsair');
INSERT INTO branches(name, description) VALUES('Samsung', 'Samsung');
INSERT INTO branches(name, description) VALUES('Cooler Master', 'Cooler Master');
INSERT INTO branches(name, description) VALUES('Asus', 'Asus');

-- Products data
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(1, 'Intel CPU 1', 'Intel CPU 1', 10.10, 'None', 'CPU', 'Intel');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(2, 'Intel CPU 2', 'Intel CPU 2', 20.20, 'None', 'CPU', 'Intel');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(3, 'AMD CPU 1', 'Intel CPU 1', 30.30, 'None', 'CPU', 'AMD');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(4, 'AMD CPU 2', 'Intel CPU 2', 40.40, 'None', 'CPU', 'AMD');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(5, 'Corsair RAM 1', 'Corsair RAM 1', 50.50, 'None', 'RAM', 'Corsair');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(6, 'Corsair RAM 2', 'Corsair RAM 2', 60.60, 'None', 'RAM', 'Corsair');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(7, 'Corsair HDD 1', 'Corsair HDD 1', 70.70, 'None', 'HDD', 'Corsair');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(8, 'Corsair HDD 2', 'Corsair HDD 2', 80.80, 'None', 'HDD', 'Corsair');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(9, 'Asus SSD 1', 'Asus SSD 1', 90.90, 'None', 'SSD', 'Asus');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(10, 'Asus SSD 2', 'Asus SSD 2', 100.100, 'None', 'SSD', 'Asus');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(11, 'Asus GPU 1', 'Asus GPU 1', 110.110, 'None', 'GPU', 'Asus');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(12, 'Asus GPU 2', 'Asus GPU 2', 120.120, 'None', 'GPU', 'Asus');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(13, 'CM PSU 1', 'CM PSU 1', 130.130, 'None', 'PSU', 'Cooler Master');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(14, 'CM PSU 2', 'CM PSU 2', 140.140, 'None', 'PSU', 'Cooler Master');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(15, 'Asus Main 1', 'Asus Main 1', 150.150, 'None', 'MAIN', 'Asus');
INSERT INTO products(id, name, description, price, colour, catalog_id, branch_id) VALUES(16, 'Asus Main 2', 'Asus Main 2', 160.160, 'None', 'MAIN', 'Asus');


INSERT INTO users(username, password, first_name, last_name, phone_number, address) VALUES('user', 'password', 'First Name 1', 'Last Name 1', '123456', 'address');