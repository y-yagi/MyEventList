package xyz.yyagi.myeventlist.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.List;

import xyz.yyagi.myeventlist.R;
import xyz.yyagi.myeventlist.adapters.EventListAdapter;
import xyz.yyagi.myeventlist.models.Event;
import xyz.yyagi.myeventlist.models.EventDao;
import xyz.yyagi.myeventlist.models.EventDaoHelper;

/**
 * Created by yaginuma on 14/10/31.
 */
public class EventListFragment extends ListFragment {
    protected EventDao eventDao;

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        eventDao = EventDaoHelper.getEventDao(getActivity());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_card, R.id.title);

        // 8dp
        int padding = (int)(getResources().getDisplayMetrics().density * 8);
        ListView listView = getListView();

        // ListViewの左右に余白を追加
        listView.setPadding(padding, 0, padding, 0);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);

        listView.setDivider(null);

        // 余白用のヘッダー、フッタ―を追加
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View header = inflater.inflate(R.layout.list_header_footer, listView, false);
        View footer = inflater.inflate(R.layout.list_header_footer, listView, false);
        listView.addHeaderView(header);
        listView.addHeaderView(footer);

        setListAdapter(adapter);
    }

    @Override
    public void onStart() {
        displayEventData();
        super.onStart();
    }

    public void displayEventData() {
        EventListAdapter eventAdapter = new EventListAdapter(getActivity());
        Long now = DateTime.now().getMillis();
        List<Event> eventList =  eventDao.queryBuilder()
                .where(EventDao.Properties.Start.gt(now / 1000L))
                .orderAsc(EventDao.Properties.Start).list();

        if (eventList.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.no_data_msg), Toast.LENGTH_LONG).show();
            return ;
        }

        eventAdapter.clear();
        eventAdapter.notifyDataSetChanged();
        for (Event event : eventList) {
            eventAdapter.add(event);
        }

        setListAdapter(eventAdapter);
    }

    @Override
    public void onListItemClick(ListView parent, View view, int position, long id) {
        ListView listView = (ListView) parent;
        Event event = (Event) listView.getItemAtPosition(position);
        Uri uri = Uri.parse(event.getUrl());
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        parent.getContext().startActivity(i);
    }
}

