package pl.pawelszczerbicki.smarthome.device;

import com.google.gson.Gson;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import pl.pawelszczerbicki.smarthome.database.MongoDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 2014-05-17.
 */
public class DeviceDao extends MongoDb{

    public List<Device> find(){
        List<Device> devices = new ArrayList<Device>();
        DBCursor cursor = getSession().getCollection("devices").find();
        while(cursor.hasNext()) {
            DBObject obj = cursor.next();
            Device device = new Gson().fromJson(obj.toString(), Device.class);
            device.setId(obj.get("_id").toString());
            devices.add(device);
        }
        return devices;
    }

    @Override
    protected String databaseName() {
        return "home";
    }
}
