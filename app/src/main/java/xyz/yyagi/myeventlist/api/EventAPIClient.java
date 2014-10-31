package xyz.yyagi.myeventlist.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import xyz.yyagi.myeventlist.models.EventDao;
import xyz.yyagi.myeventlist.models.EventDaoHelper;
import xyz.yyagi.myeventlist.models.Event;
import xyz.yyagi.myeventlist.utils.Util;


/**
 * Created by yaginuma on 14/10/31.
 */
public class EventAPIClient {
    protected RequestQueue mQueue;
    protected Context context;
    protected EventDao eventDao;

    private static final String TAG = EventAPIClient.class.getSimpleName();
    protected String eventDataKey= "events";
    protected String eventCountKey = "results_returned";

    public EventAPIClient(RequestQueue mQueue, Context context) {
        this.mQueue = mQueue;
        this.context = context;

        eventDao = EventDaoHelper.getEventDao(context);
    }

    public void loadEventData(String userName, final String url) {
        Log.d(TAG, url);
        mQueue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            int count = getEventCount(response);
                            Log.e(TAG, "event count: " + count);
                            if (count != 0)
                                updateEventData(getEvents(response));
                        } catch (JSONException e) {
                            Log.e(TAG, "Data parse error url: " + url);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Data load error url: " + url);
                        Log.e(TAG, error.toString());
                        error.printStackTrace();
                    }
                }
        ));
    }

    protected int getEventCount(JSONObject response) throws JSONException{
        return response.getInt(eventCountKey);
    }

    protected JSONArray getEvents(JSONObject response) throws  JSONException {
        return response.getJSONArray(eventDataKey);
    }

    protected void updateEventData(JSONArray events) {
        JSONObject event;
        Event mEvent, existEvent;
        try {
            for (int i = 0; i < events.length(); i++) {
                event = events.getJSONObject(i);
                String mTitle = "", mCatch = "", mEventUrl = "";
                int mStartedAt = 0, mEndedAt = 0;

                if (event.has("title")) mTitle = event.getString("title");
                if (event.has("catch")) mCatch = event.getString("catch");
                if (event.has("event_url")) mEventUrl = event.getString("event_url");
                if (event.has("started_at")) mStartedAt = Util.convertDateTimeStrToInt(event.getString("started_at"));
                if (event.has("ended_at")) mEndedAt = Util.convertDateTimeStrToInt(event.getString("ended_at"));
                if (mEventUrl.isEmpty()) continue;

                mEvent = new Event(null, mTitle, mCatch, mEventUrl, mStartedAt, mEndedAt);
                existEvent = existEvent(mEvent.getUrl());
                if (existEvent != null) {
                    mEvent.setId(existEvent.getId());
                    eventDao.update(mEvent);
                } else  {
                    eventDao.insert(mEvent);
                }

                // TODO:キャンセルイベントについて更新処理が必要
            }
        } catch (JSONException e) {
            Log.e(TAG, "Data parse error");
            e.printStackTrace();
        }
    }

    private Event existEvent(String url) {
        List<Event> eventList = eventDao.queryBuilder().where(EventDao.Properties.Url.eq(url)).list();
        if (eventList.isEmpty()) {
            return null;
        } else {
            return eventList.get(0);
        }
    }
}
