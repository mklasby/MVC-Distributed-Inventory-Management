DROP DATABASE IF EXISTS toolshop;
CREATE DATABASE toolshop; 
USE toolshop;

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
);

DROP TABLE IF EXISTS ELECTRICAL;
CREATE TABLE ELECTRICAL (
  ToolID			    int(15), 
  PowerType	    		varchar(15),
  primary key (ToolID),
  foreign key (ToolID) references TOOL(ToolID)
);


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
