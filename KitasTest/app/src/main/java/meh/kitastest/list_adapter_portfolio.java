package meh.kitastest;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Aivaras on 2018-03-26.
 */

public class list_adapter_portfolio extends BaseAdapter
{


    private  JSONArray textas;
    private String[] list;
    PortfolioFragment context;
    private LayoutInflater inflater;

    public list_adapter_portfolio(PortfolioFragment context, SharedPreferences preferences, String[] list)
    {
        this.textas = public_stuff.money;
        this.context = context;
        this.inflater = context.getLayoutInflater();
        this.list = list;
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
            holder.pavadin = (TextView) convertView.findViewById(R.id.crypto_name);
            holder.img = (ImageView) convertView.findViewById(R.id.portfolioImage);
            holder.kiekis = (TextView) convertView.findViewById(R.id.cryptoAmount);
            holder.kiekisDol = (TextView) convertView.findViewById(R.id.CryptoDol);
            //Log.d("ARO", "DARO");
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        try {
            holder.pavadin.setText("lol");

            Log.d("EEEEEE", list[position]);
            Log.d("FFFFFF", public_stuff.sortedTOP[position]);
                      // fotkems
            //String url = public_stuff.visas.getJSONObject(0).getString(list[position]);
            //Picasso.get().load(url).resize(128, 128).centerCrop().into(holder.img);
                      // fotkems


        }// catch (JSONException e) {
        //    e.printStackTrace();
        //}
        catch (NullPointerException e) {
            Log.d("LOOOOOOOL", "NNNNNNNNNNNNXXXXXXXXXXXXX");
            e.printStackTrace();
        }


        return convertView;
    }
    class ViewHolder{
        TextView pavadin, kiekis, kiekisDol;
        ImageView img; // atskiri

    }
}


