package mendroid.easygo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * @author Mendroid
 */

/*Application Singleton with the capability to send crash report*/
@SuppressLint("NewApi")
@ReportsCrashes(formKey = "", // will not be used
        mailTo = "umayaeswaran@gmail.com", mode = ReportingInteractionMode.TOAST, resToastText = R.string.err_uncaught_error)
public class EasyGoApplication extends Application {

    public EasyGoApplication() {
    }

    @Override
    public void onCreate() {

        super.onCreate();
        ACRA.init(this);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy poli = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(poli);
        }

    }
}