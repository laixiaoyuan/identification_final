package edu.xlaiscu.gardenreminding;

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

public class IdentifyOutcome extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private int maxRecId;
    IdentifyOutcomeAdaptor identifyOutcomeAdaptor;
    IdentifyOutcomeDBHelper identifyOutcomeDBHelper;

    PlantDBHelper plantDBHelper;
    Cursor cursor;
    ListView list;
    Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_outcome);

        plantDBHelper = new PlantDBHelper(this);

        identifyOutcomeDBHelper = new IdentifyOutcomeDBHelper(this);
        cursor = identifyOutcomeDBHelper.fetchAll();
        identifyOutcomeAdaptor = new IdentifyOutcomeAdaptor(this, cursor, 0);

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(identifyOutcomeAdaptor);
        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String plantName = cursor.getString(cursor.getColumnIndex("plantName"));
        String photoPath = cursor.getString(cursor.getColumnIndex("photoPath"));

//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        plant.plantName = plantName;
        plant.photoPath = photoPath;
        plant.lastWater = date;
        plantDBHelper.add(plant);

    }


}
