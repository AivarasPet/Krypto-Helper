package meh.kitastest;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aivaras on 2018-03-26.
 */

public class list_adapter_crypto extends BaseAdapter
{
    private String[] trumpinys;
    private String[] pavadinimas;
    private Integer[] imgid;

    private Activity context;
    private LayoutInflater inflater;
    public list_adapter_crypto(Activity context,String[] name, String[] mobile_no,  Integer[] imgid)
    {
        this.context =context;
        this.trumpinys= name;
        this.pavadinimas = mobile_no;
        this.imgid = imgid;
        this.inflater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return trumpinys.length;
    }

    @Override
    public Object getItem(int position) {
        return trumpinys[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listo_body, null);
            holder.trump = (TextView) convertView.findViewById(R.id.trumpinys);
            holder.pavadin = (TextView) convertView.findViewById(R.id.pavadinimas);
            holder.img = (ImageView) convertView.findViewById(R.id.logo);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.trump.setText(trumpinys[position]);
        holder.pavadin.setText(pavadinimas[position]);
        holder.img.setImageResource(imgid[position]);

        return convertView;
    }
    class ViewHolder{
        TextView trump, pavadin;
        ImageView img; // atskiri

    }
}
