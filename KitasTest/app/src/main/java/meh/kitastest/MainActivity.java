package meh.kitastest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    //Declaring a string of some sort
    public static final String EXTRA_MESSAGE2 = "meh.kitastest.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "meh.kitastest.MESSAGE3";

    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate is used to start an activity
        super.onCreate(savedInstanceState);        //super is used to call the parent class constructor
        setContentView(R.layout.activity_main);    //setContentView is used to set the xml

        // Example of a call to a native method
        //TextView pirmas = (TextView) findViewById(R.id.edit_message);
        //tv.setText(stringFromJNI());
    }


    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //email
            EditText editText2 = (EditText) findViewById(R.id.editText2);
            String message2 = editText2.getText().toString();
            intent.putExtra(EXTRA_MESSAGE2, message2);
        //password
            EditText editText3 = (EditText) findViewById(R.id.editText3);
            String message3 = editText3.getText().toString();
            intent.putExtra(EXTRA_MESSAGE3, message3);

            Button sendSms = (Button) findViewById(R.id.button2);
            sendSms.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    sendSMSa("5556", "Hi You got a message!");
                           /*here i can send message to emulator 5556. In Real device
                            *you can change number*/
                }
            });

        startActivity(intent);
    }
    /** A test for sms message*/
//Sends an SMS message to another device

    private void sendSMSa(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
