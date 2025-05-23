package com.example.afinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class StartGame extends AppCompatActivity {

    private TextView idTextView;
    private Button c_button;
    private Button j_button;
    private Button joinRoomButton;
    private String myPlayerId;  // הוספת משתנה לשמירת תפקיד השחקן (player1 או player2)
    private String roomId;      // משתנה לשמירת מזהה החדר



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        roomId = FirebaseDatabase.getInstance().getReference("games").push().getKey();  // מזהה ייחודי
        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference("games").child(roomId);
        idTextView = findViewById(R.id.textView3);
        c_button = findViewById(R.id.button11);
        j_button = findViewById(R.id.button10);

        Map<String, Object> roomData = new HashMap<>();
        roomData.put("player1", FirebaseAuth.getInstance().getCurrentUser().getUid()); // או שם, תלוי במבנה שלך
        roomData.put("turn", "player1");  // תור ראשון לשחקן 1
        roomRef.setValue(roomData);
        c_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idTextView.setText(roomId);
            }
        });
        //לשים שורה  של הכנסת טקסט בלמעלה של המסך שהוא עם הקוד של החדר
        // להתחיל עם שורה ריקה ואז אחרי שזה לוחץ על הליסינר זה משנה את הטקסט לרום id

        final String roomId2 = roomCodeInput.getText().toString();
        roomRef = FirebaseDatabase.getInstance().getReference("games").child(roomId);

        roomRef.child("player2").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addOnSuccessListener(aVoid -> {
                    // נכנס לחדר בהצלחה
                    startGame(roomId2, "player2");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "קוד חדר לא תקין", Toast.LENGTH_SHORT).show();
                });

    }

    void startGame(String roomId, String myRole) {
        this.myPlayerId = myRole;  // "player1" או "player2"
        this.roomId = roomId;
        SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("playerId", "abc123");  // או editor.putInt, putBoolean וכו'
        editor.apply();  // או commit() אם אתה רוצה לשמור מיד
        // התחלת הקשבה לתור
        Game.startTurnListener();
    }



    public void moveToGame (View view)
    {
        Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Game.class);
        startActivity(intent);
    }
    public void moveToInstructions (View view)
    {
        Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Instructions.class);
        startActivity(intent);
    }
}