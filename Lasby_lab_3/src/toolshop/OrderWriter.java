package toolshop;

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

public class OrderWriter {
    private static String OUTPUT_DIR = String.format("%slib%stoolshop%sorders%s", File.separator, File.separator,
            File.separator, File.separator);
    private static BufferedWriter bw;

    /**
     * Writes order to file in ./lib/toolshop/orders/
     * 
     * @param order: Order - order object to writ to file
     * @return: String - message of success/fail
     */
    public static String writeOrder(Order order) {
        String userDir = System.getProperty("user.dir");
        File file = new File(userDir + OUTPUT_DIR,
                String.format("OrderNo_%d_%s.txt", order.getOrderID(), order.getDate()));

        try {
            bw = new BufferedWriter(new FileWriter(file));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("\nERROR! Cannot write file!!\n");
        }

        try {

            bw.write("*".repeat(80) + "\n");
            bw.write(String.format("%20s%40d\n", "ORDER ID: ", order.getOrderID()));
            bw.write(String.format("%20s%40s\n", "DATE ORDERED: ", order.getDate()));
            bw.write("\n");

            if (order.getItems().size() == 0) {
                bw.write("Nothing to order today.\n");
            } else {

                for (OrderLine line : order.getItems()) {
                    bw.write(String.format("%20s%40s\n", "Item Description: ", line.getDescription()));
                    bw.write(String.format("%20s%40d\n", "Amount Ordered: ", line.getAmount()));
                    bw.write(String.format("%20s%40s\n", "Supplier: ", line.getSupplier()));
                    bw.write("\n");
                }
            }
            bw.write("*".repeat(80) + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "\nERROR! UNABLE TO WRITE TO FILE!\n";

        }

        return String.format("Successfully wrote Order No. %d to file at %s.\n", order.getOrderID(),
                userDir + OUTPUT_DIR + String.format("OrderNo%d_%s.txt", order.getOrderID(), order.getDate()));
    }

}
