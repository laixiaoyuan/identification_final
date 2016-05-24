package edu.xlaiscu.gardenreminding;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class IdentifyOutcome extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private int maxRecId;
    IdentifyOutcomeAdaptor identifyOutcomeAdaptor;
    IdentifyOutcomeDBHelper identifyOutcomeDBHelper;

    PlantCollectionDBHelper collectionDBHelper;

    PlantDBHelper plantDBHelper;
    Cursor identifyCursor;
    Hashtable<String, Integer> waterIntervalHash;
    ListView list;
    Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_outcome);

        plantDBHelper = new PlantDBHelper(this);

        identifyOutcomeDBHelper = new IdentifyOutcomeDBHelper(this);
        identifyCursor = identifyOutcomeDBHelper.fetchAll();

        collectionDBHelper = new PlantCollectionDBHelper(this);
        waterIntervalHash = collectionDBHelper.fetchWaterInterval();
        identifyOutcomeAdaptor = new IdentifyOutcomeAdaptor(this, identifyCursor, 0);

        plant = new Plant();

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(identifyOutcomeAdaptor);
        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String plantName = identifyCursor.getString(identifyCursor.getColumnIndex("plantName"));
        String photoPath = identifyCursor.getString(identifyCursor.getColumnIndex("photoPath"));

        int waterInterval = waterIntervalHash.get(plantName);

//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        plant.plantName = plantName;
        plant.photoPath = photoPath;
        plant.lastWater = date;
        plant.waterInterval = waterInterval;
        plantDBHelper.add(plant);

        Intent intent = new Intent(IdentifyOutcome.this, MainActivity.class);
        startActivity(intent);

    }


}
