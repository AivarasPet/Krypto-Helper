package com.scripts;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class NewsAdapter extends BaseAdapter {

    private JSONArray jsonArray;
    private Activity context;
    private LayoutInflater inflater;

    public NewsAdapter(Activity context, JSONArray textas)
    {
        this.jsonArray = textas;
        this.context = context;
        this.inflater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {

        return 60;
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
        NewsAdapter.ViewHolder holder;
        if(convertView==null){
            holder = new NewsAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.news_body, null);
            //holder.trump = (TextView) convertView.findViewById(R.id.);
            holder.nameTxtView = (TextView) convertView.findViewById(R.id.NewsTitle);
            holder.img = (ImageView) convertView.findViewById(R.id.NewsImage);


            convertView.setTag(holder);
        }else{
            holder = (NewsAdapter.ViewHolder)convertView.getTag();
        }

        try {
            String url = jsonArray.getJSONObject(position).getString("imageurl").toString();
            Picasso.get().load(url).resize(512, 512).centerCrop().into(holder.img);
            holder.nameTxtView.setText(jsonArray.getJSONObject(position).getString("title").toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return convertView;
    }
    class ViewHolder{
        TextView nameTxtView;
        ImageView img; // atskiri
    }
}
