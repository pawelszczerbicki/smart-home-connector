package pl.pawelszczerbicki.smarthome.device;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Pawel on 2014-05-17.
 */
public class Device implements Serializable {
    private String id;

    private String name;

    private Set<Action> actions;

    private State state;

    private Integer value=0;

    private Integer raspiPin;

    private Boolean active;

    public Device(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getValue() {
        return value == null ? 0: value;
    }

    public void setValue(Integer value) {
        this.value = value == null ? 0 : value;
    }

    public Integer getRaspiPin() {
        return raspiPin;
    }

    public void setRaspiPin(Integer raspiPin) {
        this.raspiPin = raspiPin;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public boolean isOn() {
        return state == State.ON;
    }

    public enum State {
        ON, OFF
    }
}
