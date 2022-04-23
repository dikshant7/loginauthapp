package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class carimages extends AppCompatActivity {

    RecyclerView rcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carimages);
        rcv=(RecyclerView) findViewById(R.id.rclview);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        String arr[]={"This is lamo","This is mercedes","This is porche","This is bmw","This is bhugati"};
        int arr1[]={R.drawable.lamo,R.drawable.merc,R.drawable.porche,R.drawable.bmw,R.drawable.bhugati};
        rcv.setAdapter(new myadapter(arr,arr1,getApplicationContext()));
    }
}