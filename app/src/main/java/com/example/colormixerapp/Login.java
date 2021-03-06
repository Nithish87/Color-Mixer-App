package com.example.colormixerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText jEmail,jPassword;
    Button jLoginButton;
    TextView jCreateButton,forgotTextLink;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Removes status bar
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent=new Intent(Login.this,Mixing.class);

        jEmail=findViewById(R.id.Email);
        jPassword=findViewById(R.id.Password);
        jLoginButton=findViewById(R.id.loginButton);
        jCreateButton=findViewById(R.id.notRegistered);
        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        forgotTextLink=findViewById(R.id.forgotPassword);

        jLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=jEmail.getText().toString().trim();
                email=jEmail.getText().toString();
                System.out.println(email);
                intent.putExtra("User",email);
                String password=jPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    jEmail.setError("Email is required!!!");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    jPassword.setError("Password is required!!!");
                    return;
                }

                if(password.length()<6){
                    jPassword.setError("Password must atleast 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user

                String finalEmail = email;
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(Login.this,"Error!!! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        jCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail=new EditText(view.getContext());
                AlertDialog.Builder passwordResetDailog = new AlertDialog.Builder(view.getContext());
                passwordResetDailog.setTitle("Reset Password");
                passwordResetDailog.setMessage("Enter your Email id to receive reset link");
                passwordResetDailog.setView(resetMail);

                passwordResetDailog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract the email to send reset link
                        String mail=resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Login.this,"Rest link sent Succesfully",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"Link is not sent "+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDailog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //closing dailog and to move to login page
                    }
                });

                passwordResetDailog.create().show();
            }
        });

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            Intent intent1=new Intent(getApplicationContext(), Mixing.class);
            intent1.putExtra("User",email);
            startActivity(intent1);
            finish();
        }
    }
}