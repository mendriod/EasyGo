package mendroid.easygo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import mendroid.easygo.data.DbManager;
import mendroid.easygo.utils.FuncUtils;
import mendroid.easygo.utils.ui.MyProgressDialog;
import mendroid.easygo.view.activity.HomeActivity;


/**
 * @author Mendroid
 */

/*BaseActivity for all the activities in the application by extending from this activity
        to make use of methods and other assets defined here.*/

public abstract class BaseActivity extends AppCompatActivity {

    MyProgressDialog progressDialog;

    int progressInstances = 0;

    public Context context = this;

    public DbManager dbManager;
    public FuncUtils funcUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DbManager(context);
        funcUtils = FuncUtils.getInstance(context);
        closeKeyboard();
        initProgressDialog();
    }

    public void closeKeyboard() {
        try {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {

        }
    }


    /**
     * Used to initiate the progress dialog from onCreate
     */
    private void initProgressDialog() {
        try {
            if (progressDialog == null) {
                progressDialog = new MyProgressDialog(this);
                progressDialog.setCancelable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to show the progress dialog if its not showing
     * 'progressInstances' will be having the number of progress dialog showing occurrences.
     * It will be incremented while showing and decremented when a progress dialog completes its necessity
     */
    public void showProgressDialog() {
        try {
            if (progressDialog == null) {
                initProgressDialog();
            }
            progressInstances = progressInstances + 1;
            if (!progressDialog.isShowing())
                progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to dismiss the progress dialog if its showing
     * 'progressInstances' will be having the number of progress dialog showing occurrences.
     * It will be incremented while showing and decremented when a progress dialog completes its necessity
     */
    public void dismissProgressDialog() {
        try {
            progressInstances = progressInstances - 1;
            if (progressDialog != null && progressDialog.isShowing() && progressInstances == 0) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * @param isConfirmationRequired - Demotes whether user confirmation required to exit from the activity or not
     */
    public void finishMyActivity(boolean isConfirmationRequired) {
        if (isConfirmationRequired) {
            FuncUtils.showYesOrNoDialog(this, getString(R.string.app_name) + " Notice!", "Are you sure that you want to exit?", new FuncUtils.YesOrNoCommunicator() {
                @Override
                public void onYesClicked() {
                    finish();
                }

                @Override
                public void onNoClicked() {

                }

            });
        } else {
            finish();
        }
    }

    public void gotoLandingPage(boolean shouldFinish) {
        Intent in = new Intent(this, HomeActivity.class);
        startActivity(in);
        if (shouldFinish)
            finish();
    }
}
