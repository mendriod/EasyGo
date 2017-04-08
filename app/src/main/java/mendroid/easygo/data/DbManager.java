package mendroid.easygo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mendroid.easygo.model.SearchEntity;

/**
 * @author Mendroid
 */

public class DbManager extends SQLiteOpenHelper {
    static final String dbName = "easygo.db";

    public DbManager(Context _context) {
        super(_context, dbName, null, 01);
    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(SearchEntity.DBCREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        _db.execSQL(SearchEntity.DBDROP);
        onCreate(_db);
    }


    public void wipeOffData(Context context) {
        SQLiteDatabase db = getWritableDatabase();
        new SearchEntity().dbdeleteAll(db);
        db.close();
    }

    public SQLiteDatabase getDatabase() {
        return this.getWritableDatabase();
    }
}