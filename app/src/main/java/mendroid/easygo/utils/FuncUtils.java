package mendroid.easygo.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import mendroid.easygo.R;


/**
 * @author Mendroid
 */
@SuppressLint("NewApi")
public class FuncUtils {


    /**
     * @param context
     * @param msg     showing the alert dialog which can be utilised from any part of the app
     */
    public static void showNotifyDialog(Context context, String msg, final OkCommunicator communicator) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle(context.getString(R.string.app_name) + " ALERT");
        alertDialogBuilder.setMessage(msg).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (communicator != null)
                            communicator.onOkClicked();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * @param context
     * @param msg     showing the toast message which can be utilised from any part of the app
     */
    public static void showToast(Context context, String msg) {
        try {
            if (msg != null && msg.trim().length() > 0)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param context
     * @param title
     * @param message
     * @param funcUtilsCommunicator showing alert dialog from anywhere of the app with interface instance to callback---> YES Highlighted
     */
    public static void showYesOrNoDialog(Context context, String title, String message,
                                         final YesOrNoCommunicator funcUtilsCommunicator) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        funcUtilsCommunicator.onYesClicked();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                funcUtilsCommunicator.onNoClicked();
                dialog.cancel();
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }


    public interface YesOrNoCommunicator {
        public void onYesClicked();

        public void onNoClicked();
    }

    public interface OkCommunicator {
        public void onOkClicked();
    }


    Context context;

    public FuncUtils(Context ctx) {
        context = ctx;
    }

    static FuncUtils currentInstance;

    public static FuncUtils getInstance(Context cnContext) {
        if (currentInstance == null) {
            currentInstance = new FuncUtils(cnContext);
        }
        return currentInstance;
    }

    public static long getMSfromDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            return timeInMilliseconds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    public static Date getDateFromString(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date mDate = sdf.parse(date);
            return mDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getDateStringFromMS(long inputMS, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date mDate = new Date(inputMS);
            return sdf.format(mDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputMS + "";
    }


    public static String readFromFile(Context context, String fileName) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }


    /* Checking whether Internet avails or not */
    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnectionAvailable = (activeNetworkInfo != null);
        return isConnectionAvailable;
    }

    public static boolean isEmpty(String input) {
        try {
            if (input != null && input.trim().length() > 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}