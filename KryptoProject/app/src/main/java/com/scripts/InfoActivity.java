package com.scripts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {



    ImageView img;
    TextView text;
    JSONObject infoAssetsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        img = (ImageView) findViewById(R.id.infoImage);
        text = (TextView) findViewById(R.id.info_text);

        final Spinner spinner = (Spinner) findViewById(R.id.infoSpinner);
        ArrayAdapter<String > adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  PublicStuff.getSortedTOP());


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String url = null;
                try {
                    JSONObject infoSourceCrypto = PublicStuff.getMoney().getJSONObject(position).getJSONObject("CoinInfo");

                    ImageLoader.getInstance().loadImageByCryptoPosition(img, position, 256, 256);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(infoAssetsText != null) {
                    try {
                        String string = infoAssetsText.getString(PublicStuff.getCryptoNameByPosition(position));
                        if(string.equals("")) string = "to be fixed";
                        text.setText(string);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else text.setText("to be fixed");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Bundle bundle = getIntent().getExtras();
        if(getIntent().hasExtra("kelintas")) {
            int position = bundle.getInt("kelintas");
            try {
                infoAssetsText = new JSONObject(bundle.getString("infoAssetsText"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            spinner.setSelection(position);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(InfoActivity.this, MainActivity.class));
    }


}
