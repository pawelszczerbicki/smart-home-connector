package pl.pawelszczerbicki.smarthome;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import pl.pawelszczerbicki.smarthome.atmosphre.AtmosphereSender;
import pl.pawelszczerbicki.smarthome.atmosphre.AtmosphereService;
import pl.pawelszczerbicki.smarthome.device.Device;
import pl.pawelszczerbicki.smarthome.device.DevicesAdapter;
import pl.pawelszczerbicki.smarthome.location.LocationService;
import pl.pawelszczerbicki.smarthome.message.Message;
import pl.pawelszczerbicki.smarthome.message.MessageService;

import java.util.ArrayList;

public class WelcomeScreen extends Activity {

    private DevicesAdapter adapter;
    private AtmosphereSender sender;
    private LocationService locationService;
    private MessageService messageService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpDevicesList();
        locationService = new LocationService(this);
        messageService = new MessageService();
        connectWithAtmosphere();
        buttonListener();
        Device d = new Device("name 1", 1234);
        d.setId("531b5415975a961d9b2d5df6");
        adapter.insert(d, 0);
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
        adapter = new DevicesAdapter(this, R.layout.list_item, new ArrayList<Device>());
        ((ListView) findViewById(R.id.listView)).setAdapter(adapter);
    }

    private boolean hasInternetConnection() {
        return true;
    }

    public void service(final Message m) {
        final WelcomeScreen screen = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    if (m.getType() == Message.MessageType.NOTIFICATION && m.getMessage().split(";").length == 2)
                        adapter.getItem(Integer.parseInt(m.getMessage().split(";")[0])).setName(m.getMessage().split(";")[1]);
                    else
                        messageService.service(m, adapter.getById(m.getDeviceId()), screen);
                    adapter.notifyDataSetChanged();
                } catch (Exception e){
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
