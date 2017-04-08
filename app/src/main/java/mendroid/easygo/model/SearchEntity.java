package mendroid.easygo.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mendroid.easygo.data.DatabaseObject;
import mendroid.easygo.utils.FuncUtils;


/**
 * @author Mendroid
 */


public class SearchEntity extends DatabaseObject<SearchEntity> {
    @Override
    public void set_id(int _id) {
        super.set_id(_id);
        this._id = _id;
    }

    public int getXid_origin() {
        return xid_origin;
    }

    public void setXid_origin(int xid_origin) {
        this.xid_origin = xid_origin;
    }

    public int getXid_dest() {
        return xid_dest;
    }

    public void setXid_dest(int xid_dest) {
        this.xid_dest = xid_dest;
    }

    public long getDownloaded_ts() {
        return downloaded_ts;
    }

    public void setDownloaded_ts(long downloaded_ts) {
        this.downloaded_ts = downloaded_ts;
    }

    public String getTxt_origin() {
        return txt_origin;
    }

    public void setTxt_origin(String txt_origin) {
        this.txt_origin = txt_origin;
    }

    public String getTxt_dest() {
        return txt_dest;
    }

    public void setTxt_dest(String txt_dest) {
        this.txt_dest = txt_dest;
    }

    public String getDate_search() {
        return date_search;
    }

    public void setDate_search(String date_search) {
        this.date_search = date_search;
    }

    public String getDownloaded_result() {
        return downloaded_result;
    }

    public void setDownloaded_result(String downloaded_result) {
        this.downloaded_result = downloaded_result;
    }

    int _id, xid_origin, xid_dest, pax;
    long downloaded_ts;
    String txt_origin, txt_dest, date_search, downloaded_result;

    @Override
    public int get_id() {
        return _id;
    }


    public static String TABLENAME = "tSearchEntity";
    public static String DBCREATE = "CREATE TABLE " + TABLENAME + " (\n" +
            "  `_id` integer PRIMARY KEY  AUTOINCREMENT,\n" +
            "  `xid_origin` integer,\n" +
            "  `txt_origin` text,\n" +
            "  `xid_dest` integer,\n" +
            "  `txt_dest` text,\n" +
            "  `date_search` text,\n" +
            "  `downloaded_result` text,\n" +
            "  `downloaded_ts` text,\n" +
            "  `pax` integer)";

    public static String DBDROP = "DROP TABLE IF EXISTS " + TABLENAME;
    public static String[] COLUMNS = new String[]{
            "_id",
            "xid_origin",
            "txt_origin",
            "xid_dest",
            "txt_dest",
            "date_search",
            "downloaded_result",
            "downloaded_ts",
            "pax"
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
        cv.put("xid_origin", xid_origin);
        cv.put("txt_origin", FuncUtils.isEmpty(txt_origin) ? "" : txt_origin);
        cv.put("xid_dest", xid_dest);
        cv.put("txt_dest", FuncUtils.isEmpty(txt_dest) ? "" : txt_dest);
        cv.put("date_search", FuncUtils.isEmpty(date_search) ? "" : date_search);
        cv.put("downloaded_result", FuncUtils.isEmpty(downloaded_result) ? "" : downloaded_result);
        cv.put("downloaded_ts", downloaded_ts);
        cv.put("pax", pax);
        return cv;
    }

    @Override
    public SearchEntity[] retrieveAll(SQLiteDatabase _db, long... _ids) {
        _db.beginTransactionNonExclusive();
        Cursor cur = _db.query(false, getTableName(), COLUMNS,
                null, null, null, null, "_id DESC", null);
        cur.moveToFirst();
        SearchEntity[] ret = new SearchEntity[cur.getCount()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = new SearchEntity();
            ret[i].set_id(cur.getInt(0));
            ret[i].xid_origin = cur.getInt(1);
            ret[i].txt_origin = cur.getString(2);
            ret[i].xid_dest = cur.getInt(3);
            ret[i].txt_dest = cur.getString(4);
            ret[i].date_search = cur.getString(5);
            ret[i].downloaded_result = cur.getString(6);
            ret[i].downloaded_ts = cur.getLong(7);
            ret[i].pax = cur.getInt(8);
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
            this.xid_origin = cur.getInt(1);
            this.txt_origin = cur.getString(2);
            this.xid_dest = cur.getInt(3);
            this.txt_dest = cur.getString(4);
            this.date_search = cur.getString(5);
            this.downloaded_result = cur.getString(6);
            this.downloaded_ts = cur.getLong(7);
            this.pax = cur.getInt(8);
            cur.moveToNext();
        }
        cur.close();
        _db.endTransaction();
    }


}
