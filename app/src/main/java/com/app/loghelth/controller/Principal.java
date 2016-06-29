package com.app.loghelth.controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.loghelth.R;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;
import java.util.UUID;

public class Principal extends AppCompatActivity {

    private ViewLogHelth telaLogHelth;
    private TextView txtBpm;

    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice dispositivo;

    BluetoothSocket socket;

    boolean encontrado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        telaLogHelth = (ViewLogHelth) findViewById(R.id.telaLogHelth);
        txtBpm = (TextView) findViewById(R.id.txtBpm);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        verificaPareamento();
    }

    public void verificaPareamento(){

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if(bondedDevices.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Por favor, pareie seu dispositivo primeiro.",Toast.LENGTH_SHORT).show();
        }
        else {

            for(BluetoothDevice iterator : bondedDevices) {

                if(iterator.getName().equals("linvor")) {

                    dispositivo = iterator;
                    encontrado = true;

                    try{
                        socket = dispositivo.createRfcommSocketToServiceRecord(PORT_UUID);
                        socket.connect();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    break;

                }
            }
        }
    }


}