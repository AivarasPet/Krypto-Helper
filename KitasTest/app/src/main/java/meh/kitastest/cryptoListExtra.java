package meh.kitastest;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aivaras on 2018-03-20.
 */

public class cryptoListExtra extends ArrayAdapter<String>{

    private String[] trumpiniai;
    private String[] valiutos;
    private Integer[] imgid;
    private Activity context;


    public cryptoListExtra(Activity context, String[] trumpiniai, String[] valiutos, Integer[] imgid) {
        super(context, R.layout.activity_crypto_list, trumpiniai);
        this.context = context;
        this.trumpiniai = trumpiniai;
        this.imgid = imgid;
        this.valiutos = valiutos;
    }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }

        class ViewHolder
        {
            TextView txt1, txt2;
            ImageView img1;
            ViewHolder(View v) {
                txt1 = (TextView) v.findViewById(R.id.trumpiniai);

            }

        }
}
