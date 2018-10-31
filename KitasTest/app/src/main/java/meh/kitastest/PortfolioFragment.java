package meh.kitastest;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PortfolioFragment extends Fragment implements View.OnClickListener {



    ArrayList<String> list ;
    ListView listView;
    Button addButton, removeHistory, subtractBtn, convertBtn;
    TextView allInDolars;
    SharedPreferences preferences;
    InterfaceForFragments interfaceForFragments;
    String[] stockArr;
    list_adapter_portfolio listAdapterPortfolio;

    public PortfolioFragment() {
        // Required empty public constructor
    }

    public  void setInterfaceForFragments(InterfaceForFragments interfaceForFragments) {
        this.interfaceForFragments = interfaceForFragments;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        list = new ArrayList();
        list = getArrayList("owned");
        stockArr = new String[list.size()];
        stockArr = list.toArray(stockArr);
        preferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);

        public_stuff.currentFragment = "PortfolioFragment";

        View view  = inflater.inflate(R.layout.fragment_portfolio, container, false);
        addButton = (Button) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        removeHistory = (Button) view.findViewById(R.id.ClearHistory);
        removeHistory.setOnClickListener(this);
        subtractBtn = (Button) view.findViewById(R.id.subtractBtn);
        subtractBtn.setOnClickListener(this);
        convertBtn = (Button) view.findViewById(R.id.convertBtn);
        convertBtn.setOnClickListener(this);
        allInDolars = (TextView) view.findViewById(R.id.Dollars);
        listView = (ListView) view.findViewById(R.id.valiutos);
        listAdapterPortfolio = new list_adapter_portfolio(this, preferences, stockArr);
        listView.setAdapter(listAdapterPortfolio);
        setDollars();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ClearHistory:
                DialogClass confirmDialog = new DialogClass();
                confirmDialog.Confirm(getActivity(), "Confirm", "This will delete all your portfolio", "Cancel", "OK", aproc());
                break;

            case R.id.addButton:

                for (int i = 0; i < list.size() ; i++) {
                Log.d("ARraY", stockArr[i]);
                }
                openDialog(true);
                break;

            case R.id.subtractBtn:
                openDialog(false);
                break;

            case R.id.convertBtn:
                convertDialog dialog = new convertDialog(getActivity());
                dialog.show();
                break;

        }
    }



    private  void openDialog(final boolean isToAdd) { //teigiamas pridet, neigiamas atimt
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.add_dialog, null);

        ArrayAdapter<String > adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,  public_stuff.sortedTOP);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner1 = (Spinner) view.findViewById(R.id.droplist);
        spinner1.setAdapter(adapter);
        final EditText editText = (EditText) view.findViewById(R.id.moneyEdit);
        builder.setView(view);
        final AlertDialog dialog =  builder.create();
        dialog.show();

        Button button = view.findViewById(R.id.addCryptoButton);
        if(!isToAdd) button.setText("sub");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText())) { //jei netuscias laukelis
                    String valiuta = spinner1.getSelectedItem().toString();
                    if (!list.contains(valiuta))
                    list.add(list.size(), spinner1.getSelectedItem().toString());
                    saveArrayList();
                    if(isToAdd)saveSum(valiuta,  Float.valueOf(editText.getText().toString()));
                    else saveSum(valiuta,  - Float.valueOf(editText.getText().toString())); //minusiukas
                    dialog.dismiss();
                    setDollars();
                }
            }
        });


    }

     void saveSum(String pav, float kiek){
         SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         float dabartinis = sharedPreferences.getFloat(pav, 0);
         dabartinis = dabartinis + kiek;
         if(dabartinis<=0) {
             dabartinis = 0;
             list.remove(pav);
             saveArrayList();
         }
         editor.putFloat(pav, dabartinis);
         editor.apply();
     }

    public void saveArrayList(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("owned", json);
        editor.apply();     // This line is IMPORTANT !!!

        stockArr = new String[list.size()];
        stockArr = list.toArray(stockArr);
        list_adapter_portfolio listAdapterPortfolio = new list_adapter_portfolio(this, preferences, stockArr);
        listView.setAdapter(listAdapterPortfolio);
    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        if(json == null) return new ArrayList<>();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public Runnable aproc(){
        return new Runnable() {
            public void run() {
                list = new ArrayList<>();
                SharedPreferences.Editor editor = preferences.edit();
                for (int i = 0; i < public_stuff.sortedTOP.length; i++) {
                    editor.putFloat(public_stuff.sortedTOP[i], 0);
                }
                editor.apply();
                saveArrayList();
                setDollars();
                //Bundle bundle = new Bundle();
                //bundle.putString("KEY_MODE", "PortfolioFragment");
                //interfaceForFragments.onActionInFragment(bundle);
            }
        };
    }

    private void setDollars() {
        float dollars=0;

        int pozicija = 0;
        for (int i = 0; i < list.size(); i++) {
            String suma = preferences.getFloat(stockArr[i], 0) + "";

                for(int x = 0; x < 10; x++) { //surast atitinkanti

                    boolean a = false;
                    if(public_stuff.sortedTOP[x].length() == stockArr[i].length()) {
                        for(int z = 0; z < public_stuff.sortedTOP[x].length(); z++) {
                            if(public_stuff.sortedTOP[x].charAt(z) != stockArr[i].charAt(z)) { a = true;}
                        }
                        if(!a) { pozicija = x; break;}
                    }


                }

            String kursas = null;
            try {
                Log.d(" pozicija ", pozicija+"");
                kursas = public_stuff.money.getJSONObject(pozicija).getString("price_usd").toString();
                dollars += Float.parseFloat(kursas)*Float.parseFloat(suma);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        allInDolars.setText("$"+dollars);
    }



}
