package xyz.yyagi.myeventlist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import xyz.yyagi.myeventlist.R;
import xyz.yyagi.myeventlist.models.Event;

/**
 * Created by yaginuma on 14/10/31.
 */

public class EventListAdapter extends ArrayAdapter<Event> {

    private LayoutInflater mInflater;

    public EventListAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = this.getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_card, parent, false);
        }

        TextView tv = (TextView)convertView.findViewById(R.id.sub);
        tv.setText(event.getFormatDates());

        tv = (TextView)convertView.findViewById(R.id.title);
        tv.setText(event.getTitle() + " " + event.getDetail());
        return convertView;
    }

}

