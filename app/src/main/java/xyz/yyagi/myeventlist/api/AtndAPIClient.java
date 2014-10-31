package xyz.yyagi.myeventlist.api;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;

/**
 * Created by yaginuma on 14/05/11.
 */
public class AtndAPIClient extends EventAPIClient {
    private static final String TAG = AtndAPIClient.class.getSimpleName();
    private static final String API_URL = "http://api.atnd.org/events/?format=json";

    public AtndAPIClient(RequestQueue mQueue, Context context) {
        super(mQueue, context);
    }

    public void loadEventData() {
        String userName = PreferenceManager.getDefaultSharedPreferences(context).getString("atnd_nickname", "");
        Log.e(TAG, "userName: " + userName);
        if (userName == "")
            return ;

        String url = API_URL + "&nickname=" + userName;
        super.loadEventData(userName, url);
    }
}
