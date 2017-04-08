package mendroid.easygo.presenter;

import android.util.Log;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import mendroid.easygo.BaseActivity;
import mendroid.easygo.R;
import mendroid.easygo.model.City;
import mendroid.easygo.model.RouteEntity;
import mendroid.easygo.model.SearchEntity;
import mendroid.easygo.utils.FuncUtils;
import mendroid.easygo.utils.HttpGetTask;
import mendroid.easygo.view.HomeView;


/**
 * @author Mendroid
 */

public class HomePresenterImpl implements HomePresenter {
    private HomeView view;

    private BaseActivity baseActivity;

    @Override
    public void init(HomeView view, BaseActivity baseActivity) {
        this.view = view;
        this.baseActivity = baseActivity;
    }


    @Override
    public void getHttpCities(String ip, final AutoCompleteTextView autoCompleteTextView) {
        new HttpGetTask(baseActivity, new HttpGetTask.GetDataDownloadListener() {
            @Override
            public void dataDownloadedSuccessfully(String data) {
                try {
                    ArrayList<City> cities = new ArrayList<City>();
                    JSONArray jsRoot = new JSONArray(data);
                    for (int i = 0; i < jsRoot.length(); i++) {
                        JSONObject jsCity = jsRoot.getJSONObject(i);
                        City city = new City(jsCity.getInt("xid"), jsCity.getString("text"));
                        cities.add(city);
                    }
                    view.loadCities(cities, autoCompleteTextView);
                } catch (Exception e) {
                    e.printStackTrace();
//                    FuncUtils.showNotifyDialog(baseActivity, baseActivity.getString(R.string.err_api_json_malformed), new FuncUtils.OkCommunicator() {
//                        @Override
//                        public void onOkClicked() {
                    view.loadCities(new ArrayList<City>(), autoCompleteTextView);
//                        }
//                    });
                }
            }

            @Override
            public void dataDownloadFailed(String reason) {
//                FuncUtils.showNotifyDialog(baseActivity, (reason != null && reason.trim().length() > 0) ? reason : baseActivity.getString(R.string.err_api_unknown), new FuncUtils.OkCommunicator() {
//                    @Override
//                    public void onOkClicked() {
                view.loadCities(new ArrayList<City>(), autoCompleteTextView);
//                    }
//                });

            }
        }).execute(String.format(baseActivity.getString(R.string.url_get_cities), ip));
    }

    @Override
    public void getHttpRouteInfo(final int xid_origin, final int xid_destination, final String date) {
        baseActivity.showProgressDialog();
        new HttpGetTask(baseActivity, new HttpGetTask.GetDataDownloadListener() {
            @Override
            public void dataDownloadedSuccessfully(String data) {
                baseActivity.dismissProgressDialog();
                try {
                    Log.d("Home Presenter", data);
                    JSONObject jsRoot = new JSONObject(data).getJSONObject("data");

                    SearchEntity searchEntity = new SearchEntity();
                    searchEntity.setDate_search(date);
                    searchEntity.setDownloaded_result(data);
                    searchEntity.setDownloaded_ts(System.currentTimeMillis());
                    searchEntity.setTxt_dest(jsRoot.getJSONObject("destination").getString("name"));
                    searchEntity.setTxt_origin(jsRoot.getJSONObject("origin").getString("name"));
                    searchEntity.setXid_dest(xid_destination);
                    searchEntity.setXid_origin(xid_origin);

                    searchEntity.dbsafeInsert(baseActivity.dbManager.getWritableDatabase());


                    SearchEntity entity = new SearchEntity();
                    SearchEntity[] searchEntities = entity.retrieveAll(baseActivity.dbManager.getReadableDatabase());

                    int id_tSearchEntity = searchEntities.length > 0 ? searchEntities[0].get_id() : 0;

                    JSONObject fastestRoute = jsRoot.getJSONObject("fastestRoute");

                    JSONArray steps = fastestRoute.getJSONArray("steps");
                    for (int i = 0; i < steps.length(); i++) {
                        JSONObject step = steps.getJSONObject(i);
                        RouteEntity routeEntity = new RouteEntity();
                        routeEntity.setXid_origin(step.getInt("originXid"));
                        routeEntity.setXid_dest(step.getInt("destinationXid"));
                        routeEntity.setTxt_origin(step.getString("origin"));
                        routeEntity.setTxt_dest(step.getString("destination"));

                        routeEntity.setDistance(step.getInt("distance"));
                        routeEntity.setId_tSearchEntity(id_tSearchEntity);

                        routeEntity.setMode(step.getString("mode"));
                        routeEntity.setStep(i + 1);

                        JSONArray carriers = step.getJSONArray("carriers");
                        for (int j = 0; j < carriers.length(); j++) {
                            JSONObject carrier = carriers.getJSONObject(j);
                            routeEntity.setCode(carrier.getString("code"));
                            routeEntity.setArrTime(carrier.getString("arrTime"));
                            routeEntity.setDepTime(carrier.getString("depTime"));
                            routeEntity.setCarrierJson(carrier.toString());
                            routeEntity.setcName(carrier.getString("carrierName"));
                            routeEntity.setCode_origin(carrier.getString("originCode"));
                            routeEntity.setCode_dest(carrier.getString("destinationCode"));
                            try {
                                if (carrier.getString("price") != null) {
                                    routeEntity.setPrice(carrier.getInt("price"));
                                }
                            } catch (Exception e) {
                                
                            }
                            routeEntity.setTravelMinutes(carrier.getInt("time"));

                            routeEntity.dbsafeInsert(baseActivity.dbManager.getWritableDatabase());

                        }
                    }

                    getRecentSearches();

                } catch (Exception e) {
                    e.printStackTrace();
                    FuncUtils.showNotifyDialog(baseActivity, baseActivity.getString(R.string.err_api_json_malformed), new FuncUtils.OkCommunicator() {
                        @Override
                        public void onOkClicked() {
                        }
                    });
                }
            }

            @Override
            public void dataDownloadFailed(String reason) {
                baseActivity.dismissProgressDialog();
                FuncUtils.showNotifyDialog(baseActivity, (reason != null && reason.trim().length() > 0) ? reason : baseActivity.getString(R.string.err_api_unknown), new FuncUtils.OkCommunicator() {
                    @Override
                    public void onOkClicked() {
                    }
                });

            }
        }).execute(String.format(baseActivity.getString(R.string.url_get_routes), xid_origin, xid_destination));
    }

    @Override
    public void getRecentSearches() {
        SearchEntity searchEntity = new SearchEntity();
        SearchEntity[] searchEntities = searchEntity.retrieveAll(baseActivity.dbManager.getReadableDatabase());
        view.loadRecentSearches(searchEntities);
    }
}
