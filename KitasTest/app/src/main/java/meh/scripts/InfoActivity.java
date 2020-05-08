package meh.scripts;

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

public class InfoActivity extends AppCompatActivity {



    ImageView img;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        img = (ImageView) findViewById(R.id.infoImage);
        text = (TextView) findViewById(R.id.info_text);

        final Spinner spinner = (Spinner) findViewById(R.id.infoSpinner);
        ArrayAdapter<String > adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  public_stuff.sortedTOP);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String url = null;
                try {
                    url = public_stuff.visas.getJSONObject(0).getString(public_stuff.sortedTOP[position]);
                    Picasso.get().load(url).resize(128, 128).centerCrop().into(img);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                text.setText("to be fixed");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Bundle bundle = getIntent().getExtras();
        if(getIntent().hasExtra("kelintas")) {
            int lol = bundle.getInt("kelintas");
            spinner.setSelection(lol);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(InfoActivity.this, MainActivity.class));
    }
}
