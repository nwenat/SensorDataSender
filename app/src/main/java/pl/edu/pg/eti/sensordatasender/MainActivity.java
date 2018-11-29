package pl.edu.pg.eti.sensordatasender;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    SensorDataSender sensorDataSender = new SensorDataSender();
    private String path = Environment.getExternalStorageDirectory().toString() + "/DigitalZombieLab/KenisToys";
    private final int MEMORY_ACCESS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CALL_LOG)) {}
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, MEMORY_ACCESS);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MEMORY_ACCESS:
                if ((grantResults.length > 0)&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {}
                else {
                    Toast.makeText(getApplicationContext(), "Jeśli nie zostanie wyrażona zgoda na dostęp do pamięci, nie będzie możliwości zapisania pliku", Toast.LENGTH_LONG).show();
                }
        }
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.sendBtn:

                editText = (EditText) findViewById(R.id.editText);
                String serverAddress = editText.getText().toString();
                Log.d("activityyy", editText.getText().toString());

                sensorDataSender.sendData(getApplicationContext(), serverAddress);

                break;
        }
    }
}
