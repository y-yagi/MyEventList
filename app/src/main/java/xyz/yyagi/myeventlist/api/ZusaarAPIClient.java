package xyz.yyagi.myeventlist.api;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yaginuma on 14/10/31.
 */
public class ZusaarAPIClient extends  EventAPIClient {
    private static final String TAG = ZusaarAPIClient.class.getSimpleName();
    private static final String API_URL = "http://www.zusaar.com/api/event/";

    protected String eventDataKey= "event";

    public ZusaarAPIClient(RequestQueue mQueue, Context context) {
        super(mQueue, context);
    }

    public void loadEventData() {
        String userName = PreferenceManager.getDefaultSharedPreferences(context).getString("zusaar_nickname", "");
        Log.e(TAG, "userName: " + userName);
        if (userName == "")
            return ;

        String url = API_URL + "?nickname=" + userName;
        Log.e(TAG, "url: " + url);
        super.loadEventData(userName, url);
    }

    @Override
    protected JSONArray getEvents(JSONObject response) throws JSONException {
        return response.getJSONArray(eventDataKey);
    }
}
