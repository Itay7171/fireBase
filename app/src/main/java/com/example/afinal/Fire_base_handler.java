package com.example.afinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fire_base_handler {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef = database.getReference();
    private static FirebaseAuth auth;
    private static Context context;

    public Fire_base_handler(FirebaseAuth auth, Context context) {
        Fire_base_handler.auth = auth;
        Fire_base_handler.context = context;
    }

    public void signIn(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Sign in successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, StartGame.class);
                            ((Activity) context).startActivity(intent);
                        } else {
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        }
    }

    public void register(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Registration failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // ✅ שינוי סיסמה
    public void changePassword(String newPassword) {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null && !TextUtils.isEmpty(newPassword)) {
            user.updatePassword(newPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Password updated successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to update password.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(context, "Invalid password or user not logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    // ✅ התנתקות
    public void logout() {
        auth.signOut();
        Toast.makeText(context, "Logged out successfully.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, First.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    // ✅ מחיקת חשבון
    public void deleteAccount() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Account deleted.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, First.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        } else {
                            Toast.makeText(context, "Failed to delete account.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(context, "No user is logged in.", Toast.LENGTH_SHORT).show();
        }
    }
}
