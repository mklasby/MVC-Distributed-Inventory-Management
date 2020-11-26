-- 	Retrieval Query using Joined Tables (Retrieve all info for an electrical tool)
SELECT * FROM TOOL LEFT JOIN ELECTRICAL ON TOOL.ToolID = ELECTRICAL.ToolID WHERE TOOL.ToolID=1001; 
