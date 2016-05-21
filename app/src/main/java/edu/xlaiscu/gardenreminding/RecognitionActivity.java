package edu.xlaiscu.gardenreminding;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static android.provider.MediaStore.Images.Media;


import com.clarifai.api.ClarifaiClient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecognitionActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = RecognitionActivity.class.getSimpleName();
    private static final int CODE_PICK = 1;
    private final ClarifaiClient client = new ClarifaiClient(Credentials.CLIENT_ID,
            Credentials.CLIENT_SECRET);

    private Button selectPicButton;
    private Button takePicButton;
    private ImageView imageView;
    private TextView tagOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
        imageView = (ImageView) findViewById(R.id.imageView);
        tagOutput = (TextView) findViewById(R.id.tagOutput);
        selectPicButton = (Button) findViewById(R.id.selectPicButton);
        selectPicButton.setOnClickListener(this);

        takePicButton = (Button) findViewById(R.id.takePicButton);
        takePicButton.setOnClickListener(this);

        acquireRunTimePermissions();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectPicButton:
                final Intent selectPicIntent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(selectPicIntent, CODE_PICK);
                break;
            case R.id.takePicButton:
                final Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicIntent.resolveActivity(getPackageManager()) == null) {
                    toast("Cannot take pictures on this device!");
                    return;
                }
                String fileName = getOutputFileName();
                takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(fileName));
                startActivityForResult(takePicIntent, CODE_PICK);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == CODE_PICK && resultCode == RESULT_OK) {


        }
    }

    private String getOutputFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName =
                "file://"
                + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/BMP_"
                + timeStamp
                + ".bmp";
        return fileName;
    }



    private void acquireRunTimePermissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode != 111) return;
        if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Great! We have the permission!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cannot write to external storage! App will not work properly!", Toast.LENGTH_SHORT).show();
        }
    }





    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
