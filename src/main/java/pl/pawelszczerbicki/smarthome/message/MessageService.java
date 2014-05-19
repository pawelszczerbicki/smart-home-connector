package pl.pawelszczerbicki.smarthome.message;

import pl.pawelszczerbicki.smarthome.WelcomeScreen;
import pl.pawelszczerbicki.smarthome.device.Action;
import pl.pawelszczerbicki.smarthome.device.Device;

import static pl.pawelszczerbicki.smarthome.message.Message.MessageType.ACTION;

/**
 * Created by Pawel on 2014-05-18.
 */
public class MessageService {

    public Device service(Message m, Device d, WelcomeScreen s) {
        if (m.getType() == ACTION && d != null)
            serviceAction(m, d, s);
        return d;
    }

    private void serviceAction(Message m, Device d, WelcomeScreen s) {
        if (m.getAction() == Action.ON)
            d.setState(Device.State.ON);
        else if (m.getAction() == Action.OFF)
            d.setState(Device.State.OFF);
        else if (m.getAction() == Action.READ || m.getAction() == Action.ADJUST)
            d.setValue(new Double(Double.parseDouble(m.getMessage())).intValue());
        else if (m.getAction() == Action.FLICKER)
            s.toast("Flickering");
    }
}
