package com.example.sachin.test1_c0694313;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShowFavActivity extends AppCompatActivity {
    ListView lv;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fav);
        lv= (ListView) findViewById(R.id.listView);
        Realm.init(this);
        realm=Realm.getDefaultInstance();
        RealmResults<favourites> test = realm.where(favourites.class).findAll();
        String[] names=new String[test.size()];
        String[] pass=new String[test.size()];
        for(int i=0;i<test.size();i++)
        {
            names[i]=test.get(i).Rname;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names);
        lv.setAdapter(adapter);
    }
    }

