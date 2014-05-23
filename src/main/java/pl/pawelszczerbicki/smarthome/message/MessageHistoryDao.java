package pl.pawelszczerbicki.smarthome.message;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import pl.pawelszczerbicki.smarthome.database.MongoDb;

import java.util.ArrayList;

/**
 * Created by Pawel on 2014-05-20.
 */
public class MessageHistoryDao extends MongoDb {

    public void save(MessageHistory history) {
        getSession().getCollection("history").save((DBObject) JSON.parse(new Gson().toJson(history)));
    }

    @Override
    protected String databaseName() {
        return "android_project";
    }

    public ArrayList<MessageHistory> findByDeviceId(String deviceId) {
        ArrayList<MessageHistory> history = new ArrayList<MessageHistory>();
        DBObject finder = new BasicDBObject();
        finder.put("deviceId", deviceId);
        DBCursor cursor = getSession().getCollection("history").find(finder).sort(new BasicDBObject("_id", -1));
        while(cursor.hasNext()) {
            DBObject obj = cursor.next();
            MessageHistory device = new Gson().fromJson(obj.toString(), MessageHistory.class);
            history.add(device);
        }
        return history;
    }
}
