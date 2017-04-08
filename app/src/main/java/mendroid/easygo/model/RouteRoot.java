package mendroid.easygo.model;

import java.util.ArrayList;

/**
 * Created by user on 08-Apr-17.
 */

public class RouteRoot {
    public ArrayList<RouteEntity> getRouteEntities() {
        return routeEntities;
    }

    public void setRouteEntities(ArrayList<RouteEntity> routeEntities) {
        this.routeEntities = routeEntities;
    }

    private ArrayList<RouteEntity> routeEntities;
}
