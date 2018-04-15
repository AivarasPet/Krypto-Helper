package meh.kitastest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
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

public class graph_adapter {

    TextView txt;
    public GraphView graph;
    Context context;

    public void run(String url) {
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
            JSONArray textas = obj.getJSONArray("Data");
            //Log.d("myTag", textas.getJSONObject(0).getString("symbol").toString());
            //Log.d("Log.d", textas.length()+"");
            String laikas;

            float b;
            int l;
            Date date;
            final String[] diena = new String[32];

        //KREIVE
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            for(int x=0; x<30; x++) {
                laikas = textas.getJSONObject(x).getString("time").toString();
                l = Integer.parseInt(laikas);
                date = new java.util.Date(l*1000L);
                diena[x] = (String) android.text.format.DateFormat.format("MM/dd",  date);
                b = Float.parseFloat(textas.getJSONObject(x).getString("close").toString());
                series.appendData(new DataPoint(x, b), true, 30);
            }
        //X IR Y ASYS (LABELIAI)
            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        // show  x values
                        //return diena[Integer.parseInt(""+value)];
                        int x = (int ) value;
                        Log.d("CRash", x+" "+value);
                        return diena[x];
                    } else {
                        // show  y values
                        return super.formatLabel(value, isValueX)+"$";
                    }
                }
            });
            //testas.setText(a);

        //TEKSTAS
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    txt.setText("$"+dataPoint.getY());
                }
            });

        //SPALVOS
            series.setColor(Color.parseColor("#f7931a"));
            series.setThickness(10);
            series.setDrawBackground(true);
            series.setBackgroundColor(Color.parseColor("#fcd19c"));


        //xzn kazka pagrazina???
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setPathEffect(new DashPathEffect(new float[]{18, 5}, 0));
            series.setCustomPaint(paint);


            graph.setTitle("BTC");
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(20);
            graph.getViewport().setMaxX(30);

            //graph.getViewport().setYAxisBoundsManual(true);
            //graph.getViewport().setMinY(textas.);
            //graph.getViewport().setMaxY(12000);
        //SCALABLE
            graph.getViewport().setScalable(true);
            graph.addSeries(series);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,"Check your internet connection!",Toast.LENGTH_LONG).show();
        }
    }
}
