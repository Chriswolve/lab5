package com.example.labtel.lab5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    //private static String url = "http://www.omdbapi.com/?i=tt5826134&plot=short&r=json";
    String [] ids = {"tt1587310","tt0903624","tt1418377","tt0471093","tt1631867","tt2400570","tt1800241","tt2304933"};
    ArrayList<String> ImageList;
     String name;
    private ProgressDialog pDialog;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         ImageList = new ArrayList<>();

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Toast.makeText(getApplicationContext(), ImageList.get(position), Toast.LENGTH_LONG).show();
            }
        });

         new GetContacts().execute();

    }


    void cargar_imagenes(){

        HttpHandler sh = new HttpHandler();

        String url = "http://www.omdbapi.com/?i=tt1587310&plot=short&r=json";
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);


        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node


                // looping through All Contacts


                String name = jsonObj.getString("Title");


                Toast.makeText(getApplicationContext(),
                        "Get Title: " + name,
                        Toast.LENGTH_LONG)
                        .show();



                // adding contact to contact list
                ImageList.add(name);



            } catch (final JSONException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }
    }



    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response

            for (int i = 0 ; i< ids.length ; i++ ) {
                String url = "http://www.omdbapi.com/?i="+ids[i]+"&plot=short&r=json";
                String jsonStr = sh.makeServiceCall(url);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        String data = "";
                        data += "Titulo : "+ jsonObj.getString("Title") +"\n";

                        data += "Año : "+ jsonObj.getString("Year") +"\n";
                        data += "Genero : "+ jsonObj.getString("Genre") +"\n";

                        // adding contact to contact list
                        ImageList.add(data);
                    } catch (final JSONException e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            }

            return null;
        }


    }

}
