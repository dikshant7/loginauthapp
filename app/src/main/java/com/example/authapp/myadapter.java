package com.example.authapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.InetAddresses;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class myadapter extends RecyclerView.Adapter<myadapter.holder> {
    String data[];
    int data1[];
    Context context;

    public myadapter(String[] data, int[] data1, Context context) {
        this.data=data;
        this.data1=data1;
        this.context=context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.singlerow,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        final String temp1=data[position];
        final int temp2=data1[position];
        holder.tv.setText(data[position]);
        holder.img.setImageResource(data1[position]);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,rough.class);
                intent.putExtra("imagename",temp2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    class holder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView tv;
        Button btn;


        public holder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.img1);
            tv=(TextView) itemView.findViewById(R.id.t1);
            btn=(Button) itemView.findViewById(R.id.save);
        }
    }
}
