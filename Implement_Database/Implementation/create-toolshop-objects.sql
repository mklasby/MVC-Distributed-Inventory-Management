DROP DATABASE IF EXISTS toolshop;
CREATE DATABASE toolshop; 
USE toolshop;


DROP TABLE IF EXISTS SUPPLIER;
CREATE TABLE SUPPLIER (
  SupplierID			int(15),
  Name					varchar(50),
  Type            		varchar(15),
  Address		        varchar(50),
  CName				    varchar(50),
  Phone					varchar(20),
  primary key (SupplierID)
);


DROP TABLE IF EXISTS INTERNATIONAL;
CREATE TABLE INTERNATIONAL (
  SupplierID			int(15),
  ImportTax				double(5,2),
  primary key (SupplierID),
  foreign key (SupplierID) references SUPPLIER(SupplierID)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);


DROP TABLE IF EXISTS TOOL;
CREATE TABLE TOOL (
  ToolID				int(15),
  Name         			varchar(50),
  Type      			varchar(15),
  Quantity     			int(5), 
  Price					double(5,2),
  SupplierID			int(15),
  primary key (ToolID),
  foreign key (SupplierID) references SUPPLIER(SupplierID)
        ON UPDATE CASCADE
);


DROP TABLE IF EXISTS ELECTRICAL;
CREATE TABLE ELECTRICAL (
  ToolID			    int(15), 
  PowerType	    		varchar(15),
  primary key (ToolID),
  foreign key (ToolID) references TOOL(ToolID)
  		ON DELETE CASCADE
        ON UPDATE CASCADE
);


DROP TABLE IF EXISTS ORDERS;
CREATE TABLE ORDERS (
  OrderID      			int(15),
  Date					DATE,
  primary key (OrderID)
);


DROP TABLE IF EXISTS ORDERLINE;
CREATE TABLE ORDERLINE (
  OrderID      			int(15),
  ToolID				int(15),
  SupplierID			int(15),
  Quantity				int(5),
  primary key (OrderID, ToolID),
  foreign key (OrderID) references ORDERS(OrderID),
  foreign key (ToolID) references TOOL(ToolID),
  foreign key (SupplierID) references SUPPLIER(SupplierID)
);


DROP TABLE IF EXISTS CLIENT;
CREATE TABLE CLIENT (
  ClientID     			int(15),
  LName					varchar(15),
  FName					varchar(15),
  Type					varchar(15),
  Phone					varchar(20),
  Address				varchar(50),
  PostalCode			varchar(10),
  primary key (ClientID)
);


DROP TABLE IF EXISTS PURCHASES;
CREATE TABLE PURCHASES (
  ClientID     			int(15),
  ToolID				int(15),
  Date					DATE,
  primary key (ClientID, ToolID, Date),
  foreign key (ClientID) references CLIENT(ClientID),
  foreign key (ToolID) references TOOL(ToolID)
);

INSERT INTO SUPPLIER VALUES 
	(8001,'Grommet Builders','Domestic','788 30th St, SE, Calgary','Fred','587-555-0183'),
    (8002,'Pong Works','Domestic','749 Dufferin Blvd, SE, Calgary','Bart','587-555-0119'),
    (8003,'Wiley Inc','Domestic','26 40th St, SE, Calgary','BillyBob','587-555-0182'),
    (8004,'Winork Manufacturing Inc','Domestic','156 51st Ave, SE, Calgary','Marty','587-555-0113'),
    (8005,'Grab Bag Inc','Domestic','138 42nd Ave, NE, Calgary','Monty','587-555-0137'),
    (8006,'Paddy Manufacturing','Domestic','311 McCall Way, NE, Calgary','Barney','587-555-0104'),
    (8007,'Smickles Industries','Domestic','966 Smed Lane, SE, Calgary','Lisa','587-555-0122'),
    (8008,'Thangs Inc','Domestic','879 37th Ave, NE, Calgary','Thelma','587-555-0105'),
    (8009,'Flip Works Inc','Domestic','472 Ogden Dale Rd, SE, Calgary','Rory','587-555-0148'),
    (8010,'Irkle Industries','Domestic','754 Sunridge Way, NE, Calgary','Irma','587-555-0181'),
    (8011,'Biff Builders','Domestic','690 19th St, NE, Calgary','Borjn','587-555-0100'),
    (8012,'Twinkles Inc','Domestic','318 29th St, NE, Calgary','Barkley','587-555-0175'),
    (8013,'Piddles Industries','Domestic','238 112th Ave, NE, Calgary', 'Parnell','587-555-0161'),
    (8014,'Tic Tac Manufacturing','Domestic','598 Palmer Rd, NE, Calgary','Teisha','587-555-0170'),
	(8015,'Knock Knock Inc','Domestic','363 42nd Ave, NE, Calgary','Ned','587-555-0103'),
	(8016,'Corky Things Inc','Domestic','35 McCall Way, NE, Calgary','Corrine','587-555-0120'),
	(8017,'E & O Industries','Domestic','883 44th St, SE, Calgary','Stan','587-555-0117'),
	(8018,'Fiddleys Makes Stuff Inc','Domestic','350 27th St, NE, Calgary','Fredda','587-555-0147'),
	(8019,'Horks and Stuff Manufacturing','Domestic','997 42nd Ave, NE, Calgary','Harold','587-555-0121'),
	(8020,'Wings Works','Domestic','754 48th St, SE, Calgary','Wing','587-555-0105'),
	(8021,'Ningbo Rilson Sealing Material Co Ltd','International','No 188 West Chongshou Road,Cixi, Ningbo, China','Sunny','86-574-6311-9187'),
	(8022,'Zhejiang Maxtop Tool Manufacturing Co Ltd','International','9 Rongshi Rd, Wenling, Taizhou, Zhejiang, China','Eric','86-576-8640-2898');
    
