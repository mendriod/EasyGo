package mendroid.easygo.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mendroid
 */
public abstract class DatabaseObject<T> {
    public abstract String getTableName();

    protected int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int __id) {
        _id = __id;
    }


    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Date parseDate(String _timestamp) throws ParseException {
        return formatter.parse(_timestamp);
    }

    public String getTimestamp(Date _d) {
        return formatter.format(_d);
    }

    public boolean dbexists(SQLiteDatabase _db) {
        if (_id > 0) {
            _db.beginTransaction();
            Cursor cur = _db.query(false, getTableName(), new String[]{"_id"},
                    "_id=?",
                    new String[]{String.valueOf(_id)},
                    null, null, null, "1");
            boolean ret = false;
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                ret = true;
            }
            cur.close();
            _db.endTransaction();
            return ret;
        }
        return false;
    }

    public void dbsafeInsert(SQLiteDatabase _db, long... _ids) {
        if (dbexists(_db)) {
            dbupdate(_db, _ids);
        } else {
            dbinsert(_db, _ids);
        }
    }

    private void dbupdate(SQLiteDatabase _db, long... _ids) {
        //System.out.println("dbupdate " + getTableName() + ": " + getContentValues(_ids));
        _db.beginTransaction();
        _db.update(getTableName(), getContentValues(_ids), "_id = ?", new String[]{String.valueOf(_id)});
        _db.setTransactionSuccessful();
        _db.endTransaction();
    }

    public void dbdelete(SQLiteDatabase _db) {
        _db.beginTransaction();
        _db.delete(getTableName(), "_id=?", new String[]{String.valueOf(_id)});
        _db.endTransaction();
    }

    public void dbdeleteAll(SQLiteDatabase _db) {
        _db.beginTransaction();
        _db.delete(getTableName(), null, null);
        _db.endTransaction();
    }

    private void dbinsert(SQLiteDatabase _db, long... _ids) {
        //System.out.println("dbinsert " + getTableName() + ": " + getContentValues(_ids));
        _db.beginTransaction();
        _db.insert(getTableName(), null, getContentValues(_ids));
        _db.setTransactionSuccessful();
        _db.endTransaction();
    }

    public abstract ContentValues getContentValues(long... _ids);

    public abstract T[] retrieveAll(SQLiteDatabase _db, long... _ids);

    public abstract void retrieveSingle(SQLiteDatabase _db, long _id);
}
