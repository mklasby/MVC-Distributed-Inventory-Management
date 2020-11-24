package Client.ClientController;

import org.json.JSONException;
import org.json.JSONObject;

public class Message extends JSONObject implements ClientServerConstants {

    public Message() {
    };

    public Message(String messageType, String verb, String db, Object data) throws JSONException {
        put(MESSAGE_TYPE, messageType);
        put(VERB, verb);
        put(DB, db);
        put(DATA, data);
    }

    public void addQueryType(String query) throws JSONException {
        put(QUERY, query);
    }

    public void 

}
