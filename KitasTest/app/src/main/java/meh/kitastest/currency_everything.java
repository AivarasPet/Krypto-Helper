package meh.kitastest;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;




public class currency_everything extends MainActivity {

    TextView pav, trump, kaina, pokyt, info, kainaBTC, pokyt7d;
    GraphView graph;
    ImageView img;
    public int kelintas;

    Integer[] imgid={R.drawable.btc, R.drawable.eth, R.drawable.ripple, R.drawable.bitcoin_cash, R.drawable.eos,
            R.drawable.litecoin, R.drawable.cardano, R.drawable.stellar, R.drawable.iota,
            R.drawable.tron };

    String[] url = {
            "https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=ETH&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=XRP&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=BCH&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=EOS&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=LTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=ADA&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=XLM&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=IOT&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=TRX&tsym=USD&limit=30&aggregate=1&e=CCCAGG"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableTheme(useLightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_everything);

        Bundle bundle = getIntent().getExtras();
        pav = (TextView) findViewById(R.id.nameInDetailed);
        trump = (TextView) findViewById(R.id.trumpinysInDetailed);
        img = (ImageView) findViewById(R.id.imageInDetailed);
        kaina = (TextView) findViewById(R.id.priceInDetailed);
        pokyt = (TextView) findViewById(R.id.changeInDetailed);
        info = (TextView) findViewById(R.id.infoInDetails);
        graph = (GraphView) findViewById(R.id.graphInDetailed);
        kainaBTC = (TextView) findViewById(R.id.priceInBTC);
        pokyt7d = (TextView) findViewById(R.id.weekChange);
        //txt1.setText(bundle.getString("jsonStr"));
        kelintas = bundle.getInt("kelintas");
        handleLook(kelintas, bundle.getString("jsonStr"));


        createClass(kelintas, graph); //grafika
        String infoPre = getResources().getStringArray(R.array.Info)[kelintas];
        infoPre = infoPre.substring(0, 200)+" ...";
        info.setText(infoPre);

    }


    private void handleLook(int position, String json) {
        try {
            JSONArray textas = new JSONArray(json);
            trump.setText(textas.getJSONObject(position).getString("symbol").toString());
            pav.setText(textas.getJSONObject(position).getString("name").toString());
            kaina.setText(textas.getJSONObject(position).getString("price_usd").toString()+"$");
            pokyt.setText(textas.getJSONObject(position).getString("percent_change_24h").toString()+"%");
            kainaBTC.setText(textas.getJSONObject(position).getString("price_btc").toString());
            pokyt7d.setText(textas.getJSONObject(position).getString("percent_change_7d").toString()+"%");
            img.setImageResource(imgid[position]);

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    public void onGraphClick(View v) {
        Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
        intent.putExtra("kelintas", kelintas);
        startActivity(intent);
    }

    public void onClickDetails(View v) {
        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
        intent.putExtra("kelintas", kelintas);
        startActivity(intent);
    }

    private void createClass(int position, GraphView grafik) {
        graph_adapter naujas = new graph_adapter();
        naujas.graph = grafik;
        naujas.isPreview = true;
        naujas.pav = getResources().getStringArray(R.array.cryptoNames)[position];
        naujas.spalva = getResources().getStringArray(R.array.ColorsGraph)[position];
        naujas.mode = 0;
        naujas.context = getApplicationContext();
        naujas.run(url[position]);
    }

}


