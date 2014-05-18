package pl.pawelszczerbicki.smarthome.database;

import android.util.Log;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

import static pl.pawelszczerbicki.smarthome.utils.Properties.DATABASE_PORT;
import static pl.pawelszczerbicki.smarthome.utils.Properties.DATABASE_URL;

/**
 * Created by Pawel on 2014-05-17.
 */
public abstract class MongoDb<T> {

    private MongoClient client;

    protected MongoClient getClient() {
        if (client == null)
            try {
                return new MongoClient(DATABASE_URL, DATABASE_PORT);
            } catch (UnknownHostException e) {
                Log.e("Mongo", "Can not connect with mongo");
                throw new IllegalStateException();
            }
        return client;
    }

    protected void save(T entity){

    }
}
