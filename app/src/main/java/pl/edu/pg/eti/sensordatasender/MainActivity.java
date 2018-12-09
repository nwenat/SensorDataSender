package pl.edu.pg.eti.sensordatasender;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView messageView;
    SensorDataSender sensorDataSender = new SensorDataSender();
    private final int MEMORY_ACCESS = 5;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CALL_LOG)) {}
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, MEMORY_ACCESS);
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_SMS)) {}
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, MEMORY_ACCESS);
        }
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.sendBtn:
                editText = findViewById(R.id.editText);
                Log.d("activityyy", editText.getText().toString());
                jsonObject = sensorDataSender.sendData(getApplicationContext());
                sendPost();
                break;
        }
    }


    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String urlAdress = editText.getText().toString();
                    URL url = new URL(urlAdress);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonObject.toString());

                    os.flush();
                    os.close();
                    messageView = (TextView) findViewById(R.id.textView2);
                    Log.d("respCode", String.valueOf(conn.getResponseCode()));
                    if(conn.getResponseCode() == conn.HTTP_OK) {
                        Log.d("MSG" , conn.getResponseMessage());
                        Log.d("STATUS", String.valueOf(conn.getResponseCode()));
                        messageView.setText("Your data was send ok.");
                    } else {
                        Log.d("MSG" , conn.getResponseMessage());
                        Log.d("STATUS", String.valueOf(conn.getErrorStream().toString()));
                        messageView.setText("There is a problem with send data!!!");
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
