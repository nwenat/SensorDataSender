package pl.edu.pg.eti.sensordatasender;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    SensorDataSender sensorDataSender = new SensorDataSender();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.sendBtn:

                editText = (EditText) findViewById(R.id.editText);
                String serverAddress = editText.getText().toString();
                Log.d("activityyy", editText.getText().toString());

                sensorDataSender.sendData(serverAddress);

                break;
        }
    }
}
