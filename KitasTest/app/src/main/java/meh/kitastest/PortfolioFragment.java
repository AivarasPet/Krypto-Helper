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



    ArrayList<String> list;
    ListView listView;
    Button addButton;


    public PortfolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        list = new ArrayList();
        list = getArrayList("owned");

        String[] stockArr = new String[list.size()];
        stockArr = list.toArray(stockArr);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stockArr);
        for (int i = 0; i < list.size() ; i++) {
            Log.d("ARraY", stockArr[i]);
        }
        // Assign adapter to ListView
//        listView.setAdapter(adapter);

        View view  = inflater.inflate(R.layout.fragment_portfolio, container, false);
        addButton = (Button) view.findViewById(R.id.addButton);
        addButton.setOnClickListener( this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                String[] stockArr = new String[list.size()];
                stockArr = list.toArray(stockArr);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stockArr);
                for (int i = 0; i < list.size() ; i++) {
                    Log.d("ARraY", stockArr[i]);
                }
                createDialog();
                break;

        }
    }

    private  void createDialog() {
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
                list.add(list.size(), spinner1.getSelectedItem().toString());
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
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }



}
