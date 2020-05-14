package com.scripts;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PublicStuff {

    public enum fragmentType{
        NEWS,
        PORTFOLIO,
        MONEY,
        EXPANDED,
        GRAPH_ACTIVITY
    }

    private static JSONArray money;
    private static JSONArray newsJson;
    private static boolean loadedDataOnce = false, downloadedNews = false;
    private static  int cryptoQuantity = 20;
    private static  String[] sortedTOP;
    private static fragmentType currentFragment = fragmentType.NEWS;
    private static String mainUrl = "https://min-api.cryptocompare.com/data/top/totaltoptiervolfull?limit=20&tsym=USD";
    private static int lastPositionExp = 0;

    public static void setLastPositionExp(int lastPositionExp) {
        PublicStuff.lastPositionExp = lastPositionExp;
    }

    public static int getLastPositionExp() {
        return lastPositionExp;
    }

    public static void setMoney(JSONArray money) {
        PublicStuff.money = money;
    }

    public static void setNewsJson(JSONArray newsJson) {
        PublicStuff.newsJson = newsJson;
    }

    public static void setLoadedDataOnce(boolean loadedDataOnce) {
        PublicStuff.loadedDataOnce = loadedDataOnce;
    }

    public static void setDownloadedNews(boolean downloadedNews) {
        PublicStuff.downloadedNews = downloadedNews;
    }

    public static void setSortedTOP(String[] sortedTOP) {
        PublicStuff.sortedTOP = sortedTOP;
    }

    public static void setCurrentFragment(fragmentType currentFragment) {
        PublicStuff.currentFragment = currentFragment;
    }

    public static fragmentType getCurrentFragment() {
        return currentFragment;
    }


    public static void setAllInDollars(float allInDollars) {
        AllInDollars = allInDollars;
    }

    public static void setBtcPrice(float btcPrice) {
        PublicStuff.btcPrice = btcPrice;
    }

    public static JSONArray getMoney() {
        return money;
    }

    public static JSONArray getNewsJson() {
        return newsJson;
    }

    public static boolean isLoadedDataOnce() {
        return loadedDataOnce;
    }

    public static boolean isDownloadedNews() {
        return downloadedNews;
    }

    public static int getCryptoQuantity() {
        return cryptoQuantity;
    }

    public static String[] getSortedTOP() {
        return sortedTOP;
    }


    public static String getMainUrl() {
        return mainUrl;
    }

    public static float getAllInDollars() {
        return AllInDollars;
    }

    public static float getBtcPrice() {
        return btcPrice;
    }

    public static float AllInDollars = 0;
    public static float btcPrice = 5000;


    private static JSONObject cryptoInfoFromAssets;

    public static void setCryptoInfoFromAssets(JSONObject cryptoInfoFromAssets) {
        PublicStuff.cryptoInfoFromAssets = cryptoInfoFromAssets;
    }

    public static void setGraphColorsFromAssets(JSONObject graphColorsFromAssets) {
        PublicStuff.graphColorsFromAssets = graphColorsFromAssets;
    }

    private static JSONObject graphColorsFromAssets;

    public static JSONObject getCryptoInfoFromAssets() {
        return cryptoInfoFromAssets;
    }

    public static JSONObject getGraphColorsFromAssets() {
        return graphColorsFromAssets;
    }


        //public Runnable ans_false = null;

        public static String getCryptoNameByPosition(int positionInList) {
                try {
                        return PublicStuff.money.getJSONObject(positionInList).getJSONObject("CoinInfo").getString("Name");
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                return "";
        }

/*    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    static String getAssetsText(String filename, Context context) {
        try(InputStream is = context.getAssets().open(filename)) {

            StringBuilder sb = new StringBuilder();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8 ));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "failed to read assets text!", Toast.LENGTH_LONG).show();
        }
        return "";
    }*/



}
