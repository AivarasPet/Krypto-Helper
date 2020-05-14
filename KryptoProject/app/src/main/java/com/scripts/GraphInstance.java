package com.scripts;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static java.lang.Math.round;


public class GraphInstance {

    public interface GraphObserver {
        void getDates(String firstDate, String secondDate);
    }

    GraphObserver graphObserver;
    public GraphView graph;
    Context context;
    public String nameStr ="", colour ="";
    public boolean isDaily = true;
    public  boolean isPreview, isCompare;
    LineGraphSeries <DataPoint> series;
    String name;

    public void run(String name, boolean isDaily) {
        this.name = name;
        this.isDaily = isDaily;
        graph.removeAllSeries();
        HttpRequestDownload dl = new HttpRequestDownload(new HttpRequestDownload.AsyncCallback() {
            @Override
            public void gavauData(String result) {
                afterDownload(result);
                //Toast.makeText(context,"Check connection!",Toast.LENGTH_LONG).show();
            }
        });
        String graphTypeStr = "histoday";
        if(!isDaily) graphTypeStr = "histohour";
        dl.webSite =  "https://min-api.cryptocompare.com/data/" +  graphTypeStr + "?fsym=" +  name + "&tsym=USD&limit=50&aggregate=1&e=CCCAGG";
        dl.execute();
    }

    private void afterDownload(String result) {
        if(result.isEmpty()) {  Toast.makeText(context, "Check your internet connection!", Toast.LENGTH_LONG).show(); return;}

        try {
            JSONObject obj = new JSONObject(result);
            final JSONArray textas = obj.getJSONArray("Data");
            final String[] time = new String[1];

            float b;
            final int[] l = new int[1];
            final Date[] date = new Date[1];
            final String[] day = new String[51];


            series = new LineGraphSeries<>();//LineGraphSeries<DataPoint>
            for(int x=0; x<50; x++) {
                time[0] = textas.getJSONObject(x).getString("time").toString();
                l[0] = Integer.parseInt(time[0]);
                date[0] = new java.util.Date(l[0] *1000L);
                if(isDaily)
                    day[x] = (String) android.text.format.DateFormat.format("MM/dd", date[0]);
                else
                    day[x] =  android.text.format.DateFormat.format("dd/kk", date[0]).toString() + ":00";


                b = Float.parseFloat(textas.getJSONObject(x).getString("close"));
                series.appendData(new DataPoint(x, b), true, 50);
            }

            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        int x = (int) value;
                        final String toReturn = day[x];
                        return toReturn;
                    } else {
                        // show  y values
                        if(series.getLowestValueY()  < value) return super.formatLabel(value, isValueX)+"$";
                        else return "";
                    }
                }
            });

            if(!isPreview) {
                        graph.setCursorMode(true);
            }

            try {
                String colour = PublicStuff.getGraphColorsFromAssets().getString(name);
                if(colour.equals("") == false) {
                    series.setColor(Color.parseColor(colour));
                    colour = "#50" + colour.substring(1);
                    series.setBackgroundColor(Color.parseColor(colour));
                }
            }
            catch (JSONException e) {

            }

            series.setThickness(10);
            series.setDrawBackground(true);

            graph.getGridLabelRenderer().setTextSize(38f);
            graph.getGridLabelRenderer().setNumHorizontalLabels(10);
            graph.setTitle(name);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(49);
            if(!isPreview) graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
            graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
            graph.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );
            graph.getViewport().setDrawBorder(true);
            graph.getViewport().setScrollable(false);

            graph.addSeries(series);
            if(graphObserver != null)
            {
                graphObserver.getDates(day[0], day[49]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,"Downloaded data is corrupted!",Toast.LENGTH_LONG).show();
        }
    }

    public void setGraphObserver(GraphObserver observer) {
        graphObserver = observer;
    }
}


