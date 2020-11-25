package Client.ClientController;

import org.json.JSONException;
import org.json.JSONObject;

public class Message extends JSONObject implements ClientServerConstants {

    public Message() throws JSONException {
        put(QUIT, false);
    };

    public Message(String rawData) throws JSONException {
        super(rawData);
        put(QUIT, false);
    };

    public Message(String messageType, String verb, String db, Object data) throws JSONException {
        put(MESSAGE_TYPE, messageType);
        put(VERB, verb);
        put(DB, db);
        put(DATA, data);
        put(QUIT, false);
    }

    /**
     * ERROR MESSAGE
     */
    public Message(String messageType, String verb, Object data) throws JSONException {
        put(MESSAGE_TYPE, messageType);
        put(DATA, data);
        put(VERB, verb);
        put(QUIT, false);

    }

    public void addQueryType(String query) throws JSONException {
        put(QUERY, query);
    }

    public void quitMessage() throws JSONException {
        put(QUIT, true);
    }
}
