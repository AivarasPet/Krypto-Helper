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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class MoneyActivity extends MainActivity {

    ListView list;
    list_adapter_crypto adapter;
    boolean sukurtasAdapteris = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableTheme(useLightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_list);
        makeTheList();



    }


    private void makeTheList() {
        if(!sukurtasAdapteris) {adapter = new list_adapter_crypto(getBaseContext(), public_stuff.money); sukurtasAdapteris = true;}
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("KEY_POSITION", i);
                    bundle.putString("KEY_MODE", "ListClick");
            }
        });
    }

}
///String[] playlists = playlist.split(","); taip gaut data-