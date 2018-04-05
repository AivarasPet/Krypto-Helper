package meh.kitastest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    Button money_button, options_button, graph_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate is used to start an activity
        super.onCreate(savedInstanceState);        //super is used to call the parent class constructor
        setContentView(R.layout.main_menu);    //setContentView is used to set the xml

        money_button = (Button) findViewById(R.id.moneyBut);
        options_button = (Button) findViewById(R.id.optionsBut);
        graph_button = (Button) findViewById(R.id.graphBut);

        money_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,  MoneyActivity.class));
            }
        });
        options_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,  OptionsActivity.class));
            }
        });
        graph_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,  GraphActivity.class));
            }
        });


/*
        public void ThemeSwitch(View this){
            boolean checked = ((Switch) this).isChecked();
                if (checked)

    }
*/

    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
