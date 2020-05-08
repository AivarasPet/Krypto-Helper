package meh.scripts;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static java.lang.Math.round;

public class graph_adapter {





    TextView txt, datosTxt;
    public GraphView graph;
    Context context;
    public String pav="", spalva="";
    public int mode;// 0 monthly, 1 hourly
    public  boolean isPreview;
    LineGraphSeries <DataPoint> series;

    public void run(String url) {
        graph.removeAllSeries();
        data_download dl = new data_download(new data_download.AsyncCallback() {
            @Override
            public void gavauData(String result) {
                toliau(result);
                //Toast.makeText(context,"Check connection!",Toast.LENGTH_LONG).show();
            }
        });
        dl.webSite = url;
        dl.execute();
    }

    private void toliau(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            final JSONArray textas = obj.getJSONArray("Data");
            //Log.d("myTag", textas.getJSONObject(0).getString("symbol").toString());
            //Log.d("Log.d", textas.length()+"");
            final String[] laikas = new String[1];

            float b;
            final int[] l = new int[1];
            final Date[] date = new Date[1];
            final String[] diena = new String[32];


            series = new LineGraphSeries<>();//LineGraphSeries<DataPoint>
            for(int x=0; x<30; x++) {
                laikas[0] = textas.getJSONObject(x).getString("time").toString();
                l[0] = Integer.parseInt(laikas[0]);
                date[0] = new java.util.Date(l[0] *1000L);
                if(mode==0) diena[x] = (String) android.text.format.DateFormat.format("MM/dd", date[0]);
                else diena[x] = (String)  android.text.format.DateFormat.format("kk", date[0]);
                b = Float.parseFloat(textas.getJSONObject(x).getString("close").toString());
                series.appendData(new DataPoint(x, b), true, 30);
            }

            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        // show  x values
                        //return diena[Integer.parseInt(""+value)];
                        int x = (int ) value;
                        //Log.d("CRash", x+" "+value);
                        if(value%2==0) return diena[x];
                        else return null;
                    } else {
                        // show  y values
                        return super.formatLabel(value, isValueX)+"$";
                    }
                }
            });
            //testas.setText(a);

            if(!isPreview) {
                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        //double kaina = Math.round(dataPoint.getY());
                        String numberAsString = String.format ("%,.2f", dataPoint.getY());
                        txt.setText("$" + numberAsString);
                        int x = (int) dataPoint.getX();
                        try {
                            laikas[0] = textas.getJSONObject(x).getString("time").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        l[0] = Integer.parseInt(laikas[0]);
                        date[0] = new java.util.Date(l[0] * 1000L);
                        if (mode == 0)
                            datosTxt.setText(android.text.format.DateFormat.format("MM/dd", date[0]));
                        else
                            datosTxt.setText(android.text.format.DateFormat.format("kk", date[0]) + "hr");

                        Log.d("dx", " veik");

                        graph.removeAllSeries();
                        LineGraphSeries <DataPoint> kursorius =  new LineGraphSeries<>();
                        double kiekPridet = (series.getHighestValueY()-series.getLowestValueY())/10;
                        kursorius.appendData(new DataPoint(x, dataPoint.getY()-kiekPridet), true, 2);
                        kursorius.appendData(new DataPoint(x, dataPoint.getY()+kiekPridet), true, 2);
                        kursorius.setColor(Color.parseColor("#002080"));
                        kursorius.setThickness(20);
                        graph.addSeries(kursorius);
                        graph.addSeries(series);

                    }
                });
            }

            series.setColor(Color.parseColor(spalva));
            series.setThickness(10);


            graph.getGridLabelRenderer().setTextSize(38f);
            graph.getGridLabelRenderer().setNumHorizontalLabels(10);
            graph.getGridLabelRenderer().setHorizontalLabelsAngle(10);
            graph.setTitle(pav);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(18);
            graph.getViewport().setMaxX(29);


            graph.getViewport().setScrollable(true);
            graph.addSeries(series);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,"Check your internet connection!",Toast.LENGTH_LONG).show();
        }
    }
}


