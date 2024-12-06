                    package com.example.afinal;

                    import android.content.Context;
                    import android.text.TextUtils;
                    import android.widget.Toast;

                    import androidx.annotation.NonNull;

                    import com.google.android.gms.tasks.OnCompleteListener;
                    import com.google.android.gms.tasks.OnFailureListener;
                    import com.google.android.gms.tasks.Task;
                    import com.google.firebase.auth.AuthResult;
                    import com.google.firebase.auth.FirebaseAuth;
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

                        public void singIn(String email, String password) {

                            // Check if email or password fields are empty
                            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                                Toast.makeText(context, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Sign in with email and password
                                auth.signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // If sign in is successful
                                                    Toast.makeText(context, "Sign in successful!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    // If sign in fails, display a message
                                                    Toast.makeText(context, "Authentication failed: ", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Additional failure handling if required
                                                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                // Check if email or password fields are empty
                                            }
                                        });

                            }
                        }
                        public void register(String email, String password)
                        {
                            auth.createUserWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // If sign up is successful
                                        Toast.makeText(context, " successful!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign up fails, display a message
                                        Toast.makeText(context, "failed" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }




