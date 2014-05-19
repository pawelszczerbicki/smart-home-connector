package pl.pawelszczerbicki.smarthome.atmosphre;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import org.atmosphere.wasync.*;
import pl.pawelszczerbicki.smarthome.WelcomeScreen;
import pl.pawelszczerbicki.smarthome.message.Message;

import java.io.IOException;

import static pl.pawelszczerbicki.smarthome.utils.Properties.SERVER_URL;

/**
 * Created by Pawel on 2014-05-16.
 */
public class AtmosphereService extends AsyncTask{

    private final String LOG = getClass().getSimpleName();
    private Socket socket;
    private WelcomeScreen activity;
    private RequestBuilder request;

    public AtmosphereService(WelcomeScreen activity) {
        this.activity = activity;
        Client client = ClientFactory.getDefault().newClient();
        request = getTransport(new Gson(), client);
        socket = client.create();
    }

    private RequestBuilder getTransport(final Gson gson, Client client) {
        return client.newRequestBuilder()
                .method(Request.METHOD.GET)
                .uri(SERVER_URL)
                .header("Content-Type", "application/json")
                .encoder(new Encoder<Message, String>() {
                    @Override
                    public String encode(Message m) {
                        return gson.toJson(m);
                    }
                })
                .decoder(new Decoder<String, Message>() {
                    @Override
                    public Message decode(Event e, String s) {
                        return gson.fromJson(s.split("\\|")[1], Message.class);
                    }
                })
                .transport(Request.TRANSPORT.WEBSOCKET)
                .transport(Request.TRANSPORT.LONG_POLLING);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            socket.on(new Function<Message>() {
                @Override
                public void on(Message m) {
                    activity.service(m);
                }
            }).on(new Function<IOException>() {

                @Override
                public void on(IOException e) {
                    Log.e(LOG, e.getMessage());
                }
            }).open(request.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("", socket.status().name());
        AtmosphereSender.getInstance().setSocket(socket);
        return null;
    }
}
