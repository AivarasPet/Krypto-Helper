package meh.kitastest;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Aivaras on 2018-03-18.
 */

public class data_download extends AsyncTask<Void, Void, Void> {

    String data="";
    String webSite = "";

    private AsyncCallback callbackas;

    public interface AsyncCallback {
        void gavauData(String result);
    }

    public data_download(AsyncCallback callback) {
        callbackas = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL(webSite);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sBuilder = new StringBuilder();
            String line="";
            while(line != null) {
                line = bufferedReader.readLine();
                sBuilder.append(line + "\n");
            }
            data = sBuilder.toString();

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        callbackas.gavauData(data);
        //MainActivity.Data.setText(this.data); //this.data tai cia atsiustas stringas
    }
}
/*
public class data_download {
    // Listener defined earlier
    public interface data_downloadListener {
        public void onObjectReady(String title);
        public void onDataLoaded(SomeData data);
    }

    // Member variable was defined earlier
    private data_downloadListener listener;

    // Constructor where listener events are ignored
    public data_download() {
        // set null or default listener or accept as argument to constructor
        this.listener = null;
        loadDataAsync();
    }

    // ... setter defined here as shown earlier

    public void loadDataAsync() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://mycustomapi.com/data/get.json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Networking is finished loading data, data is processed
                SomeData data = SomeData.processData(response.get("data"));
                // Do some other stuff as needed....
                // Now let's trigger the event
                if (listener != null)
                    listener.onDataLoaded(data); // <---- fire listener here
            }
        });
    }
}*/