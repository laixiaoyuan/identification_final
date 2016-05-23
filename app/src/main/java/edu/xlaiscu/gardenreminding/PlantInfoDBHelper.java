package edu.xlaiscu.gardenreminding;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * Created by Lexie on 5/22/16.
 */
public class PlantInfoDBHelper extends SQLiteOpenHelper {
    static private final int VERSION = 2;
    static private final String DB_PACKAGENAME = "edu.xlaiscu.gardenreminding";
    static private final String DB_NAME="flowerInfo.sql";
    static private final String DB_Path = "/data/data/" + DB_PACKAGENAME + "/databases";
    public SQLiteDatabase myDataBase;
    Context context;

//    static private final String SQL_CREATE_TABLE =
//            "CREATE TABLE plantInfo (" +
//                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    "  PlantName TEXT," +
//                    "  PhotoPath TEXT," +
//                    "  WaterInterval INTEGER" +
//                    "  LastWater DATE);";


//    static private final String SQL_DROP_TABLE = "DROP TABLE plantInfo";


    public PlantInfoDBHelper(Context context) throws IOException{
        super(context, DB_NAME, null, VERSION);     // we use default cursor factory (null, 3rd arg)
        this.context = context;
        boolean dbExist = checkDataBase();
        if (dbExist) {
            openDataBase();
        }
        else {
            createDataBase();
        }
    }


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Toast.makeText(context, "Database exists", Toast.LENGTH_SHORT).show();
        }
        else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            }
            catch (IOException e) {
                throw new Error ("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        boolean checkdb = false;
        try {
            String myPath = DB_Path + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        }
        catch (SQLiteException e) {
            Toast.makeText(context, "Database doesn't exist!", Toast.LENGTH_SHORT).show();

        }
        return checkdb;
    }

    private void copyDataBase() throws IOException {
        InputStream myinput = context.getAssets().open(DB_NAME);
        String outfilename = DB_Path + DB_NAME;
        OutputStream myoutput = new FileOutputStream("/data/data/" + DB_PACKAGENAME + "/databases/" + DB_NAME);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    private void openDataBase() throws SQLException {
        String mypath = DB_Path + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("TAG", "On create called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Hashtable<String, String> fetchPlantName() {
        Hashtable<String, String> plantNameHash = new Hashtable<String, String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM flowerInfo;", null);
        if (cursor.moveToFirst()) {
            do {
                plantNameHash.put(cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("picture")));
            } while (cursor.moveToNext());
        }
        else {
            return null;
        }
        return plantNameHash;
    }
}
