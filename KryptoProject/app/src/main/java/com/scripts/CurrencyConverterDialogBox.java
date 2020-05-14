package com.scripts;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;

public class CurrencyConverterDialogBox extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button convert;
    Spinner spinner;
    EditText editCrypto, editMoney;
    boolean wasCryptoBoxEdited;

    public CurrencyConverterDialogBox(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.connvert_dialog);

        editCrypto = (EditText) findViewById(R.id.cryptoEdit);
        editMoney = (EditText) findViewById(R.id.moneyEdit);
        editMoney.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wasCryptoBoxEdited = false;
                return false;
            }
        });
        editCrypto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wasCryptoBoxEdited = true;
                return false;
            }
        });
        convert = (Button) findViewById(R.id.convertDo);
        convert.setOnClickListener(this);
        ArrayAdapter<String > adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item,  PublicStuff.getSortedTOP());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.droplist);
        spinner.setAdapter(adapter);
    }

    void count(boolean yes) { //yes - i pinigus, !yes - i coinus

        if (yes) {
            if (!TextUtils.isEmpty(editCrypto.getText())) {
                float dollars = 0;
                float rate = 0;
                try {
                    int position = spinner.getSelectedItemPosition();
                    rate = Float.parseFloat(PublicStuff.getMoney().getJSONObject(position).getJSONObject("RAW").getJSONObject("USD").getString("PRICE"));
                    dollars = rate * Float.parseFloat(editCrypto.getText().toString());
                    editMoney.setText("" + dollars);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } //jei netuscias laukelis
        } else {
            if (!TextUtils.isEmpty(editMoney.getText())) {
                    float dollars = 0;
                    float rate = 0;
                    try {
                        int position = spinner.getSelectedItemPosition();
                        rate = Float.parseFloat(PublicStuff.getMoney().getJSONObject(position).getJSONObject("RAW").getJSONObject("USD").getString("PRICE"));
                        dollars = Float.parseFloat(editMoney.getText().toString()) / rate;
                        editCrypto.setText("" + dollars);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } //jei netuscias laukelis
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cryptoEdit:
                wasCryptoBoxEdited = true;
                Log.d("crypto", "crypto");
                break;
            case R.id.moneyEdit:
                wasCryptoBoxEdited = false;
                Log.d("money", "money");
                break;
            case R.id.convertDo:
                count(wasCryptoBoxEdited);
                break;
            default:
                break;
        }
    }
}