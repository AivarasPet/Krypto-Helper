package com.scripts;


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
    NewsAdapter adapter;
    boolean isAdapterCreated = false;

    public NewsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(PublicStuff.isDownloadedNews() == false) {
            HttpRequestDownload dl = new HttpRequestDownload(new HttpRequestDownload.AsyncCallback() {
                @Override
                public void gavauData(String result) {
                    afterDownloaded(result);
                }
            });
            dl.webSite = " https://min-api.cryptocompare.com/data/news/?lang=EN"; //https://api.coinmarketcap.com/v1/ticker/?limit=10
            dl.execute();
        }  // Streakai
        else makeList();

        PublicStuff.setCurrentFragment(PublicStuff.fragmentType.NEWS);

        View view = inflater.inflate(R.layout.fragment_money, container, false);
        list = (ListView) view.findViewById(R.id.listView);

        return view;
    }




    private void afterDownloaded(String result) {
        try {
            final JSONArray jsonArray = new JSONArray(result);
            PublicStuff.setNewsJson(jsonArray);
            makeList();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void makeList() {

        if(!isAdapterCreated) { adapter = new NewsAdapter(getActivity(), PublicStuff.getNewsJson()); isAdapterCreated = true; }
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), WebsiteActivity.class);
                String url = null;
                try {
                    url = PublicStuff.getNewsJson().getJSONObject(i).getString("guid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }

}
