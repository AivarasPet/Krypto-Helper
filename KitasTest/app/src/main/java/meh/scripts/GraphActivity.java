package meh.scripts;

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
import com.squareup.picasso.Picasso;

import org.json.JSONException;


public class GraphActivity extends AppCompatActivity {




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
    String url;


    //getResources().getStringArray(R.array.your_array)[position]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    @Override
    public void onBackPressed() {
        finish();
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


        ArrayAdapter<String > adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  public_stuff.sortedTOP);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1 = (Spinner) findViewById(R.id.CmpDrop1);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String url = null;   // fotkems
                try {
                    url = public_stuff.visas.getJSONObject(0).getString(public_stuff.sortedTOP[position]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Picasso.get().load(url).resize(128, 128).centerCrop().into(img);        // fotkemsimg.setImageResource(imgid[position]);
                createClass(position, graph1);
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
        // STAI KODAS GAUT URL: String url = )public_stuff.visas.getJSONObject(1).getString(public_stuff.sortedTOP[position];   // 1 yra menesio 2 - paros

        if(grafik==graph1) {
            naujas1 = new graph_adapter();
            naujas1.graph = grafik;
            naujas1.txt = kaina;
            naujas1.datosTxt = datosTxt;
            naujas1.pav = public_stuff.sortedTOP[position];
            try {
                String spalva = public_stuff.visas.getJSONObject(3).getString(public_stuff.sortedTOP[position]).toString();
                naujas1.spalva = spalva;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            naujas1.mode = mode;
            naujas1.context = getApplicationContext();
            if (mode == 0) {
                String url = null;
                try {
                    url = public_stuff.visas.getJSONObject(1).getString(public_stuff.sortedTOP[position]).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                naujas1.run(url);
            }
            else { //mode == 1
                try {
                    url = public_stuff.visas.getJSONObject(2).getString(public_stuff.sortedTOP[position]).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                naujas1.run(url);
                 }
        }
        else if(grafik==graph2) {
            naujas2 = new graph_adapter();
            naujas2.graph = grafik;
            naujas2.txt = kaina;
            naujas2.datosTxt = datosTxt;
            naujas2.pav = public_stuff.sortedTOP[position];
            try {
                String spalva = public_stuff.visas.getJSONObject(3).getString(public_stuff.sortedTOP[position]).toString();
                naujas2.spalva = spalva;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            naujas2.mode = mode;
            naujas2.context = getApplicationContext();
            if (mode == 0) {
                String url = null;
                try {
                    url = public_stuff.visas.getJSONObject(1).getString(public_stuff.sortedTOP[position]).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                naujas2.run(url);
            }
            else {
                try {
                    url = public_stuff.visas.getJSONObject(2).getString(public_stuff.sortedTOP[position]).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                naujas2.run(url);
            }
        }
    }





}
