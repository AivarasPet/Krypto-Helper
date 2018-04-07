package meh.kitastest;

/**
 * Created by Artis on 2018-03-26.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.ServiceWorkerClient;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.view.View;
import android.widget.Switch;

public class OptionsActivity extends MainActivity{

    Switch themeswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        enableTheme(useLightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Switch themeswitch = (Switch) findViewById(R.id.theme_switch);
        themeswitch.setChecked(usabilityLightTheme());
        themeswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleTheme(isChecked);

            }

        });
    }








}
