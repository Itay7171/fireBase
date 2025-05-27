package com.example.afinal;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
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

public class StartGame extends AppCompatActivity {

    private TextView idTextView;
    private Button c_button;
    private Button j_button;
    private Button joinRoomButton;
    private String myPlayerId;  // הוספת משתנה לשמירת תפקיד השחקן (player1 או player2)
    private String roomId;      // משתנה לשמירת מזהה החדר
    private DatabaseReference roomRef;
    Wifi_Reciver WifiModeChangeReciver = new Wifi_Reciver();


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

        roomId = FirebaseDatabase.getInstance().getReference("games").push().getKey();  // מזהה ייחודי
        roomRef = FirebaseDatabase.getInstance().getReference("games").child(roomId);


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
        // הצטרפות לחדר (Player 2)

        j_button.setOnClickListener(v -> {
            // פתיחת דיאלוג להזנת קוד
            final EditText input = new EditText(this);
            input.setHint("הכנס קוד חדר");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("הצטרפות לחדר");
            builder.setView(input);
            builder.setPositiveButton("הצטרף", (dialog, which) -> {
                String roomId2 = input.getText().toString().trim();
                if (roomId2.isEmpty()) {
                    Toast.makeText(this, "אנא הכנס קוד תקין", Toast.LENGTH_SHORT).show();
                    return;
                }

                roomRef = FirebaseDatabase.getInstance().getReference("games").child(roomId2);
                roomRef.child("player2").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "הצטרפת בהצלחה!", Toast.LENGTH_SHORT).show();
                            startGame(roomId2, "player2");  // פונקציית התחלת המשחק
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "קוד חדר לא תקין", Toast.LENGTH_SHORT).show();
                        });

            });
            builder.setNegativeButton("ביטול", (dialog, which) -> dialog.cancel());
            builder.show();
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
        Game game = new Game();
        game.startTurnListener();

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

    public void moveToSettings (View view)
    {
        Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Settings_game.class);
        startActivity(intent);
    }

    protected void onStart()
    {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(WifiModeChangeReciver,filter);
    }
    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(WifiModeChangeReciver);
    }
}