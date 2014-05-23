package pl.pawelszczerbicki.smarthome.message;

import pl.pawelszczerbicki.smarthome.device.Action;

import java.io.Serializable;

/**
 * Created by Pawel on 2014-05-16.
 */
public class Message implements Serializable{

    private MessageType type;
    private String deviceId;
    private Integer raspiPin;
    private Action action;
    private String message;

    public Message(MessageType type, String deviceId, Integer raspiPin, Action action, String message) {
        this.type = type;
        this.deviceId = deviceId;
        this.raspiPin = raspiPin;
        this.action = action;
        this.message = message;
    }

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

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", deviceId='" + deviceId + '\'' +
                ", raspiPin=" + raspiPin +
                ", action=" + action +
                ", message='" + message + '\'' +
                '}';
    }
}
