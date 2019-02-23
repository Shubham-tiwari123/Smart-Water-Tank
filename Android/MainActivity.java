package com.example.smarttank;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView block1;
    TextView block2;
    TextView block3;
    TextView block4;
    TextView block5;
    TextView block6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        block1 = (TextView)findViewById(R.id.block1);
        block2 = (TextView)findViewById(R.id.block2);
        block3 = (TextView)findViewById(R.id.block3);
        block4 = (TextView)findViewById(R.id.block4);
        block5 = (TextView)findViewById(R.id.block5);
        block6 = (TextView)findViewById(R.id.block6);

        block1.setVisibility(View.INVISIBLE);
        block2.setVisibility(View.INVISIBLE);
        block3.setVisibility(View.INVISIBLE);
        block4.setVisibility(View.INVISIBLE);
        block5.setVisibility(View.INVISIBLE);
        block6.setVisibility(View.INVISIBLE);

        Button startMotor = (Button)findViewById(R.id.startMotor);
        startMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask callDisplayOrders = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TankStatus tankStatus = new TankStatus();
                        tankStatus.execute();
                    }
                });
            }
        };
        timer.schedule(callDisplayOrders,0,3000);
    }

    private class TankStatus extends AsyncTask<Void,Void,Void>{
        int distance;
        StringBuffer output = new StringBuffer();
        @Override
        protected Void doInBackground(Void... voids) {
            Log.e("trymsg","msg");
            String URL_POST = "http://192.168.1.198:22849/RaspberryPi/TankStatus";
            try {
                URL url = new URL(URL_POST);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.connect();
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String s = "";
                    while ((s = bufferedReader.readLine()) != null)
                        output.append(s);
                    Log.e("itemNAme:-", output.toString());
                    JSONObject outputObject = new JSONObject(output.toString());
                    JSONObject waterObj;

                    waterObj = outputObject.getJSONObject("waterObj");
                    distance = waterObj.getInt("waterLevel");

                }
                Log.e("response", String.valueOf(responseCode));
            } catch (MalformedURLException e) {
                Log.e("errors",e.toString());
            } catch (IOException e) {
                Log.e("errors",e.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            block1 = (TextView)findViewById(R.id.block1);
            block2 = (TextView)findViewById(R.id.block2);
            block3 = (TextView)findViewById(R.id.block3);
            block4 = (TextView)findViewById(R.id.block4);
            block5 = (TextView)findViewById(R.id.block5);
            block6 = (TextView)findViewById(R.id.block6);

            if(distance>10 && distance<=12){
                block6.setVisibility(View.VISIBLE);
            }
            else if(distance>8 && distance<=10){
                block6.setVisibility(View.VISIBLE);
                block5.setVisibility(View.VISIBLE);
            }
            else if(distance>6 && distance<=8){
                block6.setVisibility(View.VISIBLE);
                block5.setVisibility(View.VISIBLE);
                block4.setVisibility(View.VISIBLE);
            }
            else if(distance>4 && distance<=6){
                block6.setVisibility(View.VISIBLE);
                block5.setVisibility(View.VISIBLE);
                block4.setVisibility(View.VISIBLE);
                block3.setVisibility(View.VISIBLE);
            }
            else if(distance>=2 &&distance<=4){
                block6.setVisibility(View.VISIBLE);
                block5.setVisibility(View.VISIBLE);
                block4.setVisibility(View.VISIBLE);
                block3.setVisibility(View.VISIBLE);
                block2.setVisibility(View.VISIBLE);
                block1.setVisibility(View.VISIBLE);
            }
            else if(distance>=-4 &&distance<=-2){
                block1.setVisibility(View.INVISIBLE);
            }

            else if(distance>=-6 &&distance<-4){
                block1.setVisibility(View.INVISIBLE);
                block2.setVisibility(View.INVISIBLE);
            }
            else if(distance>=-8 &&distance<-6){
                block1.setVisibility(View.INVISIBLE);
                block2.setVisibility(View.INVISIBLE);
                block3.setVisibility(View.INVISIBLE);
            }
            else if(distance>=-10 &&distance<-8){
                block1.setVisibility(View.INVISIBLE);
                block2.setVisibility(View.INVISIBLE);
                block3.setVisibility(View.INVISIBLE);
                block4.setVisibility(View.INVISIBLE);
            }
            else if(distance>=-12 &&distance<-10){
                block1.setVisibility(View.INVISIBLE);
                block2.setVisibility(View.INVISIBLE);
                block3.setVisibility(View.INVISIBLE);
                block4.setVisibility(View.INVISIBLE);
                block5.setVisibility(View.INVISIBLE);
                block6.setVisibility(View.INVISIBLE);
            }
            super.onPostExecute(aVoid);
        }
    }

    private class UserResponse extends AsyncTask<Void,Void,Void>{

        int flag=0;
        JSONObject jsonObject = new JSONObject();
        @Override
        protected Void doInBackground(Void... voids) {
            Log.e("trymsg","msg");
            String URL_POST = "http://192.168.1.198:22849/RaspberryPi/UserResponse";
            try {
                jsonObject.put("userResponse","1");
                URL url = new URL(URL_POST);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.connect();
                conn.setDoOutput(true);
                OutputStream outputStream = conn.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(outputStream, "UTF-8");
                osw.write(jsonObject.toString());
                osw.flush();
                osw.close();
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK){
                    flag=1;

                }
                Log.e("response", String.valueOf(responseCode));
            } catch (MalformedURLException e) {
                Log.e("errors",e.toString());
            } catch (IOException e) {
                Log.e("errors",e.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(flag==1)
                Toast.makeText(getApplicationContext(),"Motor Stopped",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Some error occurred try again...",Toast.LENGTH_SHORT).show();
        }
    }
}
