package mendroid.easygo.view;


import java.util.ArrayList;

import mendroid.easygo.model.RouteRoot;

/**
 * @author Mendroid
 */

public interface RoutesView {
    public void loadRoutes(ArrayList<RouteRoot> routeRoots);

    public void onBookingSuccessful();

    public void initUI();
}
