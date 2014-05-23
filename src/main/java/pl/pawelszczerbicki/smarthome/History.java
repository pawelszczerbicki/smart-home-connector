package pl.pawelszczerbicki.smarthome;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import pl.pawelszczerbicki.smarthome.message.MessageHistory;
import pl.pawelszczerbicki.smarthome.message.MessageHistoryAdapter;

import java.util.ArrayList;

/**
 * Created by Pawel on 2014-05-20.
 */
public class History extends Activity {

    private MessageHistoryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        adapter = new MessageHistoryAdapter(this, R.layout.history_list_item,(ArrayList<MessageHistory>) getIntent().getSerializableExtra("history"), this);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
