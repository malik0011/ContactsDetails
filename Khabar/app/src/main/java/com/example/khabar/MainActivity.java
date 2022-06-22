package com.example.khabar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcv;
    Contacts[] data;
    ArrayList<Contacts> list = new ArrayList<>();
    dbHelper DB;
    private static String URL = "https://api.androidhive.info/contacts";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding
        rcv = (RecyclerView) findViewById(R.id.recView);
        rcv.setLayoutManager(new LinearLayoutManager(this));//passing the context
        //local
        DB = new dbHelper(this);
        try {
            System.out.println(isConnected());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //first we have to check is internet avaliable?
        //if yes the store it in localDB and show the data
        //else get the data from localDB
        try {
            if(isConnected()){
                Toast.makeText(this, "Storing the data..", Toast.LENGTH_SHORT).show();

                DB.deleteAll();//delete the old data for avoiding duplicates
                StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        Contact1 apiData = gson.fromJson(response, Contact1.class);
                        list = (ArrayList<Contacts>) apiData.getContacts();
                        for(Contacts user : list){
                            Boolean checkInsertData = DB.insertUserData(user.getName(), user.getEmail(), user.getGender(), user.getPhone().getMobile());
                            if(checkInsertData==false){
                                Toast.makeText(MainActivity.this, "Some data is not inserted in local.!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        rcv.setAdapter(new MyAdapter(list));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"error "+error.toString(),Toast.LENGTH_SHORT);
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }

            else {
                Toast.makeText(this, "Local!", Toast.LENGTH_SHORT).show();
                getDataFormLocal();//if the device is offline the get the data from local
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    public void getDataFormLocal() {
        //getting the data form localDb
        Cursor res = DB.showData();
        ArrayList<Contacts> DataFromLocal = new ArrayList<>();

        if(res.getCount()==0){
            Toast.makeText(this, "No data found! Please Wait for a while!", Toast.LENGTH_SHORT).show();
        }else{
            while(res.moveToNext()){
               DataFromLocal.add(new Contacts(res.getString(0),res.getString(1),res.getString(2),"","",new Phone("",res.getString(3),"")));
            }
        }
    rcv.setAdapter(new MyAdapter(DataFromLocal));
    }

    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }
}