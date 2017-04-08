package mendroid.easygo.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mendroid.easygo.BaseActivity;
import mendroid.easygo.R;
import mendroid.easygo.model.RouteEntity;
import mendroid.easygo.model.RouteRoot;
import mendroid.easygo.model.SearchEntity;

/**
 * @author Mendroid
 */

public class RoutesAdapter {
    BaseActivity baseActivity;
    ArrayList<RouteRoot> routeRoots;

    public RoutesAdapter(BaseActivity baseActivity, LinearLayout linearLayout, ArrayList<RouteRoot> routeRoots) {
        this.baseActivity = baseActivity;
        this.routeRoots = routeRoots;

        linearLayout.removeAllViews();
        for (int i = 0; i < routeRoots.size(); i++) {
            linearLayout.addView(getView(i));
        }
    }

    public View getView(final int position) {
        View convertView = LayoutInflater.from(baseActivity).inflate(R.layout.view_route_root, null);
        final LinearLayout linearLayoutDetails = (LinearLayout) convertView.findViewById(R.id.linearLayout_routesCustom_Details);
        TextView txtFromTo = (TextView) convertView.findViewById(R.id.txt_routesCustom_FromTo);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.txt_routesCustom_Price);
        TextView txtBook = (TextView) convertView.findViewById(R.id.txt_routesCustom_Book);
        final TextView txtViewExtra = (TextView) convertView.findViewById(R.id.txt_routesCustom_View);

        linearLayoutDetails.setVisibility(View.GONE);
        txtViewExtra.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arw_right, 0);
        txtViewExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutDetails.getVisibility() == View.GONE) {
                    expand(linearLayoutDetails);
                    txtViewExtra.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arw_down2, 0);
                } else {
                    collapse(linearLayoutDetails);
                    txtViewExtra.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arw_right, 0);
                }
            }
        });
        txtBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ArrayList<RouteEntity> routeEntities = routeRoots.get(position).getRouteEntities();

        int price = 0;
        for (int i = 0; i < routeEntities.size(); i++) {
            price = price + routeEntities.get(i).getPrice();
            if (routeEntities.get(i).getMode().equalsIgnoreCase("Car") ||
                    routeEntities.get(i).getMode().equalsIgnoreCase("Train")) {
                txtPrice.setVisibility(View.GONE);
                txtBook.setVisibility(View.GONE);
            }
        }

        SearchEntity searchEntity = new SearchEntity();
        searchEntity.retrieveSingle(baseActivity.dbManager.getReadableDatabase(), routeEntities.get(0).getId_tSearchEntity());

        txtFromTo.setText(String.format(baseActivity.getString(R.string.routes_from_to), searchEntity.getTxt_origin(), searchEntity.getTxt_dest()));
        txtPrice.setText(baseActivity.getString(R.string.rupee_sym) + " " + price);

        linearLayoutDetails.removeAllViews();
        for (int i = 0; i < routeEntities.size(); i++) {
            getVehicleDetails(routeEntities.get(i));
        }

        return convertView;
    }

    public View getVehicleDetails(final RouteEntity routeEntity) {
        View convertView = LayoutInflater.from(baseActivity).inflate(R.layout.view_route_steps, null);
        TextView txtFromTo = (TextView) convertView.findViewById(R.id.txt_routesCustom_FromTo1);
        TextView txtDeparture = (TextView) convertView.findViewById(R.id.txt_routesCustom_DT);
        TextView txtArrival = (TextView) convertView.findViewById(R.id.txt_routesCustom_AT);
        final TextView txtDistance = (TextView) convertView.findViewById(R.id.txt_routesCustom_Distance);
        final TextView txtHrs = (TextView) convertView.findViewById(R.id.txt_routesCustom_Hrs);
        ImageView imgMode = (ImageView) convertView.findViewById(R.id.img_routesCustom_Mode);

        txtFromTo.setText(String.format(baseActivity.getString(R.string.routes_from_to), routeEntity.getTxt_origin(), routeEntity.getTxt_dest()));
        txtDeparture.setText(routeEntity.getDepTime());
        txtArrival.setText(routeEntity.getArrTime());

        txtDistance.setText(routeEntity.getDistance() + " KM");

        String hours = "";
        try {
            int travelMinutes = routeEntity.getTravelMinutes();
            if (travelMinutes > 0) {
                if (travelMinutes == 60) {

                }
            } else
                hours = "0 H";
        } catch (Exception e) {

        }
        txtHrs.setText("");

        return convertView;
    }

    /**
     * Expand Animation
     *
     * @param v - view
     */
    public void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    /**
     * Collapse Animation
     *
     * @param v - view
     */
    public void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
