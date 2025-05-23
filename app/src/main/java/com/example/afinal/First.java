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

public class First extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void moveToReg (View view)
    {
        Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
    public void moveSignIn (View view)
    {
        Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void moveToGame (View view)
    {
        Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,StartGame.class);
        startActivity(intent);
    }

}