INSERT INTO INTERNATIONAL VALUES
	(8021,0.05),
	(8022,0.05);
        
INSERT INTO TOOL VALUES
	(1000,'Knock Bits','Non-Electrical',88,12.67,8015),
	(1001,'Widgets','Electrical',10,35.50,8004),
	(1002,'Grommets','Electrical',20,23.45,8001),
	(1003,'Wedges','Non-Electrical',15,10.15,8020),
	(1004,'Wing Bats','Non-Electrical',11,11.25,8003),
	(1005,'Twinkies','Non-Electrical',75,15.75,8012),
	(1006,'Wiggles','Non-Electrical',30,12.34,8020),
	(1007,'Bing Bobs','Non-Electrical',25,2.39,8005),
	(1008,'Wog Wits','Non-Electrical',300,19.99,8004),
	(1009,'Barney Bits','Electrical',21,23.59,8006),
	(1010,'Willie Widgits','Non-Electrical',89,12.99,8003),
	(1011,'Barge Bogs','Non-Electrical',35,2.99,8011),
	(1012,'Poggy Pogs','Non-Electrical',99,1.10,8002),
	(1013,'Pardle Pins','Non-Electrical',400,0.69,8013),
	(1014,'Piddley Wicks','Non-Electrical',54,5.19,8013),
	(1015,'Iggy Orks','Electrical',22,49.95,8010),
	(1016,'Crank Cribs','Non-Electrical',888,0.29,8005),
	(1017,'Thingies','Electrical',67,45.98,8008),
	(1018,'Orf Dappers','Non-Electrical',32,9.98,8018),
	(1019,'Piff Knocks','Non-Electrical',82,4.95,8002),
	(1020,'Knit Piks','Non-Electrical',66,6.75,8015),
	(1021,'Piddley Pins','Non-Electrical',370,0.25,8020),
	(1022,'Tic Tocs','Non-Electrical',87,1.36,8014),
	(1023,'Tin Wits','Non-Electrical',23,5.67,8014),
	(1024,'Thinga-ma-bobs','Non-Electrical',40,10.98,8012),
	(1025,'Fling Flobs','Non-Electrical',254,2.33,8009),
	(1026,'Barn Bins','Electrical',45,88.67,8006),
	(1027,'Flap Wrappers','Electrical',89,44.88,8009),
	(1028,'Pong Pangs','Non-Electrical',2345,0.10,8002),
	(1029,'Oof Tongs','Non-Electrical',345,8.49,8011),
	(1030,'Nic Nacs','Non-Electrical',238,2.99,8015),
	(1031,'Tork Tins','Non-Electrical',376,0.95,8012),
	(1032,'Lilly Larks','Non-Electrical',56,12.99,8007),
	(1033,'Minnie Morks','Non-Electrical',800,1.95,8007),
	(1034,'Cork Handles','Non-Electrical',654,2.66,8016),
	(1035,'Ding Darns','Non-Electrical',1208,0.15,8019),
	(1036,'Erk Orks','Non-Electrical',498,3.99,8017),
	(1037,'Foo Figs','Non-Electrical',234,5.89,8018),
	(1038,'Googly Eyes','Non-Electrical',756,6.99,8001),
	(1039,'Handy Pandies','Non-Electrical',321,4.35,8017),
	(1040,'Inny Outies','Non-Electrical',219,3.45,8010),
	(1041,'Gasket','Non-Electrical',519,1.99,8021),
	(1042,'Wrench set','Non-Electrial',88,18.99,8022);
    
INSERT INTO ELECTRICAL VALUES
	(1001,'Type B'),
	(1002,'Type B'),
	(1009,'Type A'),
	(1015,'Type A'),
	(1017,'Type B'),
	(1026,'Type A'),
	(1027,'Type A');

INSERT INTO CLIENT VALUES 
	(2000,'Ken','Graves','Residential','403-866-9319','4555 Craven Place, Medicine Hat, AB', 'T1A 0N1'),
    (2001,'Stewart','Steven','Commercial','514-803-7810','1759 René-Lévesque Blvd, Montreal, QC','H3B 4W8'),
    (2002,'Wells','Darrel','Commercial','780-681-5901','156 Venture Place, Flatbush, AB','T0G 0Z0'),
    (2003,'Jones','Dennis','Residential','519-727-7920','801 Fallon Drive, Emeryville, ON','N0R 1C0'),
    (2004,'Eldridge','Ray','Residential','613-938-8344','4609 Pitt St, Cornwall, ON','K6J 3R2');
    

-- 	Basic Retrieval Query (Retrieve all non-electrical tools)
--     SELECT * FROM TOOL WHERE Type='Non-Electrical';

--     Retrieval Query with Ordered Result (Retrieve all non-electrical tools ordered by price)
--     SELECT * FROM TOOL WHERE Type='Non-Electrical' ORDER BY Price;
--     
-- 	Nested retrieval query (Retrieve all tools where supplier is domestic
--     SELECT * FROM TOOL WHERE SupplierID IN (SELECT SupplierID FROM SUPPLIER WHERE Type='Domestic');
--     
-- 	Retrieval Query using Joined Tables (Retrieve all info for an electrical tool)
--     SELECT * FROM TOOL LEFT JOIN ELECTRICAL ON TOOL.ToolID = ELECTRICAL.ToolID WHERE TOOL.ToolID=1001;
--     
-- 	Update Operation with Necessary Triggers (Update contact for supplier)
-- 	UPDATE SUPPLIER SET CName='Billy' WHERE SupplierID=8003;

--     Delete Operation with Necessary Triggers (delete an electrical tool)
--     DELETE FROM TOOL WHERE Name = "Widgets";
