package meh.scripts;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

/**
 * Created by Aivaras on 2018-03-26.
 */

public class ListAdapterCrypto extends BaseAdapter
{


    private  JSONArray textas;

    private MoneyFragment context;
    private LayoutInflater inflater;
    public ListAdapterCrypto(MoneyFragment context, JSONArray textas)
    {
        this.textas = textas;
        this.context = context;
        this.inflater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        //Log.d("buuuum", textas.length()+"");
        return 10;
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
            convertView = inflater.inflate(R.layout.listo_body, null);
            //holder.trump = (TextView) convertView.findViewById(R.id.trumpinys);
            holder.pavadin = (TextView) convertView.findViewById(R.id.crypto_name);
            holder.img = (ImageView) convertView.findViewById(R.id.logo);
            holder.kaina = (TextView) convertView.findViewById(R.id.price_usd);
            holder.pokytis = (TextView) convertView.findViewById(R.id.cryptoName);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        try {
            //holder.trump.setText(textas.getJSONObject(position).getString("symbol").toString());
            holder.pavadin.setText(textas.getJSONObject(position).getJSONObject("CoinInfo").getString("FullName").toString());


                      // fotkems
            String url = "https://www.cryptocompare.com" + textas.getJSONObject(position).getJSONObject("CoinInfo").getString("ImageUrl");
                    //public_stuff.visas.getJSONObject(0).getString(public_stuff.sortedTOP[position]);
                      Picasso.get().load(url).resize(128, 128).centerCrop().into(holder.img);
                      // fotkems

            JSONObject toDisplay = textas.getJSONObject(position).getJSONObject("DISPLAY").getJSONObject("USD");
            String priceString = toDisplay.getString("PRICE").toString(); //KAINA
            //String priceUSD = String.format ("%,.2f", Double.parseDouble(priceString));
            holder.kaina.setText(priceString);
            String pokytis = toDisplay.getString("CHANGEPCTDAY");
            if(pokytis.startsWith("-")) {
               // pokytis.substring(2, pokytis.length());
                pokytis = pokytis.replace("-","");
                holder.pokytis.setText("▼"+pokytis+"%");
            }
            else holder.pokytis.setText("▲" + pokytis + "%");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            System.out.println("null EXCEPTION");
        }


        return convertView;
    }
    class ViewHolder{
        TextView pavadin, kaina, pokytis;
        ImageView img; // atskiri

    }
}


//jei ka:
/*
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
*/