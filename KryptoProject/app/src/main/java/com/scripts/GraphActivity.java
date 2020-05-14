package com.scripts;

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

import org.json.JSONException;


public class GraphActivity extends AppCompatActivity {




    TextView firstDateTxtView;
    ImageView img;
    GraphView graph1, graph2;
    RadioGroup radioGroup;
    Button compareBtn, exitBtn;
    Spinner spinner1, spinner2;
    SeekBar seekBar;
    private boolean isDaily = true;
    private boolean isCompare=false;
    public GraphInstance naujas1, naujas2;
    String url, urlKeyWord = "histoday";


    //getResources().getStringArray(R.array.your_array)[position]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        img = (ImageView) findViewById(R.id.graphImage);
        firstDateTxtView = (TextView) findViewById(R.id.TestInGraph);
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
        firstDateTxtView = (TextView) findViewById(R.id.TestInGraph);
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
                double sliderValue = seekBar.getProgress()/10+6; // 5.5 i kaire ir desine palikt 23.5
                //sliderValue = (sliderValue - 0) * (23.5 - 5.5) / (18 - 0) + 5.5;
                Log.d("bam ", ""+sliderValue);
                graph1.getViewport().setMinX(sliderValue-6);
                graph1.getViewport().setMaxX(sliderValue+6);
                graph2.getViewport().setMinX(sliderValue-6);
                graph2.getViewport().setMaxX(sliderValue+6);
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

    private void DefineStuff(final int numberOfGraphs) {


        ArrayAdapter<String > adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  PublicStuff.getSortedTOP());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1 = (Spinner) findViewById(R.id.CmpDrop1);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ImageLoader.getInstance().loadImageByCryptoPosition(img, position, 256, 256);
                createGraph(position, graph1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Bundle bundle = getIntent().getExtras();
        if(getIntent().hasExtra("kelintas")) {
            int pos = bundle.getInt("kelintas");
            PublicStuff.setLastPositionExp(pos);
            spinner1.setSelection(pos);
        }

        if(numberOfGraphs == 2) { // jei compare ijungtas
            spinner2 = (Spinner) findViewById(R.id.CmpDrop2);
            spinner2.setAdapter(adapter);
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    createGraph(position, graph2);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });

        }



        radioGroup = (RadioGroup) findViewById(R.id.Rbuttons);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton2) isDaily = false;
                else isDaily = true;
                //Log.d("bam", mode+"");
                createGraph(spinner1.getSelectedItemPosition(), graph1);
                if(numberOfGraphs==2) createGraph(spinner2.getSelectedItemPosition(), graph2);
            }
        });

    }

    private void createGraph(int position, GraphView graph) {
        // STAI KODAS GAUT URL: String url = )public_stuff.visas.getJSONObject(1).getString(public_stuff.sortedTOP[position];   // 1 yra menesio 2 - paros

        if(graph==graph1) {
            naujas1 = new GraphInstance();
            naujas1.graph = graph;
            naujas1.nameStr = PublicStuff.getSortedTOP()[position];
            naujas1.setGraphObserver(new GraphInstance.GraphObserver() {
                @Override
                public void getDates(String firstDate, String secondDate) {
                    firstDateTxtView.setText(firstDate + "     ---     " + secondDate);
                }
            });
            naujas1.context = getApplicationContext();
            naujas1.run(PublicStuff.getCryptoNameByPosition(position), isDaily);
        }
        else if(graph==graph2) {
            naujas2 = new GraphInstance();
            naujas2.graph = graph;
            naujas2.nameStr = PublicStuff.getSortedTOP()[position];
            naujas2.run(PublicStuff.getCryptoNameByPosition(position), isDaily);
        }
    }





}
