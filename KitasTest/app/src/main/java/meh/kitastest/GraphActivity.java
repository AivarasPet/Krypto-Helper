package meh.kitastest;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.hardware.camera2.params.RggbChannelVector;
import android.icu.text.DateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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


public class GraphActivity extends AppCompatActivity {

    TextView testas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        testas = (TextView) findViewById(R.id.TestInGraph);

        data_download dl = new data_download(new data_download.AsyncCallback() {
            @Override
            public void gavauData(String result) {
                toliau(result);
            }
        });
        dl.webSite = "https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG";
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


            GraphView graph = (GraphView) findViewById(R.id.graph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            for(int x=0; x<30; x++) {
                laikas = textas.getJSONObject(x).getString("time").toString();
                l = Integer.parseInt(laikas);
                date = new java.util.Date(l*1000L);
                diena[x] = (String) android.text.format.DateFormat.format("MM/dd",  date);
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
                        Log.d("CRash", x+" "+value);
                        return diena[x];
                    } else {
                        // show  y values
                        return super.formatLabel(value, isValueX)+"$";
                    }
                }
            });
            //testas.setText(a);

            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    testas.setText("$"+dataPoint.getY());
                }
            });

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
            graph.getViewport().setScalable(true);
            graph.addSeries(series);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Check your internet connection!",Toast.LENGTH_LONG).show();
        }
    }
}
