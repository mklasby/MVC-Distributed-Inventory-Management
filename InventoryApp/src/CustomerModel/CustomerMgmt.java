package CustomerModel;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;

public class CustomerMgmt {

    public JSONArray encodeCustomerSearchQuery(ResultSet rs) {
        try {
            JSONArray jsonArray = new JSONArray();
            do {
                String type = rs.getString("Type");
                if (type.equals("Residential")) {
                    ResidentialCustomer cust = new ResidentialCustomer(rs);
                    jsonArray.put(cust.encode());
                } else {
                    CommercialCustomer cust = new CommercialCustomer(rs);
                    jsonArray.put(cust.encode());
                }
            } while (rs.next());
            return jsonArray;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
