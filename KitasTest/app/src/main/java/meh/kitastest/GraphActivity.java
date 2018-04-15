package meh.kitastest;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.hardware.camera2.params.RggbChannelVector;
import android.icu.text.DateFormat;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
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


public class GraphActivity extends AppCompatActivity{

    TextView testas, datosTxt;
    GraphView graph;
    RadioGroup radioGroup;
    private int mode=0;

    String[] url = {
            "https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=ETH&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=XRP&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=BCH&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=LTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=EOS&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=ADA&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=XLM&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=NEO&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=IOTA&tsym=USD&limit=30&aggregate=1&e=CCCAGG"
    };
    String[] url2 = {
            "https://min-api.cryptocompare.com/data/histohour?fsym=BTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=ETH&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=XRP&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=BCH&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=LTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=EOS&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=ADA&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=XLM&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=NEO&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=IOTA&tsym=USD&limit=30&aggregate=1&e=CCCAGG"
    };
    //getResources().getStringArray(R.array.your_array)[position]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        testas = (TextView) findViewById(R.id.TestInGraph);
        datosTxt = (TextView) findViewById(R.id.DateInGraph);
        graph = (GraphView) findViewById(R.id.graph);

        final Spinner spinner = (Spinner) findViewById(R.id.dropDown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cryptoNames, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
         spinner.setAdapter(adapter);
         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               //Toast.makeText(getApplicationContext(), position, Toast.LENGTH_LONG);
                //Log.d("D", position+"");
                createClass(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        radioGroup = (RadioGroup) findViewById(R.id.Rbuttons);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == 2131296375) mode = 1;
                else mode = 0;
                //Log.d("bam", mode+"");
                createClass(spinner.getSelectedItemPosition());
            }
        });

        createClass(0);




    }

    private void createClass(int position) {
        graph_adapter naujas = new graph_adapter();
        naujas.graph = graph;
        naujas.txt = testas;
        naujas.datosTxt = datosTxt;
        naujas.pav = getResources().getStringArray(R.array.cryptoNames)[position];
        naujas.spalva = getResources().getStringArray(R.array.ColorsGraph)[position];
        naujas.mode = mode;
        naujas.context = getApplicationContext();
        if (mode == 0)naujas.run(url[position]);
        else naujas.run(url2[position]);
    }





}
