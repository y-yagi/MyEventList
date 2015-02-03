package xyz.yyagi.myeventlist.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import xyz.yyagi.myeventlist.R;
import xyz.yyagi.myeventlist.api.AtndAPIClient;
import xyz.yyagi.myeventlist.api.ConnpassAPIClient;
import xyz.yyagi.myeventlist.api.OkHttpStack;
import xyz.yyagi.myeventlist.api.ZusaarAPIClient;
import xyz.yyagi.myeventlist.fragments.EventListFragment;
import xyz.yyagi.myeventlist.models.EventDao;
import xyz.yyagi.myeventlist.models.EventDaoHelper;


public class MainActivity extends Activity {
    private RequestQueue mQueue;
    protected EventDao eventDao;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isFinishedSetting()) {
            Toast.makeText(this, getString(R.string.no_setting_msg), Toast.LENGTH_LONG).show();
            return;
        }

        mQueue = Volley.newRequestQueue(this, new OkHttpStack());

        ConnpassAPIClient connpassAPIClient = new ConnpassAPIClient(mQueue, this);
        connpassAPIClient.loadEventData();
        ZusaarAPIClient zussarAPIClient = new ZusaarAPIClient(mQueue, this);
        zussarAPIClient.loadEventData();
        AtndAPIClient atndAPIClient = new AtndAPIClient(mQueue, this);
        atndAPIClient.loadEventData();

        Resources res = getResources();
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.getIndeterminateDrawable().setColorFilter(res.getColor(R.color.header_text), android.graphics.PorterDuff.Mode.MULTIPLY);

        eventDao = EventDaoHelper.getEventDao(this);
    }

    @Override
    protected  void onStart() {
        super.onStart();
    }

    public void displayEventData() {
        EventListFragment fragment = (EventListFragment) getFragmentManager().findFragmentById(R.id.eventListFragment);
        fragment.displayEventData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent settingIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingIntent);
                return true;
            case R.id.action_regeneration:
                displayEventData();
                return true;
            case R.id.action_update:
                updateEventData();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateEventData()  {
        eventDao.deleteAll();

        ConnpassAPIClient connpassAPIClient = new ConnpassAPIClient(mQueue, this);
        connpassAPIClient.loadEventData();
        ZusaarAPIClient zussarAPIClient = new ZusaarAPIClient(mQueue, this);
        zussarAPIClient.loadEventData();
        AtndAPIClient atndAPIClient = new AtndAPIClient(mQueue, this);
        atndAPIClient.loadEventData();
    }

    public void finishAllDataLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "データの読み込みが完了しました", Toast.LENGTH_SHORT).show();
    }


    private boolean isFinishedSetting() {
        String atndNickName = PreferenceManager.getDefaultSharedPreferences(this).getString("atnd_nickname", "");
        String zussarNickName = PreferenceManager.getDefaultSharedPreferences(this).getString("zussar_nickname", "");
        String connpassNickName = PreferenceManager.getDefaultSharedPreferences(this).getString("connpass_nickname", "");

        if (atndNickName.isEmpty() && zussarNickName.isEmpty() && connpassNickName.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}


