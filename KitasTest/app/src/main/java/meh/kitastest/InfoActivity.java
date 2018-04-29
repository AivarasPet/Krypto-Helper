package meh.kitastest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class InfoActivity extends MainActivity {

    Integer[] imgid={R.drawable.btc, R.drawable.eth, R.drawable.ripple, R.drawable.bitcoin_cash, R.drawable.litecoin,
            R.drawable.eos, R.drawable.cardano, R.drawable.stellar, R.drawable.neo,
            R.drawable.iota };

    ImageView img;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableTheme(useLightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        img = (ImageView) findViewById(R.id.infoImage);
        text = (TextView) findViewById(R.id.info_text);

        final Spinner spinner = (Spinner) findViewById(R.id.infoSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cryptoNames, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Toast.makeText(getApplicationContext(), position, Toast.LENGTH_LONG);
                //Log.d("D", position+"");
                img.setImageResource(imgid[position]);
                text.setText(getResources().getStringArray(R.array.Info)[position]);
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
}
