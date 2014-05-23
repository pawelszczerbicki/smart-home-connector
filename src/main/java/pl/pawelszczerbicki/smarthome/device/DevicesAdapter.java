package pl.pawelszczerbicki.smarthome.device;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import pl.pawelszczerbicki.smarthome.R;
import pl.pawelszczerbicki.smarthome.atmosphre.AtmosphereSender;
import pl.pawelszczerbicki.smarthome.location.LocationService;
import pl.pawelszczerbicki.smarthome.message.Message;
import pl.pawelszczerbicki.smarthome.message.MessageHistory;
import pl.pawelszczerbicki.smarthome.message.MessageHistoryDao;

import java.util.List;

import static pl.pawelszczerbicki.smarthome.message.Message.MessageType.ACTION;

/**
 * Created by Pawel on 2014-05-17.
 */
public class DevicesAdapter extends ArrayAdapter<Device> {
    private int layoutResourceId;
    private AtmosphereSender sender;
    private LocationService locationService;
    private MessageHistoryDao messageHistoryDao;

    public DevicesAdapter(Context context, int layoutResourceId, List<Device> items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        sender = AtmosphereSender.getInstance();
    }

    public DevicesAdapter setLocationService(LocationService locationService) {
        this.locationService = locationService;
        return this;
    }

    public DevicesAdapter setMessageHistoryDao(MessageHistoryDao messageHistoryDao) {
        this.messageHistoryDao = new MessageHistoryDao();
        return this;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        convertView = inflater.inflate(layoutResourceId, parent, false);
        convertView.setTag(holder(position, convertView));
        return convertView;
    }

    private DeviceHolder holder(int position, View convertView) {
        DeviceHolder holder = new DeviceHolder();
        Device d = getItem(position);
        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.value = (TextView) convertView.findViewById(R.id.value);
        holder.switchDevice = (ToggleButton) convertView.findViewById(R.id.switchDevice);
        holder.flicker = (Button) convertView.findViewById(R.id.flicker);
        holder.valueSeek = (SeekBar) convertView.findViewById(R.id.valueSeek);
        holder.name.setText(d.getName());
        holder.value.setText(d.getValue().toString());
        holder.switchDevice.setChecked(d.isOn());

        if(!d.getActions().contains(Action.SWITCH))
            holder.switchDevice.setEnabled(false);

        holder.valueSeek.setProgress(d.getValue());
        if(!d.getActions().contains(Action.ADJUST))
            holder.valueSeek.setEnabled(false);

        if(!d.getActions().contains(Action.FLICKER))
            holder.flicker.setEnabled(false);

        holder.name.setClickable(false);
        holder.value.setClickable(false);
        addListeners(holder, d);
        return holder;
    }

    private void addListeners(final DeviceHolder holder, final Device d) {
        holder.flicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message m = new Message(ACTION, d.getId(), d.getRaspiPin(), Action.FLICKER, "100");
                sender.send(m);
                saveHistory(m);
            }
        });

        holder.valueSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Message m = new Message(ACTION, d.getId(), d.getRaspiPin(), Action.ADJUST, String.valueOf(i));
                sender.send(m);
                sender.send(new Message(ACTION, d.getId(), d.getRaspiPin(), Action.READ, String.valueOf(i)));
                saveHistory(m);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        holder.switchDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message m = new Message(ACTION, d.getId(), d.getRaspiPin(), holder.switchDevice.isChecked() ? Action.ON : Action.OFF, "");
                sender.send(m);
                saveHistory(m);
            }
        });
    }

    private void saveHistory(Message m) {
        messageHistoryDao.save(new MessageHistory(m, locationService.location(), false));
    }

    public Device getById(String deviceId) {
        for (int i = 0; i < getCount(); i++)
            if (getItem(i).getId().equals(deviceId))
                return getItem(i);
        return null;
    }

    private class DeviceHolder {
        private TextView name;
        private TextView value;
        private ToggleButton switchDevice;
        private Button flicker;
        private SeekBar valueSeek;
    }
}
