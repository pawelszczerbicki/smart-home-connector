package pl.pawelszczerbicki.smarthome.message;

import android.location.Location;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Pawel on 2014-05-20.
 */
public class MessageHistory extends Message implements Serializable {

    private Double latitude;

    private Double longitude;

    private Date date = new Date();

    private boolean received = true;


    public MessageHistory(Message m, Location l, boolean received) {
        super(m.getType(), m.getDeviceId(), m.getRaspiPin(), m.getAction(), m.getMessage());
        if (l != null) {
            this.latitude = l.getLatitude();
            this.longitude = l.getLongitude();
        }
        this.received = received;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
