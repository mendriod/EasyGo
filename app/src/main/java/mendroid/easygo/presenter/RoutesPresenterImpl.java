package mendroid.easygo.presenter;

import java.util.ArrayList;

import mendroid.easygo.BaseActivity;
import mendroid.easygo.model.RouteEntity;
import mendroid.easygo.model.RouteRoot;
import mendroid.easygo.view.RoutesView;


/**
 * @author Mendroid
 */

public class RoutesPresenterImpl implements RoutesPresenter {
    private RoutesView view;

    private BaseActivity baseActivity;

    @Override
    public void init(RoutesView view, BaseActivity baseActivity) {
        this.view = view;
        this.baseActivity = baseActivity;
    }


    @Override
    public void getRoutes(int id_tSearchEntity) {
        try {
            ArrayList<RouteRoot> routeRoots = new ArrayList<>();

            RouteEntity routeEntity = new RouteEntity();
            RouteEntity[] routeEntities = routeEntity.retrieveAll(baseActivity.dbManager.getReadableDatabase(), id_tSearchEntity);

            if (routeEntities.length > 0) {
                int totalSteps = routeEntities[0].getStep();

                RouteEntity[] routeEntitiesStep1 = routeEntity.retrieveSpecialCarriers(baseActivity.dbManager.getReadableDatabase(),
                        1, id_tSearchEntity);
                for (RouteEntity step1Entity : routeEntitiesStep1) {
                    RouteEntity[] routeEntitiesStep2 = routeEntity.retrieveSpecialCarriers(baseActivity.dbManager.getReadableDatabase(),
                            2, id_tSearchEntity);
                    for (RouteEntity step2Entity : routeEntitiesStep2) {
                        ArrayList<RouteEntity> routeEntityArrayList = new ArrayList<>();
                        if (step1Entity.getMode().toUpperCase().equals("FLIGHT") &&
                                step2Entity.getMode().toUpperCase().equals("FLIGHT")) {
                            if (step1Entity.getcName().equals(step2Entity.getcName())) {
                                routeEntityArrayList.add(step1Entity);
                                routeEntityArrayList.add(step2Entity);

                                RouteRoot routeRoot = new RouteRoot();
                                routeRoot.setRouteEntities(routeEntityArrayList);

                                routeRoots.add(routeRoot);
                            }
                        } else {
                            routeEntityArrayList.add(step1Entity);
                            routeEntityArrayList.add(step2Entity);

                            RouteRoot routeRoot = new RouteRoot();
                            routeRoot.setRouteEntities(routeEntityArrayList);

                            routeRoots.add(routeRoot);
                        }
                    }
                }

                view.loadRoutes(routeRoots);
            } else {
                view.loadRoutes(new ArrayList<RouteRoot>());
            }

        } catch (Exception e) {
            e.printStackTrace();
            view.loadRoutes(new ArrayList<RouteRoot>());
        }
    }
}
