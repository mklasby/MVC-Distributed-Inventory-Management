package Client.ViewController;

/**
 * Static utility class to write orders to file 
 * 
 * @author: Mike Lasby
 * @since: Oct 11, 2020
 * @version: 1.0
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
<<<<<<< HEAD
import java.time.LocalTime;
=======
import java.time.LocalDate;
>>>>>>> 0596c5272c76d4e9b7588595dd2acfeeb6975b36

import Client.ClientController.Message;
import Client.ClientController.ClientServerConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class OrderWriter implements ClientServerConstants {

    private static BufferedWriter bw;

    /**
     * Writes order to file in ./lib/toolshop/orders/
     * 
     * @param order: Order - order object to writ to file
     * @return: String - message of success/fail
     */
    public static String writeOrder(Message order) {
        String userDir = System.getProperty("user.dir");
        
        File file = new File(userDir, "Order.txt");

        try {
            bw = new BufferedWriter(new FileWriter(file));
        } catch (Exception e) {
            e.printStackTrace();
            return "\nERROR! Cannot write file!!\n";
        }

        try {

        	JSONArray orderlines = order.getJSONObject(DATA).getJSONArray("OrderLines");
        	int numItems = orderlines.length(); 
            if (numItems == 0) {
                bw.write("Nothing to order today.\n");
            } else {
            	JSONObject orderline = orderlines.getJSONObject(0);
                bw.write("*".repeat(80) + "\n");
                bw.write(String.format("%20s%40d\n", "ORDER ID: ", orderline.getInt("OrderID")));
<<<<<<< HEAD
                bw.write(String.format("%20s%40s\n", "DATE ORDERED: ", LocalTime.now()));
=======
                bw.write(String.format("%20s%40s\n", "DATE ORDERED: ", LocalDate.now()));
>>>>>>> 0596c5272c76d4e9b7588595dd2acfeeb6975b36
                bw.write("\n");
                
            	for(int n = 0; n < orderlines.length(); n++)
            	{
            		orderline = orderlines.getJSONObject(n);
                    bw.write(String.format("%20s%40s\n", "Tool ID: ", orderline.getInt("ToolID")));
                    bw.write(String.format("%20s%40d\n", "Amount Ordered: ", orderline.getInt("Quantity")));
                    bw.write(String.format("%20s%40s\n", "Supplier ID: ", orderline.getInt("SupplierID")));
                    bw.write("\n");
            	}
            }

            bw.write("*".repeat(80) + "\n");
            bw.close();
            return "Order written to file!";
        } catch (Exception e) {
            e.printStackTrace();
            return "\nERROR! UNABLE TO WRITE TO FILE!\n";
        }
        
    }
    

}