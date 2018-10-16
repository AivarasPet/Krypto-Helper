package meh.kitastest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class public_stuff {

        public static JSONArray money;
        public static JSONArray newsJson;
        public static JSONArray visas;
        public static boolean sortedOnce = false, downloadedNews = false;
        public static  int valiutuKiekis = 10;
        public static  String[] sortedTOP;
        public static String lol;
        public static int FragmentNum = 1;
        public static String mainUrl = "https://api.coinmarketcap.com/v1/ticker/?limit=10";

        //public Runnable ans_false = null;



}
