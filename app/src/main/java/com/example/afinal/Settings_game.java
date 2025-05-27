package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Settings_game extends AppCompatActivity {

    private Button ChangePassword, Logout, DeleteAccount;
    private Fire_base_handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ChangePassword = findViewById(R.id.ChangePassword);
        Logout = findViewById(R.id.Logout);
        DeleteAccount = findViewById(R.id.DeleteAccount);


        // יצירת אובייקט של Fire_base_handler
        handler = new Fire_base_handler(FirebaseAuth.getInstance(), this);


        ChangePassword.setOnClickListener(v -> showChangePasswordDialog());
        Logout.setOnClickListener(v -> handler.logout());
        DeleteAccount.setOnClickListener(v -> confirmDeleteAccount());

    }
    private void showChangePasswordDialog() {
        EditText input = new EditText(this);
        input.setHint("הכנס סיסמה חדשה");


        new AlertDialog.Builder(this)
                .setTitle("שינוי סיסמה")
                .setView(input)
                .setPositiveButton("שנה", (dialog, which) -> {
                    String newPassword = input.getText().toString().trim();
                    if (!newPassword.isEmpty()) {
                        handler.changePassword(newPassword);
                    } else {
                        Toast.makeText(this, "נא להזין סיסמה תקינה", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("ביטול", null)
                .show();
    }


    private void confirmDeleteAccount() {
        new AlertDialog.Builder(this)
                .setTitle("מחיקת חשבון")
                .setMessage("האם אתה בטוח שברצונך למחוק את החשבון? פעולה זו אינה הפיכה.")
                .setPositiveButton("מחק", (dialog, which) -> handler.deleteAccount())
                .setNegativeButton("ביטול", null)
                .show();
    }

    public void moveToStartGame (View view)
    {
        Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,StartGame.class);
        startActivity(intent);
    }

}