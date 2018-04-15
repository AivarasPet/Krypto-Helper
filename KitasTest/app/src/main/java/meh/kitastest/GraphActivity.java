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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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


public class GraphActivity extends MainActivity{

    TextView testas;
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableTheme(useLightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        testas = (TextView) findViewById(R.id.TestInGraph);
        graph = (GraphView) findViewById(R.id.graph);

//  GETTING AN ARRAY
//        getResources().getStringArray(R.array.menu);

        Spinner spinner = (Spinner) findViewById(R.id.dropDown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cryptoNames, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
         spinner.setAdapter(adapter);
        //spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           // @Override
           // public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

          //  }
        //});


        graph_adapter naujas = new graph_adapter();
        naujas.graph = graph;
        naujas.txt = testas;
        naujas.context = getApplicationContext();
        naujas.run("https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG");



    }





}
