package mendroid.easygo.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mendroid.easygo.data.DatabaseObject;
import mendroid.easygo.utils.FuncUtils;


/**
 * @author Mendroid
 */


public class BookEntity extends DatabaseObject<BookEntity> {
    @Override
    public void set_id(int _id) {
        super.set_id(_id);
        this._id = _id;
    }

    int _id;
    String ids_tRouteEntity;// comma separated
    long booked_ts;
    int cab_service;
    String org_address, dest_address;

    @Override
    public int get_id() {
        return _id;
    }


    public static String TABLENAME = "tBookEntity";
    public static String DBCREATE = "CREATE TABLE " + TABLENAME + " (\n" +
            "  `_id` integer PRIMARY KEY  AUTOINCREMENT,\n" +
            "  `ids_tRouteEntity` text,\n" +
            "  `booked_ts` text,\n" +
            "  `cab_service` integer,\n" +
            "  `org_address` text,\n" +
            "  `dest_address` text)";

    public static String DBDROP = "DROP TABLE IF EXISTS " + TABLENAME;
    public static String[] COLUMNS = new String[]{
            "_id",
            "ids_tRouteEntity",
            "booked_ts",
            "cab_service",
            "org_address",
            "dest_address"
    };


    @Override
    public String getTableName() {
        return TABLENAME;
    }

    @Override
    public ContentValues getContentValues(long... _ids) {
        ContentValues cv = new ContentValues();
        if (_id > 0) {
            cv.put("_id", _id);
        }
        cv.put("ids_tRouteEntity", FuncUtils.isEmpty(ids_tRouteEntity) ? "" : ids_tRouteEntity);
        cv.put("booked_ts", booked_ts);
        cv.put("cab_service", cab_service);
        cv.put("org_address", FuncUtils.isEmpty(org_address) ? "" : org_address);
        cv.put("dest_address", FuncUtils.isEmpty(dest_address) ? "" : dest_address);
        return cv;
    }

    @Override
    public BookEntity[] retrieveAll(SQLiteDatabase _db, long... _ids) {
        _db.beginTransactionNonExclusive();
        Cursor cur = _db.query(false, getTableName(), COLUMNS,
                null, null, null, null, "_id DESC", null);
        cur.moveToFirst();
        BookEntity[] ret = new BookEntity[cur.getCount()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = new BookEntity();
            ret[i].set_id(cur.getInt(0));
            ret[i].ids_tRouteEntity = cur.getString(1);
            ret[i].booked_ts = cur.getLong(2);
            ret[i].cab_service = cur.getInt(3);
            ret[i].org_address = cur.getString(4);
            ret[i].dest_address = cur.getString(5);
            cur.moveToNext();
        }
        cur.close();
        _db.endTransaction();
        return ret;
    }


    @Override
    public void retrieveSingle(SQLiteDatabase _db, long _id) {
        _db.beginTransactionNonExclusive();
        Cursor cur = _db.query(false, getTableName(), COLUMNS, "_id = ?",
                new String[]{String.valueOf(_id)}, null, null, null, null);
        cur.moveToFirst();
        if (cur.getCount() > 0) {
            this.set_id(cur.getInt(0));
            this.ids_tRouteEntity = cur.getString(1);
            this.booked_ts = cur.getLong(2);
            this.cab_service = cur.getInt(3);
            this.org_address = cur.getString(4);
            this.dest_address = cur.getString(5);
            cur.moveToNext();
        }
        cur.close();
        _db.endTransaction();
    }


}
