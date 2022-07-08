package com.example.colormixerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.util.JsonUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import top.defaults.colorpicker.ColorPickerPopup;
import yuku.ambilwarna.AmbilWarnaDialog;

public class Mixing extends AppCompatActivity {

    TextView colorBox1,colorBox2,colorCode1,colorCode2;
    TextView colorBox3,colorBox4,colorCode3,colorCode4;
    Button firstColor,secondColor,mix;
    Button thirdColor,fourthColor;

    TextView resultColorBox,resultColorCode;
    Button retryButton,saveButton;

    String displayColor;
    int color1=0,color2=0,color3=0,color4=0;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixing);

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
                    int resultColor = ColorUtils.blendARGB(color1, color2, 0.5F);
                    resultColor=ColorUtils.blendARGB(resultColor,color3,0.5F);
                    resultColor=ColorUtils.blendARGB(resultColor,color4,0.5F);

                    System.out.println(Integer.toHexString(resultColor));
                    resultColorBox.setBackgroundColor(resultColor);
                    resultColorCode.setText(String.valueOf(Integer.toHexString(resultColor)));
                }
                else if(color3!=0){
                    int resultColor = ColorUtils.blendARGB(color1, color2, 0.5F);
                    resultColor=ColorUtils.blendARGB(resultColor,color3,0.5F);

                    fourthColor.setEnabled(false);

                    System.out.println(Integer.toHexString(resultColor));
                    resultColorBox.setBackgroundColor(resultColor);
                    resultColorCode.setText(String.valueOf(Integer.toHexString(resultColor)));
                }
                else{
                    int resultColor = ColorUtils.blendARGB(color1, color2, 0.5F);
                    System.out.println(Integer.toHexString(resultColor));
                    resultColorBox.setBackgroundColor(resultColor);
                    resultColorCode.setText(String.valueOf(Integer.toHexString(resultColor)));
                }

                mix.setVisibility(View.INVISIBLE);
                resultColorBox.setVisibility(View.VISIBLE);
                resultColorCode.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.VISIBLE);
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mix.setVisibility(View.VISIBLE);
                resultColorBox.setVisibility(View.INVISIBLE);
                resultColorCode.setVisibility(View.INVISIBLE);
                retryButton.setVisibility(View.INVISIBLE);

                colorBox1.setVisibility(View.INVISIBLE);
                colorBox2.setVisibility(View.INVISIBLE);
                colorBox3.setVisibility(View.INVISIBLE);
                colorBox4.setVisibility(View.INVISIBLE);

                colorCode1.setVisibility(View.INVISIBLE);
                colorCode2.setVisibility(View.INVISIBLE);
                colorCode3.setVisibility(View.INVISIBLE);
                colorCode4.setVisibility(View.INVISIBLE);

                firstColor.setVisibility(View.VISIBLE);
                secondColor.setVisibility(View.VISIBLE);
                thirdColor.setVisibility(View.VISIBLE);
                fourthColor.setVisibility(View.VISIBLE);

                thirdColor.setEnabled(false);
                fourthColor.setEnabled(false);
                mix.setEnabled(false);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void openColorPickerDialogue() {

    }
}