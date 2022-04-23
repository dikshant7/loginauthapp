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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register;
    private EditText editTextemail,editTextpassword;
    private Button signin;
    private FirebaseAuth mauth;
    private ProgressBar progressBar;

    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current
// .
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register=(TextView) findViewById(R.id.register1);
        register.setOnClickListener(this);
        signin=(Button)findViewById(R.id.login1);
        signin.setOnClickListener(this);
        editTextemail=(EditText)findViewById(R.id.email1) ;
        editTextpassword=(EditText)findViewById(R.id.password1);
        progressBar=(ProgressBar) findViewById(R.id.progressBar1);
        mauth=FirebaseAuth.getInstance();
        TextView textView = (TextView) findViewById(R.id.register1);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onClick(View view) {
    switch (view.getId())
    {
        case R.id.register1:
            startActivity(new Intent(this,registeruser.class));
            overridePendingTransition(R.anim.leftslidein,R.anim.rightslideout);
            break;
        case  R.id.login1:
            userlogin();
            overridePendingTransition(R.anim.leftslidein,R.anim.rightslideout);
            break;
    }

    }

    private void userlogin() {
        String email=editTextemail.getText().toString().trim();
        String password=editTextpassword.getText().toString().trim();
        if(email.isEmpty())
        {
            editTextemail.setError("email is empty");
            editTextemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextemail.setError("please enter a valid email ");
            editTextemail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editTextpassword.setError("email is empty");
            editTextpassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            editTextpassword.setError("min password shpuld be of 6 character");
            editTextpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        startActivity(new Intent(MainActivity.this,userprofile.class));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"failed to login please check you credential",Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}