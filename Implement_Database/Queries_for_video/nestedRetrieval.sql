-- 	Nested retrieval query (Retrieve all tools where supplier is domestic
    SELECT * FROM TOOL WHERE SupplierID IN (SELECT SupplierID FROM SUPPLIER WHERE Type='Domestic');