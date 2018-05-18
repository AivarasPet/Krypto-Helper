package meh.kitastest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

import javax.net.ssl.SSLPeerUnverifiedException;


public class GraphActivity extends MainActivity{

    Integer[] imgid={R.drawable.btc, R.drawable.eth, R.drawable.ripple, R.drawable.bitcoin_cash, R.drawable.eos,
            R.drawable.litecoin, R.drawable.cardano, R.drawable.stellar, R.drawable.iota,
            R.drawable.tron };

    TextView kaina, datosTxt;
    ImageView img;
    GraphView graph1, graph2;
    RadioGroup radioGroup;
    Button compareBtn, exitBtn;
    Spinner spinner1, spinner2;
    SeekBar seekBar;
    private int mode=0;
    private boolean isCompare=false;
    public graph_adapter naujas1, naujas2;

    String[] url = {
            "https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=ETH&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=XRP&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=BCH&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=EOS&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=LTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=ADA&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=XLM&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=IOT&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histoday?fsym=TRX&tsym=USD&limit=30&aggregate=1&e=CCCAGG"
    };
    String[] url2 = {
            "https://min-api.cryptocompare.com/data/histohour?fsym=BTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=ETH&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=XRP&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=BCH&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=EOS&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=LTC&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=ADA&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=XLM&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=IOT&tsym=USD&limit=30&aggregate=1&e=CCCAGG",
            "https://min-api.cryptocompare.com/data/histohour?fsym=TRX&tsym=USD&limit=30&aggregate=1&e=CCCAGG"
    };
    //getResources().getStringArray(R.array.your_array)[position]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableTheme(useLightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        img = (ImageView) findViewById(R.id.graphImage);
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
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        //createClass(0, graph1);
        //createClass(1, graph2);


        DefineStuff(2); //kai compare ijungtas

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                graph1.removeAllSeries();
                graph2.removeAllSeries();
                double zymeklis = seekBar.getProgress()/10+6; // 5.5 i kaire ir desine palikt 23.5
                //zymeklis = (zymeklis - 0) * (23.5 - 5.5) / (18 - 0) + 5.5;
                Log.d("bam ", ""+zymeklis);
                graph1.getViewport().setMinX(zymeklis-6);
                graph1.getViewport().setMaxX(zymeklis+6);
                graph2.getViewport().setMinX(zymeklis-6);
                graph2.getViewport().setMaxX(zymeklis+6);
                graph1.addSeries(naujas1.series);
                graph2.addSeries(naujas2.series);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
                img.setImageResource(imgid[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Bundle bundle = getIntent().getExtras();
        if(getIntent().hasExtra("kelintas")) {
            int lol = bundle.getInt("kelintas");
            spinner1.setSelection(lol);
                    }

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

        if(grafik==graph1) {
            naujas1 = new graph_adapter();
            naujas1.graph = grafik;
            naujas1.txt = kaina;
            naujas1.datosTxt = datosTxt;
            naujas1.pav = getResources().getStringArray(R.array.cryptoNames)[position];
            naujas1.spalva = getResources().getStringArray(R.array.ColorsGraph)[position];
            naujas1.mode = mode;
            naujas1.context = getApplicationContext();
            if (mode == 0) naujas1.run(url[position]);
            else naujas1.run(url2[position]);
        }
        else if(grafik==graph2) {
            naujas2 = new graph_adapter();
            naujas2.graph = grafik;
            naujas2.txt = kaina;
            naujas2.datosTxt = datosTxt;
            naujas2.pav = getResources().getStringArray(R.array.cryptoNames)[position];
            naujas2.spalva = getResources().getStringArray(R.array.ColorsGraph)[position];
            naujas2.mode = mode;
            naujas2.context = getApplicationContext();
            if (mode == 0) naujas2.run(url[position]);
            else naujas2.run(url2[position]);
        }
    }





}
