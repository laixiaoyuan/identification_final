package edu.xlaiscu.gardenreminding;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class IdentifyOutcome extends AppCompatActivity implements View.OnClickListener{

    private int maxRecId;
    IdentifyOutcomeAdaptor identifyOutcomeAdaptor;
    IdentifyOutcomeDBHelper identifyOutcomeDBHelper;
    Cursor cursor;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_outcome);

        identifyOutcomeDBHelper = new IdentifyOutcomeDBHelper(this);
        cursor = identifyOutcomeDBHelper.fetchAll();
        identifyOutcomeAdaptor = new IdentifyOutcomeAdaptor(this, cursor, 0);

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(identifyOutcomeAdaptor);

        Button btnConfirm = (Button) findViewById(R.id.confirmButton);
        btnConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }


}
