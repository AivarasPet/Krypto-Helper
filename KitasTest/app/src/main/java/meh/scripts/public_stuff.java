package meh.scripts;

import org.json.JSONArray;

public class public_stuff {

        public static JSONArray money;
        public static JSONArray newsJson;
        public static JSONArray visas;
        public static boolean sortedOnce = false, downloadedNews = false;
        public static  int valiutuKiekis = 10;
        public static  String[] sortedTOP;
        public static String lol;
        public static String currentFragment = "NewsFragment";
        public static int FragmentNum = 1;
        public static String mainUrl = "https://min-api.cryptocompare.com/data/top/totaltoptiervolfull?limit=10&tsym=USD";
        public static float AllInDollars = 0;

        //public Runnable ans_false = null;



}
