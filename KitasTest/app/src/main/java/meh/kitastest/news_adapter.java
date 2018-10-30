package meh.kitastest;

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

public class news_adapter extends BaseAdapter {

    private JSONArray textas;
    private Activity context;
    private LayoutInflater inflater;

    public news_adapter(Activity context, JSONArray textas)
    {
        this.textas = textas;
        this.context = context;
        this.inflater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        //Log.d("buuuum", textas.length()+"");
        return 60;//textas.length();
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
        news_adapter.ViewHolder holder;
        if(convertView==null){
            holder = new news_adapter.ViewHolder();
            convertView = inflater.inflate(R.layout.news_body, null);
            //holder.trump = (TextView) convertView.findViewById(R.id.);
            holder.pavadin = (TextView) convertView.findViewById(R.id.NewsTitle);
            holder.img = (ImageView) convertView.findViewById(R.id.NewsImage);


            convertView.setTag(holder);
        }else{
            holder = (news_adapter.ViewHolder)convertView.getTag();
        }

        try {
            String url = textas.getJSONObject(position).getString("imageurl").toString();
            Picasso.get().load(url).resize(512, 512).centerCrop().into(holder.img);
            holder.pavadin.setText(textas.getJSONObject(position).getString("title").toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return convertView;
    }
    class ViewHolder{
        TextView pavadin;
        ImageView img; // atskiri

    }
}
