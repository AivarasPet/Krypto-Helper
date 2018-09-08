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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableTheme(useLightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_list);




        // list = (ListView) findViewById(R.id.listView);
        //list_adapter_crypto custom = new list_adapter_crypto(this., textas);
        //list.setAdapter(custom);
        //list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
        //   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //      Intent intent = new Intent(MoneyActivity.this, currency_everything.class);
        //     intent.putExtra("jsonStr", result);
        //    intent.putExtra("kelintas", i);
        //    startActivity(intent);
        // }
        //});



    }




}
///String[] playlists = playlist.split(","); taip gaut data-