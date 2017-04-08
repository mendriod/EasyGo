package mendroid.easygo.utils;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import mendroid.easygo.R;

/**
 * @author Mendroid
 */
public class HttpGetTask extends AsyncTask<String, Void, String> {

    Context mContext;
    String resp;
    GetDataDownloadListener dataDownloadListener;


    public HttpGetTask(Context con, GetDataDownloadListener comm) {
        this.mContext = con;
        try {
            this.dataDownloadListener = comm;
            this.resp = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onPreExecute() {
    }


    protected String doInBackground(String... urls) {
        try {
            String currentRequestURL = urls[0];
            URL url = new URL(currentRequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);

//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//            writer.flush();
//            writer.close();
//            os.close();

            conn.connect();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    resp += line;
                }

                return resp;
            } else {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = br.readLine()) != null) {
                    resp += line;
                }
                return resp;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }


    protected void onPostExecute(String feed) {
        if (feed != null) {

            boolean isJson = false;
            try {
                new JSONObject((feed));
                isJson = true;
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    new JSONArray((feed));
                    isJson = true;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (isJson) {
                dataDownloadListener.dataDownloadedSuccessfully(feed);
            } else {
                dataDownloadListener.dataDownloadFailed(mContext.getString(R.string.err_api_json_malformed));
            }
        } else {
            if (FuncUtils.isNetworkAvailable(mContext)) {
                dataDownloadListener.dataDownloadFailed(mContext.getString(R.string.err_api_unknown));
            } else {
                dataDownloadListener.dataDownloadFailed(mContext.getString(R.string.err_network));
            }
        }
    }


    public interface GetDataDownloadListener {
        void dataDownloadedSuccessfully(String data);

        void dataDownloadFailed(String reason);
    }

}
