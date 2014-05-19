package pl.pawelszczerbicki.smarthome.atmosphre;

import org.atmosphere.wasync.Socket;
import pl.pawelszczerbicki.smarthome.message.Message;

import java.io.IOException;

/**
 * Created by Pawel on 2014-05-16.
 */
public class AtmosphereSender {

    private static AtmosphereSender instance = new AtmosphereSender();
    private Socket socket;

    public static AtmosphereSender getInstance() {
        return instance;
    }

    private AtmosphereSender() {
    }

    public void setSocket(Socket s) {
        this.socket = s;
    }

    public void send(Message m) {
        if(socket == null)
            throw new IllegalStateException("Socket is not initialized!");
        try {
            socket.fire(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
