CREATE TABLE Product (
	prod_id CHAR(10),
	pname VARCHAR(128),
	price DECIMAL
);
Alter table Product add constraint pk_product primary key (prod_id);
Alter table Product add constraint ck_product_price check (price > 0);


INSERT INTO Product (prod_id, pname, price) Values 
('p1', 'tape', 2.5),
('p2', 'tv', 250),
('p3', 'vcr', 80);

CREATE TABLE Depot (
	dep_id CHAR(10),
	addr VARCHAR(128),
	volume INTEGER
);

Alter table Depot add constraint pk_depot primary key (dep_id);


INSERT INTO Depot (dep_id, addr, volume) Values 
('d1', 'New Yrok', 9000),
('d2', 'Syracuse', 6000),
('d4', 'New Yrok', 2000);

CREATE TABLE Stock (
	prod_id CHAR(10),
	dep_id VARCHAR(10),
	quantity INTEGER
);


Alter table Stock add constraint pk_stock primary key (prod_id, dep_id);
Alter table Stock add constraint fk_product foreign key (prod_id) REFERENCES Product (prod_id) ON UPDATE CASCADE;
Alter table Stock add constraint fk_depot foreign key (dep_id) REFERENCES Depot (dep_id);


INSERT INTO Stock (prod_id, dep_id, quantity) Values 
('p1', 'd1', 1000),
('p1', 'd2', -100),
('p1', 'd4', 1200),
('p3', 'd1', 3000),
('p3', 'd4', 2000),
('p2', 'd4', 1500),
('p2', 'd1', -400),
('p2', 'd2', 2000);
