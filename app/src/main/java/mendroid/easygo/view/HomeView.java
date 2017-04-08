package mendroid.easygo.view;


import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

import mendroid.easygo.model.City;
import mendroid.easygo.model.SearchEntity;

/**
 * @author Mendroid
 */

public interface HomeView {
    public void loadCities(ArrayList<City> cities, AutoCompleteTextView autoCompleteTextView);

    public void loadRecentSearches(SearchEntity[] searchEntities);

    public void loadRouteResults(int id_tSearchEntity);

    public void initUI();
}
