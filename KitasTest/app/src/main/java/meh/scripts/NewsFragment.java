package meh.scripts;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class NewsFragment extends Fragment  {

    ListView list;
    news_adapter adapter;
    boolean sukurtasAdapteris = false;

    public NewsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(public_stuff.downloadedNews == false) {
            data_download dl = new data_download(new data_download.AsyncCallback() {
                @Override
                public void gavauData(String result) {
                    toliau(result);
                }
            });
            dl.webSite = " https://min-api.cryptocompare.com/data/news/?lang=EN"; //https://api.coinmarketcap.com/v1/ticker/?limit=10
            dl.execute();
        }  // Streakai
        else makeList();

        public_stuff.currentFragment = "NewsFragment";

        View view = inflater.inflate(R.layout.fragment_money, container, false);
        list = (ListView) view.findViewById(R.id.listView);

        return view;
    }




    private void toliau(String result) {
        try {
            final JSONArray textas = new JSONArray(result);
            public_stuff.newsJson = textas;
            makeList();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void makeList() {

        if(!sukurtasAdapteris) { adapter = new news_adapter(getActivity(), public_stuff.newsJson); sukurtasAdapteris = true; }
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), WebsiteActivity.class);
                String url = null;
                try {
                    url = public_stuff.newsJson.getJSONObject(i).getString("guid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }

}
