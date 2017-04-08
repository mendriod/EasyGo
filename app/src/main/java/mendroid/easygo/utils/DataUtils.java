package mendroid.easygo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * All the data preferences stored here.
 *
 * @author Mendroid
 */
public class DataUtils {

    SharedPreferences sharedPreferences;

    static DataUtils currentInstance;

    Context dContext;

    public DataUtils(Context context) {
        dContext = context;
    }

    public static DataUtils getInstance(Context context) {
        if (currentInstance == null)
            currentInstance = new DataUtils(context);
        return currentInstance;
    }

    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = dContext.getSharedPreferences(dContext.getPackageName(), Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

}
