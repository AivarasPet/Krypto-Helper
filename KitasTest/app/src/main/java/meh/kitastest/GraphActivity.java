package meh.kitastest;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


    String[] A = new String[30];
    private void toliau(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            JSONArray textas = obj.getJSONArray("Data");
            //Log.d("myTag", textas.getJSONObject(0).getString("symbol").toString());
            //Log.d("Log.d", textas.length()+"");
            String a = "";
            float b;

            GraphView graph = (GraphView) findViewById(R.id.graph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            for(int x=0; x<30; x++) {
                b = Float.parseFloat(textas.getJSONObject(x).getString("close").toString());
                //Date date = new java.util.Date(b*1000L);
                series.appendData(new DataPoint(x, b), true, 30);
                //new DataPoint(x, Integer.parseInt(textas.getJSONObject(x).getString("close").toString()));
                //a +=   textas.getJSONObject(x).getString("close").toString() +  "  " + date  + "\n";
            }


            //testas.setText(a);

            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    testas.setText("$"+dataPoint.getY());
                }
            });

            graph.setTitle("BTC");
            graph.getViewport().setScalable(true);
            graph.getViewport().setMinX(0.1);
            graph.addSeries(series);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Check your internet connection!",Toast.LENGTH_LONG).show();
        }
    }
}
