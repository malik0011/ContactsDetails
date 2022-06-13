package com.example.khabar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class MainActivity extends AppCompatActivity {
    RecyclerView rcv;
    Contacts data[];
    ArrayList<Contacts> list = new ArrayList<>();
    dbHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding
        rcv = (RecyclerView) findViewById(R.id.recView);
        rcv.setLayoutManager(new LinearLayoutManager(this));//passing the context

        //local
        DB = new dbHelper(this);

        //now adapter
        //first we have to fetch the data and store it in the
        //Url
        String url = "https://api.androidhive.info/contacts/";
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder Gb = new GsonBuilder();
                        Gson gson = Gb.create();
                        list = gson.fromJson(response,ArrayList.class);
                        for(Contacts user : list){
                            Boolean checkInsertData = DB.insertUserData(user.getName(), user.getEmail(), user.getGender(), user.getPhone().getMobile());
                            if(checkInsertData==false){
                                Toast.makeText(MainActivity.this, "Some data is not inserted in local.!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

        getDataFormLocal();
    }

    public void getDataFormLocal() {

        ArrayList<Contacts> DataFromLocal = new ArrayList<>();

        //getting the data form localDb
        Cursor res = DB.showData();
        if(res.getCount()==0){
            Toast.makeText(this, "No data found! Please Wait for a while!", Toast.LENGTH_SHORT).show();
        }else{
            while(res.moveToNext()){
                DataFromLocal.add(new Contacts(res.getString(0),res.getString(1),res.getString(2),new Phone("",res.getString(3),"")));
            }
        }
    rcv.setAdapter(new MyAdapter(DataFromLocal));
    }
}