package meh.kitastest;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyFragment extends Fragment {

    ListView list;
    InterfaceForFragments interfaceForFragments;
    list_adapter_crypto adapter;
    boolean sukurtasAdapteris = false;

    public MoneyFragment() {
        // Required empty public constructor
    }

    public  void setInterfaceForFragments(InterfaceForFragments interfaceForFragments) {
        this.interfaceForFragments = interfaceForFragments;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_money, container, false);
        list = (ListView) view.findViewById(R.id.listView);
        makeTheList();
        setInterfaceForFragments(interfaceForFragments);
        return view;
    }


    private void makeTheList() {
        if(!sukurtasAdapteris) {adapter = new list_adapter_crypto(this, public_stuff.money); sukurtasAdapteris = true;}
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(interfaceForFragments!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("KEY_POSITION", i);
                    bundle.putString("KEY_MODE", "ListClick");
                    interfaceForFragments.onActionInFragment(bundle);

                }

                //Intent intent = new Intent(getActivity(), currency_everything.class);
            //intent.putExtra("kelintas", i);
            //startActivity(intent);
         }
        });
    }

}
