package meh.kitastest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class crypto_list extends AppCompatActivity {

    ListView list;

    String[] trumpiniai = {"BTC", "ETH", "LTC", "XRP"};
    String[] valiutos = {"Bitcoin", "Ethereum", "Litecoin", "Ripple"};
    Integer[] imgid={R.drawable.btc, R.drawable.eth, R.drawable.litecoin, R.drawable.ripple};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_list);


            // darbas su data_download klase:
        data_download dl = new data_download(new data_download.AsyncCallback() {
            @Override
            public void gavauData(String result) {
                toliau(result);
            }
        });
        dl.webSite = "https://api.coinmarketcap.com/v1/ticker/";
        dl.execute();
    }
    private void toliau(String result){

        try {
            JSONArray textas = new JSONArray(result);
            Log.d("myTag", textas.getJSONObject(0).getString("symbol").toString());

            list = (ListView) findViewById(R.id.listView);
           //list_adapter_crypto custom = new list_adapter_crypto(this, textas, imgid);
            list_adapter_crypto custom = new list_adapter_crypto(this, trumpiniai, valiutos, imgid);
            list.setAdapter(custom);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
