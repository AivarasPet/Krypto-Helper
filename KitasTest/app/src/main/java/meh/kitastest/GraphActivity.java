package meh.kitastest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

import javax.net.ssl.SSLPeerUnverifiedException;


public class GraphActivity extends AppCompatActivity{

    TextView kaina, datosTxt;
    GraphView graph, graph1, graph2;
    RadioGroup radioGroup;
    Button compareBtn, exitBtn;
    Spinner spinner1, spinner2;
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

        kaina = (TextView) findViewById(R.id.TestInGraph);
        datosTxt = (TextView) findViewById(R.id.DateInGraph);
        graph1 = (GraphView) findViewById(R.id.graph1);
        compareBtn = (Button) findViewById(R.id.CompareBtn);
        compareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compareWindow();
            }
        });

        DefineStuff(1); //kai normalus langas

        //createClass(0, graph); //pradziai




    }

    private void compareWindow() { //viskas comparui
        setContentView(R.layout.compare_graph);
        graph1 = (GraphView) findViewById(R.id.graph1);
        graph2 = (GraphView) findViewById(R.id.graph2);
        kaina = (TextView) findViewById(R.id.TestInGraph);
        datosTxt = (TextView) findViewById(R.id.DateInGraph);
        exitBtn = (Button) findViewById(R.id.exitCompareBtn);
        //createClass(0, graph1);
        //createClass(1, graph2);

        DefineStuff(2); //kai compare ijungtas


        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
    }

    private void DefineStuff(final int kiekis) {


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cryptoNames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1 = (Spinner) findViewById(R.id.CmpDrop1);
        spinner1.setAdapter(adapter);

        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                createClass(position, graph1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        if(kiekis == 2) { // jei compare ijungtas
            spinner2 = (Spinner) findViewById(R.id.CmpDrop2);
            spinner2.setAdapter(adapter);
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    createClass(position, graph2);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });

        }

// Apply the adapter to the spinner






        radioGroup = (RadioGroup) findViewById(R.id.Rbuttons);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton2) mode = 1;
                else mode = 0;
                //Log.d("bam", mode+"");
                createClass(spinner1.getSelectedItemPosition(), graph1);
                if(kiekis==2) createClass(spinner2.getSelectedItemPosition(), graph2);
            }
        });

    }

    private void createClass(int position, GraphView grafik) {
        graph_adapter naujas = new graph_adapter();
        naujas.graph = grafik;
        naujas.txt = kaina;
        naujas.datosTxt = datosTxt;
        naujas.pav = getResources().getStringArray(R.array.cryptoNames)[position];
        naujas.spalva = getResources().getStringArray(R.array.ColorsGraph)[position];
        naujas.mode = mode;
        naujas.context = getApplicationContext();
        if (mode == 0)naujas.run(url[position]);
        else naujas.run(url2[position]);
    }





}
