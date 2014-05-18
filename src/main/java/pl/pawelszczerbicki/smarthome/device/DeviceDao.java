package pl.pawelszczerbicki.smarthome.device;

import android.util.Log;
import pl.pawelszczerbicki.smarthome.database.MongoDb;

/**
 * Created by Pawel on 2014-05-17.
 */
public class DeviceDao extends MongoDb{

    public void printDatabases(){
        for (String s : getClient().getDatabaseNames()) {
            Log.e("DeviceDao", s);

        }
    }
}
