package pl.pawelszczerbicki.smarthome;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import pl.pawelszczerbicki.smarthome.atmosphre.AtmosphereSender;
import pl.pawelszczerbicki.smarthome.atmosphre.AtmosphereService;
import pl.pawelszczerbicki.smarthome.device.Device;
import pl.pawelszczerbicki.smarthome.device.DevicesAdapter;
import pl.pawelszczerbicki.smarthome.location.LocationService;

import java.io.IOException;
import java.util.ArrayList;

public class WelcomeScreen extends Activity {
    private DevicesAdapter adapter;
    private AtmosphereSender sender;
    private LocationService locationService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpDevicesList();
        disableAsyncStrictMode();
        setupListener();
        locationService = new LocationService(this);
        connectWithAtmosphere();
        buttonListener();
    }

    private void setupListener() {

    }

    private void connectWithAtmosphere() {
        if (hasInternetConnection())
            try {
                sender = new AtmosphereService(this).startListening();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Can not connect with atmosphere", Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
        else
            Toast.makeText(getApplicationContext(), "No Internet connection! Turn on internet and click 'connect'", Toast.LENGTH_SHORT);
    }

    private void buttonListener() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectWithAtmosphere();
            }
        });
    }

    private void disableAsyncStrictMode() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    private void setUpDevicesList() {
        adapter = new DevicesAdapter(this, R.layout.list_item, new ArrayList<Device>());
        ((ListView) findViewById(R.id.listView)).setAdapter(adapter);
    }

    private boolean hasInternetConnection() {
        return ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
