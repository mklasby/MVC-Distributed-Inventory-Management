-- 	Retrieval Query using Joined Tables (Retrieve all info for all tools in OrderLine)
SELECT * FROM TOOL AS t JOIN ORDERLINE AS o ON t.ToolID = o.ToolID;

