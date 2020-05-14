package com.scripts;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Aivaras on 2018-03-26.
 */

public class ListAdapterPortfolio extends BaseAdapter
{


    private  JSONArray moneyJsonArray;
    private String[] list;
    PortfolioFragment context;
    private LayoutInflater inflater;
    SharedPreferences preferences;

    public ListAdapterPortfolio(PortfolioFragment context, SharedPreferences preferences, String[] list)
    {
        this.moneyJsonArray = PublicStuff.getMoney();
        this.context = context;
        this.inflater = context.getLayoutInflater();
        this.list = list;
        this.preferences = preferences;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return null; //textas.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        //JSONObject jsonObject = textas.optJSONObject(position);
        //return jsonObject.optLong("id");
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.portfolio_listo_body, null);
            holder.pavadin = (TextView) convertView.findViewById(R.id.cryptoName);
            holder.img = (ImageView) convertView.findViewById(R.id.portfolioImage);
            holder.kiekis = (TextView) convertView.findViewById(R.id.cryptoAmount);
            holder.kiekisDol = (TextView) convertView.findViewById(R.id.CryptoDol);
            //Log.d("ARO", "DARO");
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        try {
            String pav = list[position];
            holder.pavadin.setText(pav);

            String suma = preferences.getFloat(pav, 0) + "";
            holder.kiekis.setText(suma);

            for (int i = 0; i < 10; i++) {

                boolean a = false;
                if(PublicStuff.getSortedTOP()[i].length() == list[position].length()) {
                    Log.d(" pav ", PublicStuff.getSortedTOP()[i]);
                    for(int x = 0; x < PublicStuff.getSortedTOP()[i].length(); x++) {
                        if(PublicStuff.getSortedTOP()[i].charAt(x) != list[position].charAt(x)) { a = true;}
                    }
                    if(!a) { position = i; break;}
                }

                //}
            }
            double rate = Double.parseDouble(moneyJsonArray.getJSONObject(position).getJSONObject("RAW").getJSONObject("USD").getString("PRICE"));;
            double inDollars = rate*Float.parseFloat(suma);
            PublicStuff.AllInDollars += inDollars;
            holder.kiekisDol.setText("$"+inDollars);
            Log.d(" pozicija ", ""+position);
            ImageLoader.getInstance().loadImageByCryptoPosition(holder.img, position, 256, 256);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }


        return convertView;
    }
    class ViewHolder{
        TextView pavadin, kiekis, kiekisDol;
        ImageView img; // atskiri

    }
}


