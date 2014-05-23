package pl.pawelszczerbicki.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import pl.pawelszczerbicki.smarthome.atmosphre.AtmosphereSender;
import pl.pawelszczerbicki.smarthome.atmosphre.AtmosphereService;
import pl.pawelszczerbicki.smarthome.device.Device;
import pl.pawelszczerbicki.smarthome.device.DeviceDao;
import pl.pawelszczerbicki.smarthome.device.DevicesAdapter;
import pl.pawelszczerbicki.smarthome.location.LocationService;
import pl.pawelszczerbicki.smarthome.message.Message;
import pl.pawelszczerbicki.smarthome.message.MessageHistory;
import pl.pawelszczerbicki.smarthome.message.MessageHistoryDao;
import pl.pawelszczerbicki.smarthome.message.MessageService;

import java.util.ArrayList;

public class WelcomeScreen extends Activity {

    private DevicesAdapter adapter;
    private AtmosphereSender sender;
    private LocationService locationService;
    private MessageService messageService;
    private Intent historyIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.main);
        locationService = new LocationService(this);
        setUpDevicesList();
        messageService = new MessageService();
        connectWithAtmosphere();
        buttonListener();
        getDevicesFromDatabase();
        historyIntent = new Intent(this, History.class);
    }

    private void getDevicesFromDatabase() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addAll(new DeviceDao().find());
            }
        });
    }

    private void connectWithAtmosphere() {
        if (hasInternetConnection())
            new AtmosphereService(this).execute();
        else
            Toast.makeText(getApplicationContext(), "No Internet connection! Turn on internet and click 'connect'", Toast.LENGTH_SHORT).show();
    }

    private void buttonListener() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectWithAtmosphere();
            }
        });
    }

    private void setUpDevicesList() {
        adapter = new DevicesAdapter(this, R.layout.list_item, new ArrayList<Device>()).setLocationService(locationService).setMessageHistoryDao(new MessageHistoryDao());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                historyIntent.putExtra("history", new MessageHistoryDao().findByDeviceId(adapter.getItem(i).getId()));
                startActivity(historyIntent);
            }
        });
    }

    private boolean hasInternetConnection() {
        return true;
    }

    public void service(final Message m) {
        final WelcomeScreen screen = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (m.getType() == Message.MessageType.NOTIFICATION && m.getMessage().split(";").length == 2)
                        adapter.getItem(Integer.parseInt(m.getMessage().split(";")[0])).setName(m.getMessage().split(";")[1]);
                    else
                        messageService.service(m, adapter.getById(m.getDeviceId()), screen);
                    adapter.notifyDataSetChanged();
                    new MessageHistoryDao().save(new MessageHistory(m, locationService.location(), true));
                } catch (Exception e) {
                    toast("Something wrong!");
                    e.printStackTrace();
                }

            }
        });

    }

    public void toast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
