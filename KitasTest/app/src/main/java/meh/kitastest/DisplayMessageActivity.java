package meh.kitastest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        String message3 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);

        // Capture the layout's TextView and set the string as it's text
        TextView textView2 = findViewById(R.id.textView);
        textView2.setText(message2);

        TextView textView3 = findViewById(R.id.textView7);
        textView3.setText(message3);
    }
}
