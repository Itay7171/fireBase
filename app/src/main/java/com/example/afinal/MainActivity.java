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

    private TextView timerText;
    private CountDownTimer countDownTimer;
    private int currentPlayer = 1; // נניח שחקן 1 מתחיל

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

        timerText = findViewById(R.id.timerText);

        startTurnTimer(); // הפעלת הטיימר בתחילת התור
    }
    public void moveToHome (View view)
    {
        Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,First.class);
        startActivity(intent);
    }

    private void startTurnTimer() {
        countDownTimer = new CountDownTimer(60000, 1000) { // 60 שניות

            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                timerText.setText(String.valueOf(secondsLeft));
            }

            @Override
            public void onFinish() {
                // כשהטיימר נגמר — מעבירים תור
                Toast.makeText(MainActivity.this, "נגמר הזמן! תור עובר לשחקן הבא", Toast.LENGTH_SHORT).show();
                switchTurn();
            }
        }.start();
    }

    private void switchTurn() {
        // החלפת שחקן (בדוגמה בין 1 ל־2)
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        Toast.makeText(this, "תור שחקן " + currentPlayer, Toast.LENGTH_SHORT).show();

        // התחלת טיימר חדש
        startTurnTimer();
    }
}


