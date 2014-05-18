package pl.pawelszczerbicki.smarthome.atmosphre;

import org.atmosphere.wasync.Socket;
import pl.pawelszczerbicki.smarthome.message.Message;

import java.io.IOException;

/**
 * Created by Pawel on 2014-05-16.
 */
public class AtmosphereSender {

    private Socket socket;

    public AtmosphereSender(Socket socket) {
        this.socket = socket;
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
