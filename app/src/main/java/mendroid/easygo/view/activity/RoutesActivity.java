package mendroid.easygo.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mendroid.easygo.BaseActivity;
import mendroid.easygo.R;
import mendroid.easygo.model.RouteRoot;
import mendroid.easygo.presenter.RoutesPresenterImpl;
import mendroid.easygo.view.RoutesView;
import mendroid.easygo.view.adapter.RoutesAdapter;


/**
 * @author Mendroid
 */
public class RoutesActivity extends BaseActivity implements RoutesView {

    RoutesPresenterImpl presenter;

    int id_tSearchEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_routes);

        id_tSearchEntity = getIntent().getExtras().getInt("id_tSearchEntity");

        presenter = new RoutesPresenterImpl();
        presenter.init(this, this);

        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    LinearLayout layCarriers;
    TextView tvNoRouteFound;

    @Override
    public void loadRoutes(ArrayList<RouteRoot> routeRoots) {
        if (routeRoots.size() > 0) {
            layCarriers.setVisibility(View.VISIBLE);
            tvNoRouteFound.setVisibility(View.GONE);

            new RoutesAdapter(RoutesActivity.this, layCarriers, routeRoots);
        } else {
            layCarriers.setVisibility(View.GONE);
            tvNoRouteFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBookingSuccessful() {

    }

    @Override
    public void initUI() {
        try {
            layCarriers = (LinearLayout) findViewById(R.id.layCarriers);
            tvNoRouteFound = (TextView) findViewById(R.id.tvNoRouteFound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finishMyActivity(false);
    }
}

