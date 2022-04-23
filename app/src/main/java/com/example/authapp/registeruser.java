package com.example.authapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registeruser extends AppCompatActivity implements View.OnClickListener{
    private TextView banner,registeruser;

    private EditText editTextfullname,editTextage,editTextemail,editTextpassword;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current
// .
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);
        mAuth = FirebaseAuth.getInstance();
        banner=(TextView)findViewById(R.id.banner2);
        banner.setOnClickListener(this);
        registeruser=(Button)findViewById(R.id.register2);
        registeruser.setOnClickListener(this);
        editTextfullname=(EditText) findViewById(R.id.fullname2);
        editTextage=(EditText) findViewById(R.id.age2);
        editTextemail=(EditText) findViewById(R.id.email2);
        editTextpassword=(EditText) findViewById(R.id.password2);
        progressbar=(ProgressBar) findViewById(R.id.progressBar2);
        TextView textView = (TextView) findViewById(R.id.banner2);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.banner2:
                startActivity(new Intent(this,MainActivity.class));
                overridePendingTransition(R.anim.leftslidein,R.anim.rightslideout);
                break;
            case R.id.register2:
                registeruser();
                break;
        }

    }
    private void registeruser()
    {
        String email=editTextemail.getText().toString().trim();
        String password=editTextpassword.getText().toString().trim();
        String fullname=editTextfullname.getText().toString().trim();
        String age=editTextage.getText().toString().trim();
        if(fullname.isEmpty())
        {
            editTextfullname.setError("full name is required");
            editTextfullname.requestFocus();
            return;
        }
        if(age.isEmpty())
        {
            editTextage.setError("age is required");
            editTextage.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            editTextemail.setError("email is required");
            editTextemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextemail.setError("please provide valid email");
            editTextemail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editTextpassword.setError("password is required");
            editTextpassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            editTextpassword.setError("min password length should be 6");
            editTextpassword.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User user=new User(fullname,age,email);
                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                    setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(),"user registered successfully",Toast.LENGTH_SHORT).show();
                                        progressbar.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"failed to register",Toast.LENGTH_SHORT).show();
                                        progressbar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"failed to register",Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}