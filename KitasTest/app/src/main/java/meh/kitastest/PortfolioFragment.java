package meh.kitastest;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PortfolioFragment extends Fragment implements View.OnClickListener {



    ArrayList<String> list ;
    ListView listView;
    Button addButton, removeHistory, subtractBtn;
    SharedPreferences preferences;
    String[] stockArr;
    public PortfolioFragment() {
        // Required empty public constructor
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

        list_adapter_portfolio listAdapterPortfolio = new list_adapter_portfolio(this, preferences, stockArr);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stockArr);
        //for (int i = 0; i < list.size() ; i++) {
        //    Log.d("ARraY", stockArr[i]);
        //}
        // Assign adapter to ListView
//        listView.setAdapter(adapter);


        View view  = inflater.inflate(R.layout.fragment_portfolio, container, false);
        addButton = (Button) view.findViewById(R.id.addButton);
        addButton.setOnClickListener( this);
        removeHistory = (Button) view.findViewById(R.id.ClearHistory);
        removeHistory.setOnClickListener(this);
        subtractBtn = (Button) view.findViewById(R.id.subtractBtn);
        subtractBtn.setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.valiutos);
        String[] array = new String[list.size()];
        array = list.toArray(array);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, array);
        listView.setAdapter(listAdapterPortfolio);
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


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stockArr);
            for (int i = 0; i < list.size() ; i++) {
                Log.d("ARraY", stockArr[i]);
            }
           openDialog();
            break;

        }
    }



    private  void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.add_dialog, null);

        ArrayAdapter<String > adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,  public_stuff.sortedTOP);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner1 = (Spinner) view.findViewById(R.id.droplist);
        spinner1.setAdapter(adapter);
        Button button = view.findViewById(R.id.addCryptoButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!list.contains(spinner1.getSelectedItem().toString()))list.add(list.size(), spinner1.getSelectedItem().toString());
                saveArrayList();
                //indexOF kas pasiimt elementa
            }
        });

        builder.setView(view);
        AlertDialog dialog =  builder.create();
        dialog.show();
    }

    public void saveArrayList(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("owned", json);
        editor.apply();     // This line is IMPORTANT !!!
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
                saveArrayList();
            }
        };
    }



}
