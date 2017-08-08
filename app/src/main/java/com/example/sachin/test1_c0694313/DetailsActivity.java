package com.example.sachin.test1_c0694313;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import io.realm.Realm;

public class DetailsActivity extends AppCompatActivity {
    String recipeName;
    String recipeID;
    String recipeURL;
    String image;
    String socialRank;
    String imgURL;
    TextView rName,rID,rRating,rLink,temp1;
    ImageView img1;
    Button btn,btn1;
    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        recipeName=getIntent().getStringExtra("recipe");
        rName= (TextView) findViewById(R.id.recipeName);
        rID= (TextView) findViewById(R.id.recipeID);
        rRating= (TextView) findViewById(R.id.recipeRating);
        rLink= (TextView) findViewById(R.id.rLink);
        img1= (ImageView) findViewById(R.id.recipeImage);
        btn= (Button) findViewById(R.id.fav);
        btn1= (Button) findViewById(R.id.viewFav);

        new fetchdata1().execute("http://food2fork.com/api/search?key=777da9bb53c257cdfaca03a14091c9ab&q=shredded%20chicken");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm.init(getApplicationContext());
                realm=Realm.getDefaultInstance();
                favourites obj=new favourites();
                obj.Rname=recipeName;
                try {
                    realm.beginTransaction();
                    realm.copyToRealm(obj);
                    realm.commitTransaction();
                    Toast.makeText(DetailsActivity.this, "Saved To Favourites", Toast.LENGTH_SHORT).show();

                }catch (Exception e)
                {
                    Toast.makeText(DetailsActivity.this, "Already added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent=new Intent(DetailsActivity.this,ShowFavActivity.class);
                startActivity(myintent);
            }
        });

       // Picasso.with(this).load(imgURL).into(this.img1);

    }

    private class fetchdata1 extends AsyncTask<String, Void, String> {
        ArrayList<String> mylist=new ArrayList<String>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
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

                    if(name.equalsIgnoreCase(recipeName))
                    {
                          recipeURL=jsonObject1.getString("source_url");
                          image = jsonObject1.getString("image_url");
                        recipeID = jsonObject1.getString("recipe_id");
                          socialRank = jsonObject1.getString("social_rank");
                         imgURL = jsonObject1.getString("image_url");
                        Log.d("img11", "doInBackground: "+imgURL);
                    }


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
           /* myadapter obj=new myadapter(MainActivity.this,model_arr);
            lv.setAdapter(obj);*/
            rName.setText(recipeName);
            rID.setText("ID "+recipeID);
            rRating.setText("Social Rank "+socialRank);
            rLink.setText(recipeURL);
            Picasso.with(DetailsActivity.this).load(imgURL).into(DetailsActivity.this.img1);

        }
    }
}
