package meh.kitastest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();


        // Capture the layout's TextView and set the string as it's text
    }
}
