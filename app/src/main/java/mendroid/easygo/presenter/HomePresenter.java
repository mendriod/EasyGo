package mendroid.easygo.presenter;

import android.widget.AutoCompleteTextView;

import mendroid.easygo.BaseFragmentPresenter;
import mendroid.easygo.view.HomeView;

/**
 * @author Mendroid
 */

public interface HomePresenter extends BaseFragmentPresenter<HomeView> {
    public void getHttpCities(String ip, AutoCompleteTextView autoCompleteTextView);

    public void getHttpRouteInfo(int xid_origin, int xid_destination, String date);

    public void getRecentSearches();

}
