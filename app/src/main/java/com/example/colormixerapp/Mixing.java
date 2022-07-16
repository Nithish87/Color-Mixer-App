package com.example.colormixerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import top.defaults.colorpicker.ColorPickerPopup;
import yuku.ambilwarna.AmbilWarnaDialog;

class Info{
    static String user;
}

public class Mixing<user> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView colorBox1,colorBox2,colorCode1,colorCode2;
    TextView colorBox3,colorBox4,colorCode3,colorCode4;
    Button firstColor,secondColor,mix;
    Button thirdColor,fourthColor;

    TextView resultColorBox,resultColorCode;
    Button retryButton,saveButton;

    String displayColor;
    int color1=0,color2=0,color3=0,color4=0,resultColor=0;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String colorID;

    ColorInfo colorInfo;

    //Navigation
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixing);

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#DB6B16"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);


        colorBox1= (TextView) findViewById(R.id.colorBox1);
        colorBox2= (TextView) findViewById(R.id.colorBox2);
        colorBox3= (TextView) findViewById(R.id.colorBox3);
        colorBox4= (TextView) findViewById(R.id.colorBox4);

        colorCode1= (TextView) findViewById(R.id.colorCode1);
        colorCode2= (TextView) findViewById(R.id.colorCode2);
        colorCode3= (TextView) findViewById(R.id.colorCode3);
        colorCode4= (TextView) findViewById(R.id.colorCode4);

        firstColor=(Button) findViewById(R.id.firstColor);
        secondColor=(Button) findViewById(R.id.secondColor);
        thirdColor=(Button) findViewById(R.id.thirdColor);
        fourthColor=(Button) findViewById(R.id.fourthColor);
        mix=(Button) findViewById(R.id.mix);

        resultColorBox=(TextView) findViewById(R.id.resultColorBox);
        resultColorCode=(TextView) findViewById(R.id.resultColorCode);
        retryButton=(Button) findViewById(R.id.retryButton);
        saveButton=(Button) findViewById(R.id.saveButton);

        //Firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("ColorInfo");
        //colorInfo=new ColorInfo();

        //Navigation
        drawerLayout=findViewById(R.id.my_mixing_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //User email
        Bundle extras=getIntent().getExtras();
        Info.user= extras.getString("User");
        Intent intent=new Intent(Mixing.this,MainActivity.class);
        //System.out.println(user);
        //intent.putExtra("User",user);


        //Functions
        firstColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AmbilWarnaDialog colorPickerDialogue=new AmbilWarnaDialog(Mixing.this,color3,
                        new AmbilWarnaDialog.OnAmbilWarnaListener(){

                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) { }

                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                displayColor=Integer.toHexString(color);
                                colorCode1.setText(String.valueOf(displayColor));
                                colorBox1.setBackgroundColor(color);
                                color1=color;

                                firstColor.setVisibility(View.INVISIBLE);
                                colorBox1.setVisibility(View.VISIBLE);
                                colorCode1.setVisibility(View.VISIBLE);

                                //secondColor.setBackground(null);
                                secondColor.setBackgroundColor(Color.parseColor("#8BDA8F"));
                                secondColor.setEnabled(true);
                            }
                        });
                colorPickerDialogue.show();
            }
        });

        secondColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AmbilWarnaDialog colorPickerDialogue=new AmbilWarnaDialog(Mixing.this,color3,
                        new AmbilWarnaDialog.OnAmbilWarnaListener(){

                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) { }

                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                displayColor=Integer.toHexString(color);
                                colorCode2.setText(String.valueOf(displayColor));
                                colorBox2.setBackgroundColor(color);
                                color2=color;

                                secondColor.setVisibility(View.INVISIBLE);
                                colorBox2.setVisibility(View.VISIBLE);
                                colorCode2.setVisibility(View.VISIBLE);

                                thirdColor.setBackgroundColor(Color.parseColor("#8BDA8F"));
                                mix.setBackgroundColor(Color.parseColor("#FF5722"));
                                thirdColor.setEnabled(true);
                                mix.setEnabled(true);
                            }
                        });
                colorPickerDialogue.show();
            }
        });

        thirdColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AmbilWarnaDialog colorPickerDialogue=new AmbilWarnaDialog(Mixing.this,color3,
                        new AmbilWarnaDialog.OnAmbilWarnaListener(){

                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) { }

                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                color3=color;
                                displayColor=Integer.toHexString(color);
                                colorCode3.setText(String.valueOf(displayColor));
                                colorBox3.setBackgroundColor(color);

                                thirdColor.setVisibility(View.INVISIBLE);
                                colorBox3.setVisibility(View.VISIBLE);
                                colorCode3.setVisibility(View.VISIBLE);

                                fourthColor.setBackgroundColor(Color.parseColor("#8BDA8F"));
                                fourthColor.setEnabled(true);
                            }
                        });
                colorPickerDialogue.show();
            }
        });

        fourthColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AmbilWarnaDialog colorPickerDialogue=new AmbilWarnaDialog(Mixing.this,color3,
                        new AmbilWarnaDialog.OnAmbilWarnaListener(){

                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) { }

                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                displayColor=Integer.toHexString(color);
                                colorCode4.setText(String.valueOf(displayColor));
                                colorBox4.setBackgroundColor(color);
                                color4=color;

                                //fourthColor.setEnabled(false);
                                fourthColor.setVisibility(View.INVISIBLE);
                                colorBox4.setVisibility(View.VISIBLE);
                                colorCode4.setVisibility(View.VISIBLE);
                            }
                        });
                colorPickerDialogue.show();
            }
        });

        mix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color4!=0){
                    resultColor = ColorUtils.blendARGB(color1, color2, 0.5F);
                    resultColor=ColorUtils.blendARGB(resultColor,color3,0.5F);
                    resultColor=ColorUtils.blendARGB(resultColor,color4,0.5F);

                    System.out.println(Integer.toHexString(resultColor));
                    resultColorBox.setBackgroundColor(resultColor);
                    resultColorCode.setText(String.valueOf(Integer.toHexString(resultColor)));
                }
                else if(color3!=0){
                    resultColor = ColorUtils.blendARGB(color1, color2, 0.5F);
                    resultColor=ColorUtils.blendARGB(resultColor,color3,0.5F);

                    fourthColor.setEnabled(false);

                    System.out.println(Integer.toHexString(resultColor));
                    resultColorBox.setBackgroundColor(resultColor);
                    resultColorCode.setText(String.valueOf(Integer.toHexString(resultColor)));
                }
                else{
                    resultColor = ColorUtils.blendARGB(color1, color2, 0.5F);
                    System.out.println(Integer.toHexString(resultColor));
                    resultColorBox.setBackgroundColor(resultColor);
                    resultColorCode.setText(String.valueOf(Integer.toHexString(resultColor)));
                }

                mix.setVisibility(View.INVISIBLE);
                resultColorBox.setVisibility(View.VISIBLE);
                resultColorCode.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mix.setVisibility(View.VISIBLE);
                resultColorBox.setVisibility(View.INVISIBLE);
                resultColorCode.setVisibility(View.INVISIBLE);
                retryButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);

                colorBox1.setVisibility(View.INVISIBLE);
                colorBox2.setVisibility(View.INVISIBLE);
                colorBox3.setVisibility(View.INVISIBLE);
                colorBox4.setVisibility(View.INVISIBLE);

                colorCode1.setVisibility(View.INVISIBLE);
                colorCode2.setVisibility(View.INVISIBLE);
                colorCode3.setVisibility(View.INVISIBLE);
                colorCode4.setVisibility(View.INVISIBLE);

                firstColor.setVisibility(View.VISIBLE);

                secondColor.setEnabled(false);
                secondColor.setVisibility(View.VISIBLE);
                secondColor.setBackgroundColor(Color.parseColor("#375338"));
                //secondColor.setBackgroundColor(Color.TRANSPARENT);

                thirdColor.setEnabled(false);
                thirdColor.setVisibility(View.VISIBLE);
                thirdColor.setBackgroundColor(Color.parseColor("#375338"));

                fourthColor.setEnabled(false);
                fourthColor.setVisibility(View.VISIBLE);
                fourthColor.setBackgroundColor(Color.parseColor("#375338"));

                mix.setEnabled(false);
                mix.setBackgroundColor(Color.parseColor("#DD9983"));

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Save clicked");

                colorID=Info.user;
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                colorID=fAuth.getCurrentUser().getUid();

                System.out.println(colorID);

                //Adding data
                ColorInfo colorInfo=new ColorInfo(color1,color2,color3,color4,resultColor,colorID);

