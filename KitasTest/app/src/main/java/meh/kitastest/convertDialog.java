package meh.kitastest;

import android.app.Activity;
import android.app.AlertDialog;
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

public class convertDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button convert;
    Spinner spinner;
    EditText editCrypto, editMoney;
    boolean kuris;

    public convertDialog(Activity a) {
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
                kuris = false;
                return false;
            }
        });
        editCrypto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                kuris = true;
                return false;
            }
        });
        convert = (Button) findViewById(R.id.convertDo);
        convert.setOnClickListener(this);
        ArrayAdapter<String > adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item,  public_stuff.sortedTOP);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.droplist);
        spinner.setAdapter(adapter);
    }

    void count(boolean yes) { //yes - i pinigus, !yes - i coinus

        if (yes) {
            if (!TextUtils.isEmpty(editCrypto.getText())) {
                float dollars = 0;
                String kursas = null;
                try {
                    int pozicija = spinner.getSelectedItemPosition();
                    kursas = public_stuff.money.getJSONObject(pozicija).getString("price_usd").toString();
                    dollars = Float.parseFloat(kursas) * Float.parseFloat(editCrypto.getText().toString());
                    editMoney.setText("" + dollars);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } //jei netuscias laukelis
        } else {
            if (!TextUtils.isEmpty(editMoney.getText())) {
                    float dollars = 0;
                    String kursas = null;
                    try {
                        int pozicija = spinner.getSelectedItemPosition();
                        kursas = public_stuff.money.getJSONObject(pozicija).getString("price_usd").toString();
                        dollars = Float.parseFloat(editMoney.getText().toString()) / Float.parseFloat(kursas);
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
                kuris = true;
                Log.d("crypto", "crypto");
                break;
            case R.id.moneyEdit:
                kuris = false;
                Log.d("money", "money");
                break;
            case R.id.convertDo:
                count(kuris);
                break;
            default:
                break;
        }
    }
}