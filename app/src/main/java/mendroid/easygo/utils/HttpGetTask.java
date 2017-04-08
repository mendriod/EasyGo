package mendroid.easygo.utils;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
    String data;


    public HttpGetTask(Context con, String data, GetDataDownloadListener comm) {
        this.mContext = con;
        try {
            this.data = data;
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
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            os.close();

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
            try {
                new JSONObject((feed));
                dataDownloadListener.dataDownloadedSuccessfully(feed);
            } catch (Exception e) {
                e.printStackTrace();
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
