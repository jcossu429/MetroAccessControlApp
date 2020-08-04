package com.example.androidthings.metroaccesscontrolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    //UI Element
    Button btngate;
    Button btndoor;
    Button btnelevator;
    EditText ipAddress;
    public static String wifiModuleIp ="";
    public static int wifiModulePort = 0;
    public static String CMD = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btngate = findViewById(R.id.btngate);
        btndoor = findViewById(R.id.btndoor);
        btnelevator = findViewById(R.id.btnelevator);

        ipAddress = findViewById(R.id.ipAddress);

        btngate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD = "Open";
                Socket_AsyncTask cmd_open_gate = new Socket_AsyncTask();
                cmd_open_gate.execute();
                Toast.makeText(MainActivity.this, "GATE OPEN RESIDENT", Toast.LENGTH_SHORT).show();
            }
        });

        btndoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD = "Close";
                Socket_AsyncTask cmd_open_door = new Socket_AsyncTask();
                cmd_open_door.execute();
                Toast.makeText(MainActivity.this, "DOOR CLOSED RESIDENT", Toast.LENGTH_SHORT).show();
            }
        });

        btnelevator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD = "Elevator";
                Socket_AsyncTask cmd_open_elevator = new Socket_AsyncTask();
                cmd_open_elevator.execute();
                Toast.makeText(MainActivity.this, "ELEVATOR OPEN BULL HEAD", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getIPandPort()
    {
        String iPandPort = ipAddress.getText().toString();
        Log.d("METROWIFI", "IP String:"+ iPandPort);
        String[] temp = iPandPort.split(":");
        wifiModuleIp = temp[0];
        wifiModulePort = Integer.parseInt(temp[1]);
        Log.d("METROWIFI", "IP String: " + wifiModuleIp);
        Log.d("METROWIFI", "PORT:" + wifiModulePort);
    }

    public static class Socket_AsyncTask extends AsyncTask<Void,Void,Void>
    {
        Socket socket;

        @Override
        protected Void doInBackground(Void... params){
            try {
                InetAddress inetAddress = InetAddress.getByName(MainActivity.wifiModuleIp);
                socket = new java.net.Socket(inetAddress, MainActivity.wifiModulePort);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.close();
                socket.close();
            } catch (UnknownHostException e){e.printStackTrace();} catch (IOException e){e.printStackTrace();}
            return null;
        }
    }
}