//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        databaseReference.child(colorID).setValue(colorInfo);
//                        Toast.makeText(Mixing.this, "Color Saved", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(Mixing.this, "Fail to add data "+error, Toast.LENGTH_SHORT).show();
//                    }
//                });

                Map<String, String> colorMap=new HashMap<>();
                colorMap.put("user",colorID);
                colorMap.put("color1", Integer.toHexString(color1));
                colorMap.put("color2",Integer.toHexString(color2));
                colorMap.put("color3",Integer.toHexString(color3));
                colorMap.put("color4",Integer.toHexString(color4));
                colorMap.put("result",Integer.toHexString(resultColor));

                databaseReference.push().setValue(colorMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Mixing.this, "Color Saved", Toast.LENGTH_SHORT).show();
                    }
                });

                //addDatatoFirebase(color1,color2,color3,color4,resultColor);
            }
        });
    }

    private void addDatatoFirebase(int color1, int color2, int color3, int color4, int resultColor) {
//        colorInfo.setColor1(color1);
//        colorInfo.setColor2(color2);
//        colorInfo.setColor3(color3);
//        colorInfo.setColor4(color4);
//        colorInfo.setResult(resultColor);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Adding values");
                databaseReference.push().setValue(colorInfo);
                Toast.makeText(Mixing.this, "Color Saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Mixing.this, "Fail to add data "+error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId()==R.id.saved){
            System.out.println("Saved is clicked");
            Intent intent=new Intent(Mixing.this,MainActivity.class);
            System.out.println(Info.user);
            intent.putExtra("User",Info.user);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.logout){
            System.out.println("Logout is clicked");
            logout();
        }
        return false;
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();  //logout of user
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

}