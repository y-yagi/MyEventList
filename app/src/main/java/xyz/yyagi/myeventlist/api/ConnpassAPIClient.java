package xyz.yyagi.myeventlist.api;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;

/**
 * Created by yaginuma on 14/10/31.
 */
public class ConnpassAPIClient extends  EventAPIClient {
    private static final String TAG = ConnpassAPIClient.class.getSimpleName();
    private static final String API_URL = "http://connpass.com/api/v1/event?format=json";

    public ConnpassAPIClient(RequestQueue mQueue, Context context) {
        super(mQueue, context);
    }

    public void loadEventData() {
        String userName = PreferenceManager.getDefaultSharedPreferences(context).getString("connpass_nickname", "");
        Log.e(TAG, "userName: " + userName);
        if (userName == "")
            return ;

        String url = API_URL + "&nickname=" + userName;
        super.loadEventData(userName, url);
    }
}
