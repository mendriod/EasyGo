package mendroid.easygo.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mendroid.easygo.data.DatabaseObject;
import mendroid.easygo.utils.FuncUtils;


/**
 * @author Mendroid
 */


public class RouteEntity extends DatabaseObject<RouteEntity> {
    @Override
    public void set_id(int _id) {
        super.set_id(_id);
        this._id = _id;
    }

    int _id, id_tSearchEntity, step, xid_origin, xid_dest;
    String mode, cName, code, depTime, arrTime, code_origin, code_dest;
    String carrierJson;
    int travelMinutes, price, distance;
    String txt_origin, txt_dest;

    @Override
    public int get_id() {
        return _id;
    }


    public static String TABLENAME = "tRouteEntity";
    public static String DBCREATE = "CREATE TABLE " + TABLENAME + " (\n" +
            "  `_id` integer PRIMARY KEY  AUTOINCREMENT,\n" +
            "  `id_tSearchEntity` integer,\n" +
            "  `xid_origin` integer,\n" +
            "  `txt_origin` text,\n" +
            "  `xid_dest` integer,\n" +
            "  `txt_dest` text,\n" +
            "  `step` integer,\n" +
            "  `mode` text,\n" +
            "  `cName` text,\n" +
            "  `code` text,\n" +
            "  `depTime` text,\n" +
            "  `arrTime` text,\n" +
            "  `code_origin` text,\n" +
            "  `code_dest` text,\n" +
            "  `carrierJson` text,\n" +
            "  `travelMinutes` integer,\n" +
            "  `price` integer,\n" +
            "  `distance` integer)";

    public static String DBDROP = "DROP TABLE IF EXISTS " + TABLENAME;
    public static String[] COLUMNS = new String[]{
            "_id",
            "id_tSearchEntity",
            "xid_origin",
            "txt_origin",
            "xid_dest",
            "txt_dest",
            "step",
            "mode",
            "cName",
            "code",
            "depTime",
            "arrTime",
            "code_origin",
            "code_dest",
            "carrierJson",
            "travelMinutes",
            "price",
            "distance"
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
        cv.put("id_tSearchEntity", id_tSearchEntity);
        cv.put("xid_origin", xid_origin);
        cv.put("txt_origin", FuncUtils.isEmpty(txt_origin) ? "" : txt_origin);
        cv.put("xid_dest", xid_dest);
        cv.put("txt_dest", FuncUtils.isEmpty(txt_dest) ? "" : txt_dest);
        cv.put("step", step);
        cv.put("mode", FuncUtils.isEmpty(mode) ? "" : mode);
        cv.put("cName", FuncUtils.isEmpty(cName) ? "" : cName);
        cv.put("code", FuncUtils.isEmpty(code) ? "" : code);
        cv.put("depTime", FuncUtils.isEmpty(depTime) ? "" : depTime);
        cv.put("arrTime", FuncUtils.isEmpty(arrTime) ? "" : arrTime);
        cv.put("code_origin", FuncUtils.isEmpty(code_origin) ? "" : code_origin);
        cv.put("code_dest", FuncUtils.isEmpty(code_dest) ? "" : mode);
        cv.put("carrierJson", FuncUtils.isEmpty(carrierJson) ? "" : carrierJson);
        cv.put("travelMinutes", travelMinutes);
        cv.put("price", price);
        cv.put("distance", distance);
        return cv;
    }

    @Override
    public RouteEntity[] retrieveAll(SQLiteDatabase _db, long... _ids) {
        _db.beginTransactionNonExclusive();
        Cursor cur = _db.query(false, getTableName(), COLUMNS,
                null, null, null, null, "_id DESC", null);
        cur.moveToFirst();
        RouteEntity[] ret = new RouteEntity[cur.getCount()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = new RouteEntity();
            ret[i].set_id(cur.getInt(0));
            ret[i].id_tSearchEntity = cur.getInt(1);
            ret[i].xid_origin = cur.getInt(2);
            ret[i].txt_origin = cur.getString(3);
            ret[i].xid_dest = cur.getInt(4);
            ret[i].txt_dest = cur.getString(5);
            ret[i].step = cur.getInt(6);
            ret[i].mode = cur.getString(7);
            ret[i].cName = cur.getString(8);
            ret[i].code = cur.getString(9);
            ret[i].depTime = cur.getString(10);
            ret[i].arrTime = cur.getString(11);
            ret[i].code_origin = cur.getString(12);
            ret[i].code_dest = cur.getString(13);
            ret[i].carrierJson = cur.getString(14);
            ret[i].travelMinutes = cur.getInt(15);
            ret[i].price = cur.getInt(16);
            ret[i].distance = cur.getInt(17);
            cur.moveToNext();
        }
        cur.close();
        _db.endTransaction();
        return ret;
    }

