package meh.kitastest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class MoneyActivity extends MainActivity {

    ListView list;
    Integer[] imgid={R.drawable.btc, R.drawable.eth, R.drawable.ripple, R.drawable.bitcoin_cash, R.drawable.eos,
                     R.drawable.litecoin, R.drawable.cardano, R.drawable.stellar, R.drawable.iota,
                     R.drawable.tron };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableTheme(useLightTheme);
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
            public_stuff.money = textas;

            if(public_stuff.sortedOnce == false) {
                saveTOP(); //issaugot populiariausius
                public_stuff.sortedOnce = true;
            }



            list = (ListView) findViewById(R.id.listView);
            list_adapter_crypto custom = new list_adapter_crypto(this, textas, imgid);
            list.setAdapter(custom);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MoneyActivity.this, currency_everything.class);
                    intent.putExtra("jsonStr", result);
                    intent.putExtra("kelintas", i);
                    startActivity(intent);
                }
            });



        } catch (JSONException e) {
            e.printStackTrace();
            //Log.d("LOOOOOOK ","fff");
        }

    }

    private void saveTOP() {
        StringBuilder sb = new StringBuilder();
        String PREFS_NAME = "prefs";
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        for(int x = 0; x < public_stuff.valiutuKiekis; x++) {
            String a = null;
            try {
                a = public_stuff.money.getJSONObject(x).getString("name").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sb.append(a).append(",");
        }
        editor.putString("topCrypto", sb.toString());
        editor.commit();
        ///Log.d("LOOOOOOK ", sb.toString());
        //Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_LONG).show();
    }





}
///String[] playlists = playlist.split(","); taip gaut data-