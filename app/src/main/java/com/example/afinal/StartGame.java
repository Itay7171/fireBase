package com.example.afinal;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import java.util.UUID;

public class StartGame extends AppCompatActivity {

    private TextView idTextView;
    private Button c_button;
    private Button j_button;
    private String myPlayerId;
    private String roomId;
    private DatabaseReference roomRef;
    private Wifi_Reciver WifiModeChangeReciver = new Wifi_Reciver();
    private String roomId2;
    private Map<String, Object> roomData;

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

        idTextView = findViewById(R.id.textView3);
        c_button = findViewById(R.id.button11);
        j_button = findViewById(R.id.button10);

        roomId = UUID.randomUUID().toString().substring(0, 8);
        roomRef = FirebaseDatabase.getInstance().getReference("games").child(roomId);

        roomRef.child("players/player1").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
        roomRef.child("turn").setValue("player1");

        c_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idTextView.setText(roomId);
                connectGame(roomId, "player1");
            }
        });

        j_button.setOnClickListener(v -> {
            final EditText input = new EditText(this);
            input.setHint("הכנס קוד חדר");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("הצטרפות לחדר");
            builder.setView(input);
            builder.setPositiveButton("הצטרף", (dialog, which) -> {
                roomId2 = input.getText().toString().trim();
                if (roomId2.isEmpty()) {
                    Toast.makeText(this, "אנא הכנס קוד תקין", Toast.LENGTH_SHORT).show();
                    return;
                }

                roomRef = FirebaseDatabase.getInstance().getReference("games").child(roomId2);
                roomRef.child("players/player2").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "הצטרפת בהצלחה!", Toast.LENGTH_SHORT).show();
                            connectGame(roomId2, "player2");
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "קוד חדר לא תקין", Toast.LENGTH_SHORT).show();
                        });

            });
            builder.setNegativeButton("ביטול", (dialog, which) -> dialog.cancel());
            builder.show();
        });

    }

    void connectGame(String roomId, String myRole) {
        roomRef = FirebaseDatabase.getInstance().getReference("games").child(roomId);

        this.myPlayerId = myRole;
        this.roomId = roomId;
        SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("playerId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        editor.apply();

        // מאזין לנתונים של החדר כדי לבדוק אם שני שחקנים מחוברים
        roomRef.child("players").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FirebaseCheck", "Children count: " + snapshot.getChildrenCount());
                if (snapshot.exists() && snapshot.getChildrenCount() == 2) {
                    roomRef.child("board").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // שמור את הלוח בזיכרון המקומי - SharedPreferences או Intent
                            StringBuilder boardJson = new StringBuilder();
                            for (DataSnapshot cardSnap : snapshot.getChildren()) {
                                Card card = cardSnap.getValue(Card.class);
                                if (card != null) {
                                    boardJson.append(card.getId()).append(","); // תוכל לשנות את זה איך שאתה צריך
                                }
                            }

                            // מעבירים ל־Game
                            Intent intent = new Intent(StartGame.this, Game.class);
                            intent.putExtra("roomId", roomId);
                            intent.putExtra("playerId", myRole);
                            intent.putExtra("boardRawData", boardJson.toString()); // אופציונלי, תוכל להעביר JSON אם תעדיף
                            startActivity(intent);
                            roomRef.child("players").removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(StartGame.this, "שגיאה בטעינת הלוח", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // אפשר לטפל בשגיאות כאן אם צריך
            }
        });
    }


    public void moveToGame(View view) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    public void moveToInstructions(View view) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);
    }

    public void moveToSettings(View view) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Settings_game.class);
        startActivity(intent);
    }

    public void moveToLeaderBoard(View view) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LeaderBoard.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(WifiModeChangeReciver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(WifiModeChangeReciver);
    }
}
