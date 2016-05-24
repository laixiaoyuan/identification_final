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
    Cursor identifyCursor;

    PlantCollectionDBHelper collectionDBHelper;

    PlantDBHelper plantDBHelper;
//    PlantAdaptor plantAdaptor;
    Cursor plantCursor;

    Hashtable<String, Integer> waterIntervalHash;
    ListView list;
    Plant plant;
    String plantName;
    String photoPath;
    Date lastWater;
    int waterInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_outcome);

        plantDBHelper = new PlantDBHelper(this);
        plantCursor = plantDBHelper.fetchAll();
//        plantAdaptor = new PlantAdaptor(this, plantCursor, 0);

        identifyOutcomeDBHelper = new IdentifyOutcomeDBHelper(this);
        identifyCursor = identifyOutcomeDBHelper.fetchAll();
        identifyOutcomeAdaptor = new IdentifyOutcomeAdaptor(this, identifyCursor, 0);

        collectionDBHelper = new PlantCollectionDBHelper(this);
        waterIntervalHash = collectionDBHelper.fetchWaterInterval();

        plant = new Plant(plantName, photoPath, lastWater, waterInterval);

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(identifyOutcomeAdaptor);
        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String plantName = identifyCursor.getString(identifyCursor.getColumnIndex("plantName"));
        String photoPath = identifyCursor.getString(identifyCursor.getColumnIndex("photoPath"));

        int waterInterval = waterIntervalHash.get(plantName);
        String lastWaterSQL;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.plantName = plantName;
        this.photoPath = photoPath;
        lastWaterSQL = dateFormat.format(date);
        this.waterInterval = waterInterval;
        plantDBHelper.add(plant);
        plantCursor.requery();
//        plantAdaptor.notifyDataSetChanged();


        Intent intent = new Intent(IdentifyOutcome.this, MainActivity.class);
        startActivity(intent);

    }


}
