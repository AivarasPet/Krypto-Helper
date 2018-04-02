package meh.kitastest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class crypto_list extends AppCompatActivity {

    ListView list;

    Integer[] imgid={R.drawable.btc, R.drawable.eth, R.drawable.ripple, R.drawable.bitcoin_cash, R.drawable.litecoin,
                     R.drawable.eos, R.drawable.stellar, R.drawable.cardano, R.drawable.neo,
                     R.drawable.iota };


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
        dl.webSite = "https://api.coinmarketcap.com/v1/ticker/?limit=10";
        dl.execute();
    }
    private void toliau(final String result){

        try {
            final JSONArray textas = new JSONArray(result);
            //Log.d("myTag", textas.getJSONObject(0).getString("symbol").toString());
            //Log.d("Log.d", textas.length()+"");

            list = (ListView) findViewById(R.id.listView);
            list_adapter_crypto custom = new list_adapter_crypto(this, textas, imgid);
            list.setAdapter(custom);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(crypto_list.this, currency_everything.class);
                    intent.putExtra("jsonStr", result);
                    intent.putExtra("kelintas", i);
                    startActivity(intent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void openList() {

    }

}
