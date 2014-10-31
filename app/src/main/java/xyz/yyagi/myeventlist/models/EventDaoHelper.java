package xyz.yyagi.myeventlist.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by yaginuma on 14/10/31.
 */
public class EventDaoHelper {
    private static EventDao eventDao;
    private static DaoMaster.DevOpenHelper helper;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static SQLiteDatabase db;

    public static EventDao getEventDao(Context context) {
        if (eventDao == null) {
            helper = new DaoMaster.DevOpenHelper(context, "myeventlist-db", null);
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            eventDao = daoSession.getEventDao();
        }
        return eventDao;
    }
}

