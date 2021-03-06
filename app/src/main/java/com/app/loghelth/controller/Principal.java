package com.app.loghelth.controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.loghelth.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Principal extends AppCompatActivity {

    private ViewLogHelth telaLogHelth;
    private TextView txtBpm;

    private final String DEVICE_NAME="HC-05";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//Serial Port Service ID
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    boolean deviceConnected=false;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        telaLogHelth = (ViewLogHelth) findViewById(R.id.telaLogHelth);
        txtBpm = (TextView) findViewById(R.id.txtBpm);
    }

    public void onClickStart(View view) {
        if(BTinit()) {
            if(BTconnect()) {
                deviceConnected = true;
                beginListenForData();
                Toast.makeText(getApplicationContext(),"Conexão aberta!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean BTinit() {
        boolean found = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Não há suporte para Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled()) {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Por favor, pareie o dispositivo primeiro.",Toast.LENGTH_SHORT).show();
        }
        else {
            for (BluetoothDevice iterator : bondedDevices) {
                if(iterator.getName().equals(DEVICE_NAME)) {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public boolean BTconnect() {
        boolean connected = true;

        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        }
        catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }

        if(connected) {
            try {
                outputStream = socket.getOutputStream();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inputStream = socket.getInputStream();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

        return connected;
    }

    private String string;
    public void beginListenForData() {
        final Handler handler = new Handler();
        stopThread = false;
        telaLogHelth.setStopThread(stopThread);
        telaLogHelth.invalidate();

        buffer = new byte[1024];
        thread  = new Thread(new Runnable() {
            public void run() {
                while(!Thread.currentThread().isInterrupted() && !stopThread) {
                    try {
                        int byteCount = inputStream.available();
                        if(byteCount > 0) {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            string = new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run() {
                                    txtBpm.setText(string + " bpm");
                                }
                            });

                        }
                    }
                    catch (IOException ex) {
                        stopThread = true;
                        telaLogHelth.setStopThread(stopThread);
                        telaLogHelth.setFator(0.5);
                        telaLogHelth.invalidate();
                    }
                }
            }
        });

        thread.start();

    }

    public void onClickStop(View view) throws IOException {
        stopThread = true;
        telaLogHelth.setStopThread(stopThread);
        telaLogHelth.setFator(0.5);
        telaLogHelth.invalidate();

        if(socket != null){
            outputStream.close();
            inputStream.close();
            socket.close();
        }

        deviceConnected = false;
        txtBpm.setText("0 bpm");
    }

}