package pl.pawelszczerbicki.smarthome.database;

import android.util.Log;
import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

import static pl.pawelszczerbicki.smarthome.utils.Properties.DATABASE_PORT;
import static pl.pawelszczerbicki.smarthome.utils.Properties.DATABASE_URL;

/**
 * Created by Pawel on 2014-05-17.
 */
public abstract class MongoDb<T> {

    private MongoClient client;

    protected DB getSession() {
        if (client == null)
            try {
                client =  new MongoClient(DATABASE_URL, DATABASE_PORT);
            } catch (UnknownHostException e) {
                Log.e("Mongo", "Can not connect with mongo");
                throw new IllegalStateException();
            }
        return client.getDB(databaseName());
    }

    protected abstract String databaseName();
}
