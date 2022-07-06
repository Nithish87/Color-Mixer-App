package com.example.colormixerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import top.defaults.colorpicker.ColorPickerPopup;

public class MainActivity extends AppCompatActivity {

    TextView colorBox1,colorBox2,colorCode1,colorCode2;
    Button firstColor,secondColor,mix;

    String displayColor;
    int color1,color2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorBox1= (TextView) findViewById(R.id.colorBox1);
        colorBox2= (TextView) findViewById(R.id.colorBox2);
        colorCode1= (TextView) findViewById(R.id.colorCode1);
        colorCode2= (TextView) findViewById(R.id.colorCode2);

        firstColor=(Button) findViewById(R.id.firstColor);
        secondColor=(Button) findViewById(R.id.secondColor);
        mix=(Button) findViewById(R.id.mix);

        firstColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(MainActivity.this).initialColor(Color.RED)
                        .enableBrightness(true)
                        .enableAlpha(true)
                        .cancelTitle("Choose")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(v,
                                new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void onColorPicked(int color) {
                                        //System.out.println(color);
                                        //System.out.println(displayColor);
                                        displayColor=Integer.toHexString(color);
                                        colorCode1.setText(String.valueOf(displayColor));
                                        colorBox1.setBackgroundColor(color);
                                        color1=color;
                                    }
                                });
            }
        });

        secondColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(MainActivity.this).initialColor(Color.RED)
                        .enableBrightness(true)
                        .enableAlpha(true)
                        .cancelTitle("Choose")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(v,
                                new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void onColorPicked(int color) {
                                        //System.out.println(color);
                                        //System.out.println(displayColor);
                                        displayColor=Integer.toHexString(color);
                                        colorCode2.setText(String.valueOf(displayColor));
                                        colorBox2.setBackgroundColor(color);
                                        color2=color;
                                    }
                                });
            }
        });

        mix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resultColor = ColorUtils.blendARGB(color1, color2, 0.5F);
                System.out.println(Integer.toHexString(resultColor));
            }
        });
    }

    public void logout(android.view.View view) {
        FirebaseAuth.getInstance().signOut();  //logout of user
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}