package xyz.yyagi.myeventlist.models;

/**
 * Created by yaginuma on 14/10/31.
 */
// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

import android.database.sqlite.SQLiteDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;


/**
 * DAO for table EVENT.
 */
public class EventDao extends AbstractDao<Event, Long> {

    public static final String TABLENAME = "EVENT";

    /**
     * Properties of entity Event.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Detail = new Property(2, String.class, "detail", false, "DETAIL");
        public final static Property Url = new Property(3, String.class, "url", false, "URL");
        public final static Property Start = new Property(4, int.class, "start", false, "START");
        public final static Property End = new Property(5, int.class, "end", false, "END");
    };


    public EventDao(DaoConfig config) {
        super(config);
    }

    public EventDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EVENT' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TITLE' TEXT NOT NULL ," + // 1: title
                "'DETAIL' TEXT NOT NULL ," + // 2: detail
                "'URL' TEXT NOT NULL ," + // 3: url
                "'START' INTEGER NOT NULL ," + // 4: start
                "'END' INTEGER NOT NULL );"); // 5: end
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EVENT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Event entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getTitle());
        stmt.bindString(3, entity.getDetail());
        stmt.bindString(4, entity.getUrl());
        stmt.bindLong(5, entity.getStart());
        stmt.bindLong(6, entity.getEnd());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public Event readEntity(Cursor cursor, int offset) {
        Event entity = new Event( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1), // title
                cursor.getString(offset + 2), // detail
                cursor.getString(offset + 3), // url
                cursor.getInt(offset + 4), // start
                cursor.getInt(offset + 5) // end
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Event entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.getString(offset + 1));
        entity.setDetail(cursor.getString(offset + 2));
        entity.setUrl(cursor.getString(offset + 3));
        entity.setStart(cursor.getInt(offset + 4));
        entity.setEnd(cursor.getInt(offset + 5));
    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Event entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(Event entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}

