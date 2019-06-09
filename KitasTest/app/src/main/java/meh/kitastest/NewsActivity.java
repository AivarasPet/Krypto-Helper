package meh.kitastest;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

public class NewsActivity extends MainActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableTheme(useLightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        if(public_stuff.downloadedNews == false) {
            data_download dl = new data_download(new data_download.AsyncCallback() {
                @Override
                public void gavauData(String result) {
                    toliau(result);
                }
            });
            dl.webSite = " https://min-api.cryptocompare.com/data/news/?lang=EN"; //https://api.coinmarketcap.com/v1/ticker/?limit=10
            dl.execute();
        }
            makeList();

    }




    private void toliau(String result) {
        try {
            final JSONArray textas = new JSONArray(result);
            public_stuff.newsJson = textas;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void makeList() {
        list = (ListView) findViewById(R.id.NewsList);
        news_adapter custom = new news_adapter(this, public_stuff.newsJson);
        list.setAdapter(custom);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewsActivity.this, WebsiteActivity.class);
                String url = null;
                try {
                    url = public_stuff.newsJson.getJSONObject(i).getString("guid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }
}
