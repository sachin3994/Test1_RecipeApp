package com.example.sachin.test1_c0694313;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView lv;
    TextView temp1;
    Model_Getter_Setter model;
    ArrayList<Model_Getter_Setter> model_arr = new ArrayList<Model_Getter_Setter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv= (ListView) findViewById(R.id.listView);
        temp1= (TextView) findViewById(R.id.temp);
        temp1.setText("Loading");
        temp1.setVisibility(View.VISIBLE);
        new fetchdata().execute("http://food2fork.com/api/search?key=777da9bb53c257cdfaca03a14091c9ab&q=shredded%20chicken");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myintent=new Intent(MainActivity.this,DetailsActivity.class);
                myintent.putExtra("recipe",model_arr.get(i).name);
                startActivity(myintent);
            }
        });

    }

private class fetchdata extends AsyncTask<String, Void, String> {
    ArrayList<String> mylist=new ArrayList<String>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        temp1.setVisibility(View.VISIBLE);

    }

    @Override
    protected String doInBackground(String... params) {
        String jsonstring;
        JSONObject queryobject;


        try {
            //URL halfurl=new URL("http://food2fork.com/api/search?key=777da9bb53c257cdfaca03a14091c9ab&q=shredded%20chicken");
            URL siteurl = new URL(params[0]);
            BufferedReader myreader = new BufferedReader(new InputStreamReader(siteurl.openStream()));
            jsonstring = myreader.readLine();
            JSONObject myobject = new JSONObject(jsonstring);
            JSONArray jsonArray=myobject.getJSONArray("recipes");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String name = jsonObject1.getString("title");
                String image = jsonObject1.getString("image_url");
                Log.d("doInBackground: ",name);
                model = new Model_Getter_Setter(name,image);

                model_arr.add(model);
                //Log.d(TAG, name);
               // Toast.makeText(MainActivity.this, ""+name, Toast.LENGTH_SHORT).show();

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String s="";
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        myadapter obj=new myadapter(MainActivity.this,model_arr);
        lv.setAdapter(obj);
        temp1.setVisibility(View.GONE);

    }
}
}





