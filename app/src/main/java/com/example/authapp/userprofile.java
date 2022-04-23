package com.example.authapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class userprofile extends AppCompatActivity {

    private Button logout,upload,recylerview;
    private ImageView imageview;
    private FirebaseStorage storage;
    private ProgressBar progressBar;
    private Uri imageuri;
    private ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current
// .
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        logout = (Button) findViewById(R.id.signout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(userprofile.this, MainActivity.class));
                overridePendingTransition(R.anim.leftslidein,R.anim.rightslideout);
            }
        });
        recylerview=(Button)findViewById(R.id.Recyclerview);
        recylerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),carimages.class);
                startActivity(i);
                overridePendingTransition(R.anim.leftslidein,R.anim.rightslideout);
            }
        });
        upload = (Button) findViewById(R.id.upload);
        imageview = (ImageView) findViewById(R.id.imageView2);
        storage=FirebaseStorage.getInstance();
    imageview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mgetcontent.launch("image/*");//image is successfully selected from gallery
            //now upload image on firebase storage

        }
    });
    upload.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            uploadimage();
        }
    });

    }

    private void uploadimage() {
            if(imageuri!=null)
            {
                progressDialog=new ProgressDialog(this);
                progressDialog.setTitle("In Progress......");
                progressDialog.show();
                SimpleDateFormat formatter=new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.CANADA);
                Date now=new Date();
                String filename=formatter.format(now);
//                Toast.makeText(getApplicationContext(),"photo uploaded in database",Toast.LENGTH_SHORT).show();
                StorageReference reference=FirebaseStorage.getInstance().getReference("images/"+filename);
                //refrence to store image in database;
                //it will be stored in image folder inside database
                //we could have also used user auth id instead of uuid
              reference.putFile(imageuri)
                      .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                          @Override
                          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                              imageview.setImageURI(null);
                              Toast.makeText(getApplicationContext(),"photo uploaded in database",Toast.LENGTH_SHORT).show();
                              if(progressDialog.isShowing())
                              {
                                  progressDialog.dismiss();
                              }
                          }
                      }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                      if(progressDialog.isShowing())
                      {
                          progressDialog.dismiss();
                      }
                  }
              });
            }
    }

    ActivityResultLauncher<String>mgetcontent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if(result!=null)
            {
                imageview.setImageURI(result);
                imageuri=result;
            }

        }
    });
}