package meh.kitastest;

/**
 * Created by Artis on 2018-03-26.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.ServiceWorkerClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class OptionsActivity extends MainActivity{

    Switch themeswitch;
    Button btn;
    String KEY = "topCrypto", PREFS_NAME = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        enableTheme(useLightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        themeswitch = (Switch) findViewById(R.id.theme_switch);
        themeswitch.setChecked(usabilityLightTheme());
        themeswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleTheme(isChecked);

            }

        });

        btn = findViewById(R.id.updateBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                String a = pref.getString(KEY, "");
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
            }
        });
    }










}
