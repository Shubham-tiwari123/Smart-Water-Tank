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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView block1;
    TextView block2;
    TextView block3;
    TextView block4;
    TextView block5;
    TextView block6;
    TextView block7;

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
        block7 = (TextView)findViewById(R.id.block7);

        block1.setVisibility(View.INVISIBLE);
        block2.setVisibility(View.INVISIBLE);
        block3.setVisibility(View.INVISIBLE);
        block4.setVisibility(View.INVISIBLE);
        block5.setVisibility(View.INVISIBLE);
        block6.setVisibility(View.INVISIBLE);
        block7.setVisibility(View.INVISIBLE);

        Button button = (Button)findViewById(R.id.startMotor);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TankStatus tankStatus = new TankStatus();
                tankStatus.execute();
            }
        });

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
            block7 = (TextView)findViewById(R.id.block7);

            if(distance==345){   /*distance==16*/
                block7.setVisibility(View.VISIBLE);
            }
            else if(distance==12){
                block6.setVisibility(View.VISIBLE);
            }
            else if(distance==10){
                block5.setVisibility(View.VISIBLE);
            }
            else if(distance==8){
                block4.setVisibility(View.VISIBLE);
            }
            else if(distance==6){
                block3.setVisibility(View.VISIBLE);
            }
            else if(distance==4){
                block2.setVisibility(View.VISIBLE);
            }
            else if(distance==2){
                block1.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(aVoid);
        }
    }
}
