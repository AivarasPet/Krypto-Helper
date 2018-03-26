package meh.kitastest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class crypto_list extends AppCompatActivity {

    ListView list;

    String[] trumpiniai = {"BTC", "ETH", "LTC", "XRP"};
    String[] valiutos = {"Bitcoin", "Ethereum", "litecoin", "ripple"};
    Integer[] imgid={R.drawable.btc, R.drawable.eth, R.drawable.litecoin, R.drawable.ripple};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_list);
        list = (ListView) findViewById(R.id.listView);
        cryptoListExtra custom = new cryptoListExtra(this, trumpiniai, valiutos, imgid);
        list.setAdapter(custom);
    }
}
