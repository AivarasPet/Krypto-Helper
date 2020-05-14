package com.scripts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity  implements InterfaceForFragments {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_LIGHT_THEME = "light_theme";
    private FrameLayout MainFrame;
    private BottomNavigationView toolbar;
    private MoneyFragment moneyFragment;
    private NewsFragment newsFragment;
    private PortfolioFragment portfolioFragment;
    private CryptoExpFragment cryptoExpFragment;

    boolean useLightTheme;
    SharedPreferences preferences;
    Button money_button, options_button, graph_button, news_button, info_button;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_trends:
                    setFragment(moneyFragment, null);
                    return true;
                case R.id.navigation_news:
                    setFragment(newsFragment, null);
                    return true;
                case R.id.navigation_portfolio:
                    setFragment(portfolioFragment, null);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate is used to start an activity

        //  CODE FOR GETTING THE THEME
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        useLightTheme = preferences.getBoolean(PREF_LIGHT_THEME, true);
        enableTheme(useLightTheme);

        super.onCreate(savedInstanceState);    //super is used to call the parent class constructor
        setContentView(R.layout.main_menu);    //setContentView is used to set the xml


        toolbar = (BottomNavigationView) findViewById(R.id.toolbar);    // for bottom bar
        toolbar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        MainFrame = (FrameLayout) findViewById(R.id.mainFrame);
        moneyFragment = new MoneyFragment();
        moneyFragment.setInterfaceForFragments(this);
        newsFragment = new NewsFragment();
        cryptoExpFragment = new CryptoExpFragment();
        portfolioFragment = new PortfolioFragment();
        portfolioFragment.setInterfaceForFragments(this);

        if (PublicStuff.getCurrentFragment() == null) setFragment(newsFragment, null);
        else {
            if (PublicStuff.getCurrentFragment() == PublicStuff.fragmentType.GRAPH_ACTIVITY) {
                Bundle bundle = new Bundle();
                bundle.putInt("KEY_POSITION", PublicStuff.getLastPositionExp());
                bundle.putString("KEY_MODE", "ListClick");
                setFragment(cryptoExpFragment, bundle);
            } else if (PublicStuff.getCurrentFragment() == PublicStuff.fragmentType.NEWS) {
                setFragment(newsFragment, null);
                toolbar.getMenu().getItem(1).setChecked(true);
            } else if (PublicStuff.getCurrentFragment() == PublicStuff.fragmentType.PORTFOLIO) {
                setFragment(portfolioFragment, null);
                toolbar.getMenu().getItem(2).setChecked(true);
            } else if (PublicStuff.getCurrentFragment() != PublicStuff.fragmentType.NEWS) {
                setFragment(moneyFragment, null);
                toolbar.getMenu().getItem(0).setChecked(true);
            }
        }

        String moneyData = preferences.getString("moneyData", "");
        if (!moneyData.equals("")) {
            afterDownload(moneyData);
        }


        //Atsiuntimas:
        HttpRequestDownload dl = new HttpRequestDownload(new HttpRequestDownload.AsyncCallback() {
            @Override
            public void gavauData(String result) {
                afterDownload(result);
            }
        });
        dl.webSite = PublicStuff.getMainUrl();
        dl.execute();
        Toast.makeText(this, "bando", Toast.LENGTH_LONG);

        if (isNetworkAvailable(getApplicationContext()))
            Toast.makeText(getApplicationContext(), "App won't work without Internet Connection!!!", Toast.LENGTH_LONG);

        String a = preferences.getString("topCrypto", "");
        PublicStuff.setSortedTOP(a.split(","));

        if(PublicStuff.isLoadedDataOnce() == false) {
            PublicStuff.setCryptoInfoFromAssets(parseJSONlocal("info_text.json"));
            PublicStuff.setGraphColorsFromAssets(parseJSONlocal("graph_colors.json"));
            PublicStuff.setLoadedDataOnce(true);
        }
    }

    public boolean usabilityLightTheme() {
        return useLightTheme;
    }


    @Override
    protected void onResume() {
        super.onResume();
        //setFragment(newsFragment, null);
    }

    @Override
    public void onBackPressed() {
        if (PublicStuff.getCurrentFragment() == PublicStuff.fragmentType.EXPANDED) setFragment(moneyFragment, null);
    }


    public void toggleTheme(boolean lightTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_LIGHT_THEME, lightTheme);
        editor.apply();

        Intent theme = getIntent();
        finish();

        startActivity(theme);
    }

    public void enableTheme(boolean useLightTheme) {
        if (useLightTheme) setTheme(R.style.LightAppTheme);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */


    @Override
    public void onRestart() {
        super.onRestart();
        useLightTheme = preferences.getBoolean(PREF_LIGHT_THEME, false);
        enableTheme(useLightTheme);
        setContentView(R.layout.main_menu);
        Log.d("restarin", "Reeee");
        finish();
        startActivity(getIntent());
    }


    @Override
    public void onActionInFragment(Bundle bundle) {
        setFragment(cryptoExpFragment, bundle); //nebutinai sita ijungia
    }


    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    private void setFragment(android.support.v4.app.Fragment fragment, Bundle bundle) {
        if (bundle != null) { // jei egzisuoja bundle
            String mode = bundle.getString("KEY_MODE");
            switch (mode) {
                case "ListClick":
                    Bundle bundle2 = new Bundle();
                    int position = bundle.getInt("KEY_POSITION");
                    bundle2.putInt("KEY_POSITION", position); //is pirmo i antra
                    fragment.setArguments(bundle2);
                    break;
                case "PortfolioFragment":
                    Log.d("daro ", " DARo");

                    break;
            }
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }


    private void afterDownload(String result) {

        try {

            Log.d("", result);
            JSONObject object = new JSONObject(result);
            JSONArray textas = object.getJSONArray("Data");
            PublicStuff.btcPrice = Float.parseFloat(textas.getJSONObject(0).getJSONObject("RAW").getJSONObject("USD").getString("PRICE"));
            PublicStuff.setMoney(textas);
            saveTOP();


        } catch (JSONException e) {
            //Toast.makeText(this, "Data downloaded incorrect", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void saveTOP() {

        StringBuilder sb = new StringBuilder();
        String PREFS_NAME = "prefs";
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        for(int x = 0; x < PublicStuff.getCryptoQuantity(); x++) {
            String a = null;
            try {
                a = PublicStuff.getMoney().getJSONObject(x).getJSONObject("CoinInfo").getString("FullName").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sb.append(a).append(",");
        }
        editor.putString("topCrypto", sb.toString());
        editor.putString("moneyData", PublicStuff.getMoney().toString());
        editor.commit();
        PublicStuff.setSortedTOP(sb.toString().split(","));
    }


    // kaip suzinot is shared prefrencu ar appas buvo ijungtas:

    // SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this); //patestina ar katik ijunge
    // boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
    //     if (isFirstRun)
    //             {
    //             // Code to run once
    //             SharedPreferences.Editor editor = wmbPreference.edit();
    //             editor.putBoolean("FIRSTRUN", false);
    //             editor.commit();
    //             }


    private JSONObject parseJSONlocal(String folderName) {
        try {
            InputStream inputStream = getAssets().open(folderName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String string = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(string);
            return jsonObject;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}