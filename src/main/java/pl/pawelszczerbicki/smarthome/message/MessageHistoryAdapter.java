package pl.pawelszczerbicki.smarthome.message;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import pl.pawelszczerbicki.smarthome.History;
import pl.pawelszczerbicki.smarthome.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Pawel on 2014-05-20.
 */
public class MessageHistoryAdapter extends ArrayAdapter<MessageHistory> {
    private int layoutResourceId;
    private History history;

    public MessageHistoryAdapter(Context context, int layoutResourceId, List<MessageHistory> items, History history) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.history = history;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        convertView = inflater.inflate(layoutResourceId, parent, false);
        convertView.setTag(holder(position, convertView));
        return convertView;
    }

    private HistoryHolder holder(int position, View convertView) {
        HistoryHolder holder = new HistoryHolder();
        final MessageHistory h = getItem(position);
        holder.showMap = (Button) convertView.findViewById(R.id.map);
        holder.incoming = (TextView) convertView.findViewById(R.id.incoming);
        holder.action = (TextView) convertView.findViewById(R.id.action);
        holder.pin = (TextView) convertView.findViewById(R.id.pin);
        holder.msg = (TextView) convertView.findViewById(R.id.msg);
        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.incoming.setText("Incoming: " + h.isReceived());
        holder.action.setText("Action: " + h.getAction());
        holder.pin.setText("Pin: " + (h.getRaspiPin() == null ? "" : h.getRaspiPin()));
        holder.msg.setText("Msg: " + (h.getMessage() == null? "" : h.getMessage()) );
        holder.date.setText("Date: " + (h.getDate() == null ? "" : new SimpleDateFormat("dd-mm HH:mm:ss").format(h.getDate())));

        if (h.getLatitude() == null && h.getLongitude() == null)
            holder.showMap.setEnabled(false);
        holder.showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String uri = String.format(Locale.ENGLISH, "geo:%s,%s?q=%s,%s", h.getLatitude(), h.getLongitude(), h.getLatitude(),h.getLongitude());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    history.startActivity(intent);
            }
        });
        return null;
    }

    public class HistoryHolder {
        Button showMap;
        TextView incoming;
        TextView action;
        TextView pin;
        TextView msg;
        TextView date;
    }
}
