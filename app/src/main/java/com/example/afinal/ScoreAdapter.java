package com.example.afinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ScoreAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Score> list;
    private int layout;

    public ScoreAdapter(@NonNull Context context, int layout, @NonNull ArrayList<Score> list)
    {
        super(context, layout, list);
        this.context=context;
        this.layout=layout;
        this.list=list;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        LayoutInflater layoutInflater= ((AppCompatActivity)context).getLayoutInflater();

        View view = layoutInflater.inflate(layout,parent,false);


        Score Score=this.list.get(position);


        TextView tvFootballName=view.findViewById(R.id.gmail);
        TextView tvAge=view.findViewById(R.id.score);


        tvFootballName.setText(Score.getFootballName());
        tvAge.setText(String.valueOf(Score.getScore())+" gramm");


        return view;
    }


}
