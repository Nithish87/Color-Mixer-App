package com.example.colormixerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.filament.View;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logout(android.view.View view) {
        FirebaseAuth.getInstance().signOut();  //logout of user
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}