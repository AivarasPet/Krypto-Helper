package meh.kitastest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 */
public class cryptoExpFragment extends Fragment {

    TextView pav, trump, kaina, pokyt, info, kainaBTC, pokyt7d;
    GraphView graph;
    ImageView img;
    FrameLayout frameLayout;
    public int kelintas;

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

    public cryptoExpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        Bundle bundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_crypto_exp, container, false);
        pav = (TextView) view.findViewById(R.id.nameInDetailed);
        trump = (TextView) view.findViewById(R.id.trumpinysInDetailed);
        img = (ImageView) view.findViewById(R.id.imageInDetailed);
        kaina = (TextView) view.findViewById(R.id.priceInDetailed);
        pokyt = (TextView) view.findViewById(R.id.changeInDetailed);
        info = (TextView) view.findViewById(R.id.infoInDetails);
        graph = (GraphView) view.findViewById(R.id.graphInDetailed);
        kainaBTC = (TextView) view.findViewById(R.id.priceInBTC);
        pokyt7d = (TextView) view.findViewById(R.id.weekChange);
        //txt1.setText(bundle.getString("jsonStr"));
        kelintas = bundle.getInt("KEY_POSITION");
        handleLook(kelintas, public_stuff.money);


        createClass(kelintas, graph); //grafika
        String infoPre = getResources().getStringArray(R.array.Info)[kelintas];
        infoPre = infoPre.substring(0, 200)+" ...";
        info.setText(infoPre);

        return view;
    }

    private void handleLook(int position, JSONArray textas) {
        try {
            trump.setText(textas.getJSONObject(position).getString("symbol").toString());
            pav.setText(textas.getJSONObject(position).getString("name").toString());
            kaina.setText(textas.getJSONObject(position).getString("price_usd").toString()+"$");
            pokyt.setText(textas.getJSONObject(position).getString("percent_change_24h").toString()+"%");
            kainaBTC.setText(textas.getJSONObject(position).getString("price_btc").toString());
            pokyt7d.setText(textas.getJSONObject(position).getString("percent_change_7d").toString()+"%");
            String url = public_stuff.visas.getJSONObject(0).getString(public_stuff.sortedTOP[position]);   // fotkems
            Picasso.get().load(url).resize(256, 256).centerCrop().into(img);        // fotkems


        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    public void onGraphClick(View v) {
        Intent intent = new Intent(getActivity(), GraphActivity.class);
        intent.putExtra("kelintas", kelintas);
        startActivity(intent);
    }

    public void onClickDetails(View v) {
        Intent intent = new Intent(getActivity(), InfoActivity.class);
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
        naujas.context = getActivity();
        naujas.run(url[position]);
    }

}
