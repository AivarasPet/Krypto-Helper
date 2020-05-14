package com.scripts;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


/**
 * A simple {@link Fragment} subclass.
 */
public class CryptoExpFragment extends Fragment implements View.OnClickListener {

    TextView pav, trump, kaina, pokyt, info, kainaBTC, changePctHourTextBox;
    GraphView graph;
    ImageView img;
    FrameLayout frameLayout;
    public int selectedItemPosition;
    JSONObject cryptoInfoSource, infoAssetsText = PublicStuff.getCryptoInfoFromAssets();


    public CryptoExpFragment() {
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
        info.setOnClickListener(this);
        graph = (GraphView) view.findViewById(R.id.graphInDetailed);
        graph.setOnClickListener(this);
        kainaBTC = (TextView) view.findViewById(R.id.priceInBTC);
        changePctHourTextBox = (TextView) view.findViewById(R.id.weekChange);
        //txt1.setText(bundle.getString("jsonStr"));
        selectedItemPosition = bundle.getInt("KEY_POSITION");
        handleLook(selectedItemPosition, PublicStuff.getMoney());

        PublicStuff.setCurrentFragment(PublicStuff.fragmentType.EXPANDED);

        initGraph(selectedItemPosition, graph); //grafika

        try {
            infoAssetsText = PublicStuff.getCryptoInfoFromAssets();
            String infoPre = infoAssetsText.getString(cryptoInfoSource.getString("Name"));
            if(infoPre != null) {
                infoPre = infoPre.substring(0, 200)+" ...";
                info.setText(infoPre);
            }
            else info.setText("no info contained.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), "App won't work !!!", Toast.LENGTH_LONG);
        return view;
    }

    private void handleLook(int position, JSONArray textas) {
        try {
            JSONObject toDisplaySource = textas.getJSONObject(position).getJSONObject("DISPLAY").getJSONObject("USD");
            cryptoInfoSource = textas.getJSONObject(position).getJSONObject("CoinInfo");
            trump.setText(cryptoInfoSource.getString("Internal"));
            pav.setText(cryptoInfoSource.getString("FullName"));
            kaina.setText(toDisplaySource.getString("PRICE"));

            double priceInBtc = 2000;
            double priceDbl = textas.getJSONObject(position).getJSONObject("RAW").getJSONObject("USD").getDouble("PRICE");
            if(PublicStuff.btcPrice != 0) priceInBtc = priceDbl / PublicStuff.btcPrice;
            else priceInBtc = 0;
            priceInBtc = (double) Math.round(priceInBtc * 100.0) / 100.0;
            String string = String.valueOf(priceInBtc);
            //kainaBTC.setText(String.format("%.2f", string));
            kainaBTC.setText(string);
            pokyt.setText(toDisplaySource.getString("CHANGEPCT24HOUR")+"%");
            changePctHourTextBox.setText(toDisplaySource.getString("CHANGEPCTHOUR")+"%");
            ImageLoader.getInstance().loadImageByCryptoPosition(img, position, 256, 256);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void initGraph(int position, GraphView graphView) {
        // STAI KODAS GAUT URL: String url = )public_stuff.visas.getJSONObject(1).getString(public_stuff.sortedTOP[position];   // 1 yra menesio 2 - paros

            GraphInstance graph = new GraphInstance();
            graph.graph = graphView;
            graph.nameStr = PublicStuff.getSortedTOP()[position];/*
            try {
                String spalva = PublicStuff.visas.getJSONObject(3).getString(PublicStuff.sortedTOP[position]).toString();
                graph.colour = spalva;
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            graph.isDaily = true;
            graph.isPreview = true;
            graph.context = getActivity();

                String url = null, name = "";
                name = PublicStuff.getCryptoNameByPosition(position);
                graph.run(name, true);
    }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.graphInDetailed:
               intent = new Intent(getActivity(), GraphActivity.class);
                intent.putExtra("kelintas", selectedItemPosition);
                startActivity(intent);
                break;
            case R.id.infoInDetails:
                intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra("kelintas", selectedItemPosition);
                intent.putExtra("infoAssetsText", infoAssetsText.toString());
                startActivity(intent);
                break;
        }
        PublicStuff.setCurrentFragment(PublicStuff.fragmentType.GRAPH_ACTIVITY);
        //PublicStuff.Frag = selectedItemPosition;
        //Log.d("veik", "veik");
    }



}
