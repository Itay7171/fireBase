package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity {
    RecyclerView recycerView;
    ArrayList<Score> list;
    RecyclerCustomAdapter adapter;
    String[] gmail = {"itayaf@gmail.com", "shilo10@gmail.com","huyuy5@gmail.com","vdrey4@gmail.com"};
    int[] score = {22 , 17, 11, 8 };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leader_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recycerView = findViewById(R.id.RecyclerView);
        list = new ArrayList<>();
        for(int i = 0; i< gmail.length; i++)
        {
            list.add(new Score(gmail[i], score[i]));

        }
        adapter = new RecyclerCustomAdapter(list);

        recycerView.setAdapter(adapter);

        recycerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void moveToStartGame(View view) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
    }

}