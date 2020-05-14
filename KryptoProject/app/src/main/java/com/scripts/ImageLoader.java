package com.scripts;

import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

class ExistingCryptoImage {
    int cryptoPositionInList;
    ImageView imageView;
    public ExistingCryptoImage(ImageView imageView, int cryptoPositionInList) {
        this.imageView = imageView;
        this.cryptoPositionInList = cryptoPositionInList;
    }

    public int getCryptoPositionInList() {return cryptoPositionInList;}

    @Override
    public int hashCode() {
        return cryptoPositionInList;
    }
}

public class ImageLoader {

    private static ImageLoader instance = null;
    HashMap<Integer, ExistingCryptoImage> list;

    public static ImageLoader getInstance() {
        if(instance == null)
        instance = new ImageLoader();
        return instance;
    }

    private ImageLoader() {
       list = new HashMap<>();
    }

    public  void loadImageByCryptoPosition(ImageView imageView, int cryptoPositionInList, int width, int height) {
            if(list.containsKey(cryptoPositionInList)) {
                imageView.setImageDrawable(list.get(cryptoPositionInList).imageView.getDrawable());
                return;
            }

            String url = null;
        try {
            url = "https://www.cryptocompare.com" + PublicStuff.getMoney().getJSONObject(cryptoPositionInList).getJSONObject("CoinInfo").getString("ImageUrl");
            Picasso.get().load(url).resize(width, height).centerCrop().into(imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //public_stuff.visas.getJSONObject(0).getString(public_stuff.sortedTOP[position]);
    }
}
