package mendroid.easygo.view.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import mendroid.easygo.BaseActivity;
import mendroid.easygo.R;
import mendroid.easygo.model.City;
import mendroid.easygo.model.SearchEntity;
import mendroid.easygo.presenter.HomePresenterImpl;
import mendroid.easygo.utils.FuncUtils;
import mendroid.easygo.utils.ui.jazzyview.JazzyListView;
import mendroid.easygo.view.HomeView;
import mendroid.easygo.view.adapter.RecentSearchAdapter;


/**
 * @author Mendroid
 */
public class HomeActivity extends BaseActivity implements HomeView {

    HomePresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        presenter = new HomePresenterImpl();
        presenter.init(this, this);

        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (presenter == null) {
                presenter = new HomePresenterImpl();
                presenter.init(this, this);
            }
            presenter.getRecentSearches();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadCities(ArrayList<City> cities, AutoCompleteTextView autoCompleteTextView) {
        ArrayAdapter<City> adapter = new ArrayAdapter<City>(context, android.R.layout.simple_dropdown_item_1line, cities);
        autoCompleteTextView.setAdapter(adapter);
    }

    @Override
    public void loadRecentSearches(SearchEntity[] searchEntities) {
        RecentSearchAdapter adapter = new RecentSearchAdapter(HomeActivity.this, searchEntities);
        lvRecentSearch.setAdapter(adapter);
        if (searchEntities.length > 0) {
            lvRecentSearch.setVisibility(View.VISIBLE);
            tvNoRecentSearch.setVisibility(View.GONE);
        } else {
            lvRecentSearch.setVisibility(View.GONE);
            tvNoRecentSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadRouteResults(int id_tSearchEntity) {
    }


    TextInputLayout tilFrom, tilTo;
    AutoCompleteTextView atFrom, atTo;
    TextView tvSearchDate, tvNoRecentSearch;
    JazzyListView lvRecentSearch;

    @Override
    public void initUI() {
        try {
            tilFrom = (TextInputLayout) findViewById(R.id.tilFrom);
            tilTo = (TextInputLayout) findViewById(R.id.tilTo);
            atFrom = (AutoCompleteTextView) findViewById(R.id.atFrom);
            atTo = (AutoCompleteTextView) findViewById(R.id.atTo);

            atFrom.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        presenter.getHttpCities(charSequence.toString(), atFrom);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            atTo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        presenter.getHttpCities(charSequence.toString(), atTo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            atFrom.setThreshold(2);
            atTo.setThreshold(2);

            tvSearchDate = (TextView) findViewById(R.id.tvSearchDate);
            tvNoRecentSearch = (TextView) findViewById(R.id.tvNoRecentSearch);

            tvSearchDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeKeyboard();
                    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            try {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DATE, dayOfMonth);

                                String date = FuncUtils.getDateStringFromMS(calendar.getTime().getTime(), "dd MMM yyyy");
                                tvSearchDate.setText(date);
                            } catch (Exception e) {
                            }
                        }
                    };
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog dpDialog = new DatePickerDialog(context, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                    dpDialog.show();
                }
            });

            lvRecentSearch = (JazzyListView) findViewById(R.id.lvRecentSearch);

            findViewById(R.id.laySearch).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        closeKeyboard();
                        String sDate = tvSearchDate.getText().toString();
                        int xidOrigin = getXid(atFrom);
                        int xidDest = getXid(atTo);
                        if (xidOrigin >= 0 && xidDest >= 0 && !sDate.equals(getString(R.string.strHome_Date))) {
                            presenter.getHttpRouteInfo(xidOrigin, xidDest, sDate);
                        } else {
                            if (xidOrigin == 0) {
                                FuncUtils.showToast(context, getString(R.string.err_from));
                            } else if (xidDest == 0) {
                                FuncUtils.showToast(context, getString(R.string.err_to));
                            } else if (sDate.equals(getString(R.string.strHome_Date)))
                                FuncUtils.showToast(context, getString(R.string.err_date));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getXid(AutoCompleteTextView at) {
        try {
            ArrayAdapter<City> cityArrayAdapter = (ArrayAdapter<City>) at.getAdapter();
            String selection = at.getText().toString();
            for (int i = 0; i < cityArrayAdapter.getCount(); i++) {
                City city = cityArrayAdapter.getItem(i);
                if (city.getText().equals(selection))
                    return city.getXid();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void onBackPressed() {
        finishMyActivity(true);
    }
}

