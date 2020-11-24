package DBController;

import java.sql.ResultSet;
import org.json.JSONObject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.sql.Date;

public class InventoryDBController extends DBController {

	/**
	 * Return tools in the database.
	 * @return ResultSet with data of all the tools in the database.
	 */
	public ResultSet getInventory() {
		String sql = "SELECT * FROM TOOL LEFT JOIN ELECTRICAL ON TOOL.ToolID = ELECTRICAL.ToolID;";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) return rs;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Search the database for a tool matching the id parameter and return that tool.
	 * @param id tool id
	 * @return ResultSet with data of the tool matching the given id or null if not found.
	 */
	public ResultSet searchToolbyID(int id) {
		String sql = "SELECT * FROM TOOL LEFT JOIN ELECTRICAL ON TOOL.ToolID = ELECTRICAL.ToolID WHERE TOOL.ToolID=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) return rs;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Search the database for a tool matching the name parameter and return that tool.
	 * @param name tool name in lower-case
	 * @return ResultSet with data of the tool matching the given name or null if not found.
	 */
	public ResultSet searchToolbyName(String name) {
		String sql = "SELECT * FROM TOOL LEFT JOIN ELECTRICAL ON TOOL.ToolID = ELECTRICAL.ToolID WHERE LOWER(Name)=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if(rs.next()) return rs;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Search the database for tools based on tool type.
	 * @param type tool type (either electrical or non-electrical)
	 * @return ResultSet with data of tools of the given tool type or null if not found.
	 */
	public ResultSet searchToolbyType(String type) {
		String sql = "SELECT * FROM TOOL LEFT JOIN ELECTRICAL ON TOOL.ToolID = ELECTRICAL.ToolID WHERE LOWER(Type)=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, type);
			rs = stmt.executeQuery();
			if(rs.next()) return rs;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Add tool encoded as JSONObject to the database.
	 * @param tool tool encoded as JSONObject
	 */
	public void addTool(JSONObject tool) {
		String sql = "INSERT INTO TOOL VALUES(?,?,?,?,?,?);";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, tool.getInt("ToolID"));
			stmt.setString(2, tool.getString("Name"));
			stmt.setString(3, tool.getString("Type"));
			stmt.setInt(4, tool.getInt("Quantity"));
			stmt.setDouble(5, tool.getDouble("Price"));
			stmt.setInt(6, tool.getInt("SupplierID"));
			stmt.executeUpdate();
			
			if (tool.getString("Type").toLowerCase().equals("electrical")) {
				sql = "INSERT INTO ELECTRICAL VALUES(?,?);";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, tool.getInt("ToolID"));
				stmt.setString(2, tool.getString("PowerType"));
				stmt.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete tool with given lower-cased name from the database.
	 * @param name tool name in lower-case
	 */
	public void deleteToolbyName(String name) {
		String sql = "DELETE FROM TOOL WHERE LOWER(Name)=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete tool with given id from the database.
	 * @param id tool id
	 */
	public void deleteToolbyID(int id) {
		String sql = "DELETE FROM TOOL WHERE ToolID=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add given order-line to the database. If no order exist, will also 
	 * create an order.
	 * @param orderline JSONObject containing the tool id, supplier id, and quantity ordered
	 */
	public void generateOrderLine(JSONObject orderline) {
		Date date = Date.valueOf(LocalDate.now());
		String sql = "SELECT * FROM ORDERS WHERE Date=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setDate(1, date);
			rs = stmt.executeQuery(); 
			
			int orderID;
			if (rs.next()) {			
				orderID = rs.getInt("OrderID");
			// create order if it does not exist
			} else {
				sql = "INSERT INTO ORDERS VALUES(?,?);";
				orderID = 10000 + new Random().nextInt(90000);
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1,orderID);
				stmt.setDate(2, date);
				stmt.executeUpdate();
			}
			sql = "INSERT INTO ORDERLINE VALUES(?,?,?,?);";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, orderID);
			stmt.setInt(2, orderline.getInt("ToolID"));
			stmt.setInt(3, orderline.getInt("SupplierID"));
			stmt.setInt(4, orderline.getInt("Quantity"));
			stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reduce tool quantity by quantitySold for tool with given id 
	 * @param id tool id
	 * @param quantitySold quantity sold
	 * @throws SQLException
	 */
	public void reduceToolQuantity(int id, int quantitySold) throws SQLException {
		String sql = "UPDATE TOOL SET Quantity=Quantity-? WHERE ToolID=?;";
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, quantitySold);
		stmt.setInt(2, id);
		stmt.executeUpdate();
	}
	
//	public static void main(String[] args) {
//		try {
//			InventoryController c = new InventoryController();
//			
//			c.reduceToolQuantity(1001, 1);
//			
//			ResultSet rs = c.getInventory();
//			do {
//				String type = rs.getString("Type");
//				System.out.println(rs.getInt("ToolID")); 
//				System.out.println(rs.getString("Name")); 
//				System.out.println(type); 
//				System.out.println(rs.getInt("Quantity")); 
//				System.out.println(rs.getDouble("Price")); 
//				System.out.println(rs.getInt("SupplierID")); 
//				if (type.equalsIgnoreCase("electrical")) System.out.println(rs.getString("PowerType")); 
//			} while(rs.next());
//
//			ResultSet rs = c.searchToolbyID(1001);
//			String type = rs.getString("Type");
//			System.out.println(rs.getInt("ToolID")); 
//			System.out.println(rs.getString("Name")); 
//			System.out.println(rs.getString("Type")); 
//			System.out.println(rs.getInt("Quantity")); 
//			System.out.println(rs.getDouble("Price")); 
//			System.out.println(rs.getInt("SupplierID")); 
//			if (type.equalsIgnoreCase("electrical")) System.out.println(rs.getString("PowerType")); 
//
//			ResultSet rs = c.searchToolbyName("widgets");
//			String type = rs.getString("Type");
//			System.out.println(rs.getInt("ToolID")); 
//			System.out.println(rs.getString("Name")); 
//			System.out.println(type); 
//			System.out.println(rs.getInt("Quantity")); 
//			System.out.println(rs.getDouble("Price")); 
//			System.out.println(rs.getInt("SupplierID")); 
//			if (type.equalsIgnoreCase("electrical")) System.out.println(rs.getString("PowerType")); 
//			
//			ResultSet rs = c.searchToolbyType("electrical");
//			do {
//				String type = rs.getString("Type");
//				System.out.println(rs.getInt("ToolID")); 
//				System.out.println(rs.getString("Name")); 
//				System.out.println(type); 
//				System.out.println(rs.getInt("Quantity")); 
//				System.out.println(rs.getDouble("Price")); 
//				System.out.println(rs.getInt("SupplierID")); 
//				if (type.equalsIgnoreCase("electrical")) System.out.println(rs.getString("PowerType")); 
//			} while(rs.next());
//			
//			JSONObject obj = new JSONObject();
//			obj.put("ToolID", Integer.parseInt("1043"));
//			obj.put("Name", "Chainsaw");
//			obj.put("Type", "Electrical");
//			obj.put("Quantity",  Integer.parseInt("69"));
//			obj.put("Price", Double.parseDouble("69.99"));
//			obj.put("SupplierID", Integer.parseInt("8001"));
//			// only for electrical tools
//			obj.put("PowerType", "Type B");
//			c.addTool(obj);
//			
//			c.deleteToolbyName("chainsaw");
//			
//			c.deleteToolbyID(1043);	
//			
//			JSONObject obj = new JSONObject();
//			obj.put("ToolID", 1001);
//			obj.put("SupplierID", 8004);
//			obj.put("Quantity",  40);
//			c.generateOrderLine(obj);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	
	
}

