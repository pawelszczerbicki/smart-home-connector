package pl.pawelszczerbicki.smarthome.device;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import pl.pawelszczerbicki.smarthome.R;

import java.util.List;

/**
 * Created by Pawel on 2014-05-17.
 */
public class DevicesAdapter extends ArrayAdapter<Device> {
    private int layoutResourceId;

    public DevicesAdapter(Context context, int layoutResourceId, List<Device> items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
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
        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.value = (TextView) convertView.findViewById(R.id.value);
        holder.switchDevice = (ToggleButton) convertView.findViewById(R.id.switchDevice);
        holder.flicker = (Button) convertView.findViewById(R.id.flicker);
        holder.valueSeek = (SeekBar) convertView.findViewById(R.id.valueSeek);
        holder.name.setText(getItem(position).getName());
        holder.value.setText(getItem(position).getValue().toString());
        return holder;
    }

    private class DeviceHolder {
        private TextView name;
        private TextView value;
        private ToggleButton switchDevice;
        private Button flicker;
       private  SeekBar valueSeek;
    }
}
