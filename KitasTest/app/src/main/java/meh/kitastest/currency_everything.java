package meh.kitastest;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;




public class currency_everything extends AppCompatActivity {

    TextView pav, trump;
    ImageView img;

    Integer[] imgid={R.drawable.btc, R.drawable.eth, R.drawable.ripple, R.drawable.bitcoin_cash, R.drawable.litecoin,
            R.drawable.eos, R.drawable.stellar, R.drawable.cardano, R.drawable.neo,
            R.drawable.iota };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_everything);

        Bundle bundle = getIntent().getExtras();
        pav = (TextView) findViewById(R.id.nameInDetailed);
        trump = (TextView) findViewById(R.id.trumpinysInDetailed);
        img = (ImageView) findViewById(R.id.imageInDetailed);
        //txt1.setText(bundle.getString("jsonStr"));
        int lol = bundle.getInt("kelintas");
        handleLook(lol, bundle.getString("jsonStr"));


    }


    private void handleLook(int position, String json) {
        try {
            JSONArray textas = new JSONArray(json);
            trump.setText(textas.getJSONObject(position).getString("symbol").toString());
            pav.setText(textas.getJSONObject(position).getString("name").toString());
            img.setImageResource(imgid[position]);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}