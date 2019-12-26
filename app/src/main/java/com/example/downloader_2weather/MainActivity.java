package com.example.downloader_2weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
  Button btn;
  TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.button);
        text=findViewById(R.id.TextView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDownload md=new MyDownload();
                md.execute();
                //Вызов наследника AsyncTask
            }
        });
    }
    private class MyDownload extends AsyncTask<Void,Void,String>{
        HttpURLConnection httpurl;
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url=new URL("http://api.weatherstack.com/current?access_key=e6d9295a5b5851bfb9ed9d2a97adcaf3&query=Dubai");
                httpurl= (HttpURLConnection) url.openConnection();
                httpurl.setRequestMethod("GET");
                httpurl.connect();

                InputStream input=httpurl.getInputStream();
                Scanner scan=new Scanner(input);
                StringBuilder strbuild=new StringBuilder();
                while(scan.hasNextLine()){
                    strbuild.append(scan.nextLine());
                }
                return strbuild.toString();
            } catch (java.io.IOException e) {
                Log.e("UP TO LATE",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement json = jp.parse(s);
            String prettyJsonString = gson.toJson(json);
            text.setText(prettyJsonString);
        }
    }
}
