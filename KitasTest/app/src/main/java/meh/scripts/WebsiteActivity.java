package meh.scripts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebsiteActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        Bundle bundle = getIntent().getExtras();
        webView = (WebView) findViewById(R.id.WebView);

        //setContentView(webView);
        String url = bundle.getString("url");
        webView.loadUrl(url);
    }

    private static int sessionDepth = 0;

    @Override
    protected void onStart() {
        super.onStart();
        sessionDepth++;
        if(sessionDepth == 1){
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sessionDepth > 0)
            sessionDepth--;
        if (sessionDepth == 0) {
            // app went to background
        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(WebsiteActivity.this, MainActivity.class));
    }
}
