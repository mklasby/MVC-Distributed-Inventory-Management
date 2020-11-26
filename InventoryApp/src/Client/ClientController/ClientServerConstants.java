package Client.ClientController;

public interface ClientServerConstants {
    // Message Types
    static String MESSAGE_TYPE = "MESSAGE_TYPE";
    static String REQUEST = "REQUEST";
    static String RESPONSE = "RESPONSE";

    // VERBS
    // REQUEST options
    static String VERB = "VERB";// key type
    static String POST = "POST"; // Add new record
    static String GET = "GET"; // get record(s)
    static String PUT = "PUT"; // modify record
    static String DELETE = "DELETE"; // delete record
    static String ORDER = "ORDER"; // Generate order
    // RESPONSE options
    static String ERROR = "ERROR"; // Error in
    static String OK = "OK";

    // DB
    static String DB = "DB"; // key type
    static String INVENTORY = "INVENTORY";
    static String CUSTOMER = "CUSTOMER";

    // QUERY
    static String QUERY = "QUERY"; // key type
    static String BY_ID = "BY_ID";
    static String BY_NAME = "BY_NAME";
    static String BY_TYPE = "BY_TYPE";
    static String ALL = "*";

    // DATA
    static String DATA = "DATA"; // key type

    static String QUIT = "QUIT"; // key type

}
