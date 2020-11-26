--     Delete Operation with Necessary Triggers (delete an electrical tool)
	
    SELECT * FROM ORDERLINE;
    DELETE FROM ORDERS WHERE OrderID = 10000;
	SELECT * FROM ORDERLINE;
