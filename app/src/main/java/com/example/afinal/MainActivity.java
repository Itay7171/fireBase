package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button button;
    private EditText email;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Fire_base_handler f = new Fire_base_handler(auth,this);
        mAuth = FirebaseAuth.getInstance();  // Initialize FirebaseAuth
        button = findViewById(R.id.button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        ref.child("STUDENT").child("score").setValue("curentS+1");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = email.getText().toString().trim();
                String sPassword = password.getText().toString().trim();
                f.signIn(sEmail,sPassword);

            }
        });



    }
    public void moveToHome (View view)
    {
        Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,First.class);
        startActivity(intent);
    }


}


