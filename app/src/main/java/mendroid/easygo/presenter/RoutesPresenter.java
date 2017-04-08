package mendroid.easygo.presenter;

import mendroid.easygo.BaseFragmentPresenter;
import mendroid.easygo.view.RoutesView;

/**
 * @author Mendroid
 */

public interface RoutesPresenter extends BaseFragmentPresenter<RoutesView> {
    public void getRoutes(int id_tSearchEntity);
}
