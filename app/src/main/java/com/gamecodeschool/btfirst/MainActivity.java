package com.gamecodeschool.btfirst;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {


    Button buttonOn, buttonOff,button;
    BluetoothAdapter myBluetoothAdapter;
    Intent btEnablingIntent;
    int requestCodeForEnable;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOn = (Button) findViewById(R.id.buttonOn);
        buttonOff = (Button) findViewById(R.id.buttonOff);
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btEnablingIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        requestCodeForEnable = 1;

        listview=(ListView) findViewById(R.id.listview);

        bluetoothOnMethod();
        bluetoothOffMethod();

        exeButton();





    }

    private void exeButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<BluetoothDevice>bt=myBluetoothAdapter.getBondedDevices();
                String[]strings=new String[bt.size()];
                int index=0;
                if (bt.size()>0){
                    for(BluetoothDevice device:bt)
                    {
                        strings[index]=device.getName();
                        index++;
                    }
                    ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,strings);
                    listview.setAdapter(arrayAdapter);

                }
            }
        });
    }

    private void bluetoothOffMethod() {
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myBluetoothAdapter.isEnabled()) {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    myBluetoothAdapter.disable();
                }
            }
        });
    }

    private void bluetoothOnMethod() {
        buttonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myBluetoothAdapter==null)
                {

                    Toast.makeText(getApplicationContext(),"Bluetooth is not available",Toast.LENGTH_LONG).show();
                }else
                {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }



                    if (!myBluetoothAdapter.isEnabled()){

                        startActivityForResult(btEnablingIntent,requestCodeForEnable);
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==requestCodeForEnable){
            if(resultCode==RESULT_OK)
            {
                Toast.makeText(getApplicationContext(),"Bluetooth is Enable",Toast.LENGTH_LONG).show();
            }
            else if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(),"Bluetooth is failed to enable",Toast.LENGTH_LONG).show();
            }
        }

    }


}