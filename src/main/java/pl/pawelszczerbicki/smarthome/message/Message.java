package pl.pawelszczerbicki.smarthome.message;

import pl.pawelszczerbicki.smarthome.device.Action;

/**
 * Created by Pawel on 2014-05-16.
 */
public class Message {

    private MessageType type;
    private String deviceId;
    private Integer raspiPin;
    private Action action;
    private String message;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getRaspiPin() {
        return raspiPin;
    }

    public void setRaspiPin(Integer raspiPin) {
        this.raspiPin = raspiPin;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum MessageType {
        WELCOME, CONFIG, ACTION, CHECK_DEVICE, INFO, HEARTBEAT, NOTIFICATION
    }
}
