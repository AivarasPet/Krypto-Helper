package meh.kitastest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class currency_everything extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_everything);

        Bundle bundle = getIntent().getExtras();
        TextView txt1 = (TextView) findViewById(R.id.nameInDetailed);
        txt1.setText(bundle.getString("jsonStr"));

    }
}
