package meh.kitastest;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;




public class currency_everything extends MainActivity {

    TextView pav, trump, kain, pokytis;
    ImageView img;


    Integer[] imgid={R.drawable.btc, R.drawable.eth, R.drawable.ripple, R.drawable.bitcoin_cash, R.drawable.litecoin,
            R.drawable.eos, R.drawable.stellar, R.drawable.cardano, R.drawable.neo,
            R.drawable.iota };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableTheme(useLightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_everything);

        Bundle bundle = getIntent().getExtras();
        pav = (TextView) findViewById(R.id.nameInDetailed);
        trump = (TextView) findViewById(R.id.trumpinysInDetailed);
        kain = (TextView) findViewById(R.id.price_usd);
        img = (ImageView) findViewById(R.id.imageInDetailed);
        pokytis = (TextView) findViewById(R.id.percent_change);
        //txt1.setText(bundle.getString("jsonStr"));
        int lol = bundle.getInt("kelintas");
        handleLook(lol, bundle.getString("jsonStr"));


    }



    private void handleLook(int position, String json) {
        try {
            JSONArray textas = new JSONArray(json);
            trump.setText(textas.getJSONObject(position).getString("symbol").toString());
            pav.setText(textas.getJSONObject(position).getString("name").toString());
            kain.setText("Current price:   $ " + textas.getJSONObject(position).getString("price_usd").toString());
            img.setImageResource(imgid[position]);

            list_adapter_crypto.ViewHolder holder;
            holder = new list_adapter_crypto.ViewHolder();
            String pokytis = textas.getJSONObject(position).getString("percent_change_24h").toString();

            if(pokytis.startsWith("-")) {
                // pokytis.substring(2, pokytis.length());
                pokytis = pokytis.replace("-","");
                holder.pokytis.setText("▼"+pokytis+"%");
            }
            else holder.pokytis.setText("▲" + pokytis + "%");



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}