    public RouteEntity[] retrieveSpecialFlightCarriers(SQLiteDatabase _db, int step, String cName, int id_tSearchEntity) {
        _db.beginTransactionNonExclusive();
        String query = "SELECT * FROM " + getTableName() + " WHERE step=" + step + " " +
                "AND cName='" + cName + "' " +
                "AND id_tSearchEntity=" + id_tSearchEntity;
        Cursor cur = _db.rawQuery(query, null);
        cur.moveToFirst();
        RouteEntity[] ret = new RouteEntity[cur.getCount()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = new RouteEntity();
            ret[i].set_id(cur.getInt(0));
            ret[i].id_tSearchEntity = cur.getInt(1);
            ret[i].xid_origin = cur.getInt(2);
            ret[i].txt_origin = cur.getString(3);
            ret[i].xid_dest = cur.getInt(4);
            ret[i].txt_dest = cur.getString(5);
            ret[i].step = cur.getInt(6);
            ret[i].mode = cur.getString(7);
            ret[i].cName = cur.getString(8);
            ret[i].code = cur.getString(9);
            ret[i].depTime = cur.getString(10);
            ret[i].arrTime = cur.getString(11);
            ret[i].code_origin = cur.getString(12);
            ret[i].code_dest = cur.getString(13);
            ret[i].carrierJson = cur.getString(14);
            ret[i].travelMinutes = cur.getInt(15);
            ret[i].price = cur.getInt(16);
            ret[i].distance = cur.getInt(17);
            cur.moveToNext();
        }
        cur.close();
        _db.endTransaction();
        return ret;
    }

    public RouteEntity[] retrieveSpecialCarriers(SQLiteDatabase _db, int step, int id_tSearchEntity) {
        _db.beginTransactionNonExclusive();
        String query = "SELECT * FROM " + getTableName() + " WHERE step=" + step + " " +
                "AND id_tSearchEntity=" + id_tSearchEntity;
        Cursor cur = _db.rawQuery(query, null);
        cur.moveToFirst();
        RouteEntity[] ret = new RouteEntity[cur.getCount()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = new RouteEntity();
            ret[i].set_id(cur.getInt(0));
            ret[i].id_tSearchEntity = cur.getInt(1);
            ret[i].xid_origin = cur.getInt(2);
            ret[i].txt_origin = cur.getString(3);
            ret[i].xid_dest = cur.getInt(4);
            ret[i].txt_dest = cur.getString(5);
            ret[i].step = cur.getInt(6);
            ret[i].mode = cur.getString(7);
            ret[i].cName = cur.getString(8);
            ret[i].code = cur.getString(9);
            ret[i].depTime = cur.getString(10);
            ret[i].arrTime = cur.getString(11);
            ret[i].code_origin = cur.getString(12);
            ret[i].code_dest = cur.getString(13);
            ret[i].carrierJson = cur.getString(14);
            ret[i].travelMinutes = cur.getInt(15);
            ret[i].price = cur.getInt(16);
            ret[i].distance = cur.getInt(17);
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
            this.id_tSearchEntity = cur.getInt(1);
            this.xid_origin = cur.getInt(2);
            this.txt_origin = cur.getString(3);
            this.xid_dest = cur.getInt(4);
            this.txt_dest = cur.getString(5);
            this.step = cur.getInt(6);
            this.mode = cur.getString(7);
            this.cName = cur.getString(8);
            this.code = cur.getString(9);
            this.depTime = cur.getString(10);
            this.arrTime = cur.getString(11);
            this.code_origin = cur.getString(12);
            this.code_dest = cur.getString(13);
            this.carrierJson = cur.getString(14);
            this.travelMinutes = cur.getInt(15);
            this.price = cur.getInt(16);
            this.distance = cur.getInt(17);
            cur.moveToNext();
        }
        cur.close();
        _db.endTransaction();
    }


}
