package mendroid.easygo.view.activity;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import mendroid.easygo.BaseActivity;
import mendroid.easygo.R;


/**
 * @author Mendroid
 */
public class SplashActivity extends BaseActivity {
    Context context;
    TextView tvAppVersion;
    ProgressBar progressBar;

    Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        context = this;

        tvAppVersion = (TextView) findViewById(R.id.txt_SplashPage_AppVersion);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_SplashPage);

        try {
            String versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
            tvAppVersion.setText(getString(R.string.strAppVersion) + " " + versionCode);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        activatingTimer(2250);

        final ImageView animImageView = (ImageView) findViewById(R.id.img_SplashPage_Logo);
        animImageView.setVisibility(View.INVISIBLE);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            // TODO
            public void run() {
                 /*also just stop the timer thread,*/
                 /*otherwise, you may receive a crash report*/
                timer.cancel();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        animImageView.setVisibility(View.VISIBLE);
                        animImageView.setBackgroundResource(R.drawable.splash_animation);
                        animImageView.post(new Runnable() {
                            @Override
                            public void run() {
                                AnimationDrawable frameAnimation =
                                        (AnimationDrawable) animImageView.getBackground();
                                frameAnimation.start();
                            }
                        });
                    }
                });

            }
        }, 200);
    }

    public void activatingTimer(long milliseconds) {
        t = new Timer();
        t.schedule(new TimerTask() {
            // TODO
            public void run() {
                 /*also just stop the timer thread,*/
                 /*otherwise, you may receive a crash report*/
                t.cancel();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gotoLandingPage(true);
                    }
                });

            }
        }, milliseconds);
        /* after 2 second (2000 milliseconds), the task will be active. */
    }
}
