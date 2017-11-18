package com.example.client.finalproject;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView ls;
    ArrayList arrayList = new ArrayList();

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener promity;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for marshmallow devices define permission in the main activity too.
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,TimeCounterService.class);
        startService(intent);
        try{Toolbar tbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        }catch (Exception e){}

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ls=findViewById(R.id.list);
        //use cursor for fetching data
        Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null,null);
        while (cursor.moveToNext()){
            String nsme=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            arrayList.add(nsme);
        }
        ls.setAdapter(new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList));


        //sensors
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        //check if the prox sensor is not available
        if (sensor==null){
            Toast.makeText(this, "proximity sensor not available", Toast.LENGTH_LONG).show();
            finish();

        }
        promity=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0]<sensor.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.RED);

                }
                else {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(promity,sensor,
                 2*1000*1000);


    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(promity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
        }

        else if (id == R.id.bluetooth) {
            Toast.makeText(this,"you have clicked  bluetooth icon",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Bluetooth.class));

        }
        else if (id == R.id.addContact) {
            Toast.makeText(this,"you have clicked add contact icon",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,AddContact.class));

        }

        return super.onOptionsItemSelected(item);
    }
}
