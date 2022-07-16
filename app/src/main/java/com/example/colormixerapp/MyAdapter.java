package com.example.colormixerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<ColorInfo> colorInfoArrayList;
    private  Context context;
    int lastPos=-1;

    public MyAdapter(ArrayList<ColorInfo> colorInfoArrayList, Context context, ColorClickInterface colorClickInterface) {
        this.colorInfoArrayList = colorInfoArrayList;
        this.context = context;
        this.colorClickInterface = colorClickInterface;
    }

    private ColorClickInterface colorClickInterface;

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.colors,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ColorInfo colorInfo=colorInfoArrayList.get(position);

        System.out.println("Inside onBind");

        holder.color1.setTextColor(Color.parseColor("#"+colorInfo.getColor1()));
        holder.color1Text.setText(colorInfo.getColor1());
        //System.out.println(colorInfo.getColor2());

        holder.color2.setTextColor(Color.parseColor("#"+colorInfo.getColor2()));
        holder.color2Text.setText(colorInfo.getColor2());
        //System.out.println(colorInfo.getColor3());

        holder.color3.setTextColor(Color.parseColor("#"+colorInfo.getColor3()));
        holder.color3Text.setText(colorInfo.getColor3());
        //System.out.println(colorInfo.getColor4());

        holder.color4.setTextColor(Color.parseColor("#"+colorInfo.getColor4()));
        holder.color4Text.setText(colorInfo.getColor4());
        //System.out.println(colorInfo.getResult());

        holder.colorR.setTextColor(Color.parseColor("#"+colorInfo.getResult()));
        holder.resultText.setText(colorInfo.getResult());

        //setAnimation(holder.itemView,position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorClickInterface.onColorClick(position);
            }
        });
    }

    private void setAnimation(View itemView,int position){
        if(position>lastPos){
            Animation animation= AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos=position;
        }
    }

    @Override
    public int getItemCount() {
        return  colorInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView color1Text,color2Text,color3Text,color4Text;
        private TextView color1,color2,color3,color4,colorR;
        private TextView resultText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            System.out.println("TextView set");

            color1=itemView.findViewById(R.id.c1);
            color2=itemView.findViewById(R.id.c2);
            color3=itemView.findViewById(R.id.c3);
            color4=itemView.findViewById(R.id.c4);
            colorR=itemView.findViewById(R.id.cr);

            color1Text=itemView.findViewById(R.id.color1_text);
            color2Text=itemView.findViewById(R.id.color2_text);
            color3Text=itemView.findViewById(R.id.color3_text);
            color4Text=itemView.findViewById(R.id.color4_text);
            resultText=itemView.findViewById(R.id.result_text);
        }
    }

    public interface ColorClickInterface{
        void onColorClick(int position);
    }
}
