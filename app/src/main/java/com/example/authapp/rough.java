package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class rough extends AppCompatActivity {

    ImageView imageview;
    Button btsave;
    OutputStream outputStream;
    BitmapDrawable drawable;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rough);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel=new NotificationChannel("my notification","my notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        ActivityCompat.requestPermissions(rough.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        imageview=(ImageView) findViewById(R.id.carimg);
        imageview.setImageResource(getIntent().getIntExtra("imagename",0));
        btsave=(Button) findViewById(R.id.download);
        ActivityCompat.requestPermissions(rough.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder builder=new NotificationCompat.Builder(rough.this,"my notification");
                builder.setContentTitle("hi");
                builder.setContentText("your Image downloaded successfully");
                builder.setSmallIcon(R.drawable.bhugati);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat=NotificationManagerCompat.from(rough.this);
                managerCompat.notify(1,builder.build());

                FileOutputStream fileOutputStream=null;
                File file=getdisc();
                if (!file.exists() && !file.mkdirs())
                {
                    Toast.makeText(getApplicationContext(),"sorry can not make dir",Toast.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyymmsshhmmss");
                String date=simpleDateFormat.format(new Date());
                String name="img"+date+".jpeg";
                String file_name=file.getAbsolutePath()+"/"+name; File new_file=new File(file_name);
                try {
                    fileOutputStream =new FileOutputStream(new_file);
                    Bitmap bitmap=viewToBitmap(imageview,imageview.getWidth(),imageview.getHeight());
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_LONG).show();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                catch
                (FileNotFoundException e) {

                } catch (IOException e) {

                } refreshGallary(file);

            } private void refreshGallary(File file)
            { Intent i=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                i.setData(Uri.fromFile(file)); sendBroadcast(i);
            }
            private File getdisc(){
                File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                return new File(file,"My Image");
            } });
    }
    private static Bitmap viewToBitmap(View view, int widh, int hight)
    {
        Bitmap bitmap=Bitmap.createBitmap(widh,hight, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap); view.draw(canvas);
        return bitmap;
    }
}