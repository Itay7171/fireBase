package com.example.afinal;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Game extends AppCompatActivity {



    ImageButton imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6,imageButton7,imageButton8,imageButton9,imageButton10,imageButton11 ;
    private ImageButton handClickedButton = null;
    private ImageButton secondClickedButton = null;
    private ImageButton thirdClickedButton = null; // הוספת המשתנה החסר
    private DatabaseReference gameRef;
    Wifi_Reciver WifiModeChangeReciver = new Wifi_Reciver();

    private Button throwButton;
    LinkedList<Card> player1RoundCards = new LinkedList<>();
    LinkedList<Card> player2RoundCards = new LinkedList<>();
    private int player1TotalScore = 0;
    private int player2TotalScore = 0;
    private int currentRound = 1;
    private String lastPlayerToTakeCard = null;
    private int pendingAnimations = 0;
    private boolean shouldRefillHand = false;





    Card card_a_H = new Card(1,"H","1H",R.drawable.heart_a_card);
    Card card2H = new Card(2,"H","2H",R.drawable.heart2_card);
    Card card3H = new Card(3,"H","3H",R.drawable.heart3_card);
    Card card4H = new Card(4,"H","4H",R.drawable.heart4_card);
    Card card5H = new Card(5,"H","5H",R.drawable.heart5_card);
    Card card6H = new Card(6,"H","6H",R.drawable.heart6_card);
    Card card7H = new Card(7,"H","7H",R.drawable.heart7_card);
    Card card8H = new Card(8,"H","8H",R.drawable.heart8_card);
    Card card9H = new Card(9,"H","9H",R.drawable.heart9_card);
    Card card10H = new Card(10,"H","10H",R.drawable.heart10_card);


    Card card_a_D = new Card(1,"D","1D",R.drawable.diamond_a_card);
    Card card2D = new Card(2,"D","2D",R.drawable.diamond2_card);
    Card card3D = new Card(3,"D","3D",R.drawable.diamond3_card);
    Card card4D = new Card(4,"D","4D",R.drawable.diamond4_card);
    Card card5D = new Card(5,"D","5D",R.drawable.diamond5_card);
    Card card6D = new Card(6,"D","6D",R.drawable.diamond6_card);
    Card card7D = new Card(7,"D","7D",R.drawable.diamond7_card);
    Card card8D = new Card(8,"D","8D",R.drawable.diamond8_card);
    Card card9D = new Card(9,"D","9D",R.drawable.diamond9_card);
    Card card10D = new Card(10,"D","10D",R.drawable.diamond10_card);

    Card card_a_S = new Card(1,"S","1S",R.drawable.spade_a_card);
    Card card2S = new Card(2,"S","2S",R.drawable.spade2_card);
    Card card3S = new Card(3,"S","3S",R.drawable.spade3_card);
    Card card4S = new Card(4,"S","4S",R.drawable.spade4_card);
    Card card5S = new Card(5,"S","5S",R.drawable.spade5_card);
    Card card6S = new Card(6,"S","6S",R.drawable.spade6_card);
    Card card7S = new Card(7,"S","7S",R.drawable.spades7_card);
    Card card8S = new Card(8,"S","8S",R.drawable.spades8_card);
    Card card9S = new Card(9,"S","9S",R.drawable.spades9_card);
    Card card10S = new Card(10,"S","10S",R.drawable.spades10_card);

    Card card_a_C = new Card(1,"C","1C",R.drawable.clover_a_card);
    Card card2C = new Card(2,"C","2C",R.drawable.clover2_card);
    Card card3C = new Card(3,"C","3C",R.drawable.clover3_card);
    Card card4C = new Card(4,"C","4C",R.drawable.clover4_card);
    Card card5C = new Card(5,"C","5C",R.drawable.clover5_card);
    Card card6C = new Card(6,"C","6C",R.drawable.clover6_card);
    Card card7C = new Card(7,"C","7C",R.drawable.clover7_card);
    Card card8C = new Card(8,"C","8C",R.drawable.clover8_card);
    Card card9C = new Card(9,"C","9C",R.drawable.clover9_card);
    Card card10C = new Card(10,"C","10C",R.drawable.clover10_card);


    int indexOfNextCard = 0;
    Card[] arr = {card3D,card4D,card5D,card6D,card7D,card8D,card9D,card10D,card_a_S,card2S,card3S,card4S,card5S,card6S,card7S,card8S,card9S,card10S};
    ///Card[] arr = {card3H,card4H,card5H,card6H,card7H,card8H,card9H,card10H,card_a_D,card2D,card3D,card4D,card5D,card6D,card7D,card8D,card9D,card10D,card_a_S,card2S,card3S,card4S,card5S,card6S,card7S,card8S,card9S,card10S,card_a_C,card2C,card3C,card4C,card5C,card6C,card7C,card8C,card9C,card10C};
    Random rnd = new Random();

    Card[] use_card = new Card[9];
    Card[] OnTheBord = new Card[8];
    Random random = new Random();
    int[] onTheHand = new int[3];

    private TextView timerText;
    private CountDownTimer countDownTimer;
    private int currentPlayer = 1; // נניח שחקן 1 מתחיל
    private boolean isExitingGame = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        timerText = findViewById(R.id.timerText);

        // מקבלים את roomId שנשלח מ-StartGame
        String roomId = getIntent().getStringExtra("roomId");
        if (roomId == null) {
            Toast.makeText(this, "Room ID לא התקבל", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        gameRef = FirebaseDatabase.getInstance().getReference("games").child(roomId);
        setupPlayerExitListener();


        // מקבלים את התפקיד של השחקן (player1 או player2)
        String playerId = getIntent().getStringExtra("playerId");

        if (playerId == null) {
            Toast.makeText(this, "שגיאה בקבלת תפקיד", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // שמירת playerId בזיכרון
        SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
        prefs.edit().putString("playerId", playerId).apply();

        Log.d("Game", "=== PLAYER IDENTITY ===");
        Log.d("Game", "This device is: " + playerId);
        Log.d("Game", "Room ID: " + getIntent().getStringExtra("roomId"));

        // אם זה player1 – הוא מתחיל
        if (playerId.equals("player1")) {
            gameRef.child("turn").setValue("player1");
        }

        // התחלת המשחק והאזנה לתור
        startGame();
        startTurnListener();
        setupNewRoundListener();
        setupScoreListener();
        setupBoardListener();




    }


    private void setupPlayerExitListener() {
        gameRef.child("playerExit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String exitingPlayer = snapshot.getValue(String.class);
                if (exitingPlayer != null && !isExitingGame) {
                    SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
                    String currentPlayerId = prefs.getString("playerId", "");

                    // אם השחקן שיצא זה לא השחקן הנוכחי
                    if (!exitingPlayer.equals(currentPlayerId)) {
                        String playerName = exitingPlayer.equals("player1") ? "שחקן 1" : "שחקן 2";
                        Toast.makeText(Game.this, playerName + " עזב את המשחק", Toast.LENGTH_LONG).show();

                        // מחזיר למסך StartGame אחרי 2 שניות
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(Game.this, StartGame.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    // עדכן את הפונקציה moveToStartGame הקיימת
    public void moveToStartGame(View view) {
        exitGame();
    }

    // הוסף פונקציה חדשה לטיפול ביציאה מהמשחק
    private void exitGame() {
        isExitingGame = true;

        SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
        String currentPlayerId = prefs.getString("playerId", "");

        // שליחת הודעה ש-Firebase על יציאת השחקן
        gameRef.child("playerExit").setValue(currentPlayerId);

        // מעבר למסך StartGame
        Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        exitGame();
        super.onBackPressed();
    }


    // הוסף override ל-onDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // ניקוי טיימר
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // אם יוצאים מהמשחק, שלח הודעה
        if (!isExitingGame) {
            SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
            String currentPlayerId = prefs.getString("playerId", "");
            if (currentPlayerId != null && !currentPlayerId.isEmpty()) {
                gameRef.child("playerExit").setValue(currentPlayerId);
            }
        }
    }

    // הוסף override ל-onPause
    @Override
    protected void onPause() {
        super.onPause();

        // אם האפליקציה עוברת ל-background, זה עלול להיות יציאה
        if (isFinishing()) {
            exitGame();
        }
    }



    private void setupNewRoundListener() {
        gameRef.child("currentRound").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer roundNumber = snapshot.getValue(Integer.class);
                Log.d("Game", "Round listener triggered: " + roundNumber + ", Current: " + currentRound);

                if (roundNumber != null && roundNumber > currentRound) {
                    currentRound = roundNumber;

                    SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
                    String playerId = prefs.getString("playerId", null);

                    Log.d("Game", "New round detected by: " + playerId);

                    // הודעת Toast לשני השחקנים
                    Toast.makeText(Game.this, "סיבוב " + currentRound + " מתחיל!", Toast.LENGTH_LONG).show();

                    // איפוס נתונים מקומיים
                    lastPlayerToTakeCard = null;
                    player1RoundCards.clear();
                    player2RoundCards.clear();

                    // ניקוי מיידי של כל הקלפים
                    clearAllCardsImmediately();

                    if (playerId.equals("player2")) {
                        // רק שחקן 2 טוען מ-Firebase
                        loadBoardFromFirebase();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void setupBoardListener() {
        DatabaseReference boardRef = gameRef.child("board");

        boardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // תמיד נעדכן את הלוח המקומי מ-Firebase
                updateLocalBoard(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void updateLocalBoard(DataSnapshot snapshot) {
        for (int i = 0; i < 8; i++) {
            DataSnapshot cardSnapshot = snapshot.child("position" + i);

            if (cardSnapshot.exists()) {
                Card card = cardSnapshot.getValue(Card.class);
                if (card != null) {
                    OnTheBord[i] = card;
                    ImageButton button = getBoardButton(i);
                    button.setImageResource(card.getImageResource());
                    button.setTag(card);
                    button.setVisibility(View.VISIBLE);
                }
            } else {
                OnTheBord[i] = null;
                ImageButton button = getBoardButton(i);
                if (button != null) {
                    button.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public void startGame(){
        DatabaseReference boardRef = FirebaseDatabase.getInstance()
                .getReference("rooms").child("ROOM_CODE").child("board");




        // אתחול כפתור "לזרוק"
        throwButton = findViewById(R.id.throwButton);

        imageButton4 = findViewById(R.id.imageButton4);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);
        imageButton7 = findViewById(R.id.imageButton7);
        imageButton8 = findViewById(R.id.imageButton8);
        imageButton9 = findViewById(R.id.imageButton9);
        imageButton10 = findViewById(R.id.imageButton10);
        imageButton11= findViewById(R.id.imageButton11);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);


        SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
        String playerId = prefs.getString("playerId", null);

        if (playerId.equals("player1")) {
            // רק שחקן 1 יוצר את הלוח
            setupInitialBoard();
            setupCardListeners();
        } else {
            // שחקן 2 מחכה ללוח מ-Firebase
            loadBoardFromFirebase();
        }


        // שמירה של הלוח ל-Firebase (4 קלפים בלבד)
        for (int i = 0; i < 4; i++) {
            Card card = OnTheBord[i];
            boardRef.child(String.valueOf(i)).setValue(card);
        }


        imageButton8.setVisibility(View.INVISIBLE);



        imageButton9.setVisibility(View.INVISIBLE);


        imageButton10.setVisibility(View.INVISIBLE);


        imageButton11.setVisibility(View.INVISIBLE);




        // מאזין לכפתור "לזרוק"
        setthrowButtonClickListener();
    }


    private void setupInitialBoard() {
        // ערבוב הקלפים (רק עבור שחקן 1)
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Card a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }

        // יצירת הלוח
        createBoard();

        // שמירת הלוח ל-Firebase
        saveBoardToFirebase();
    }

    private void createBoard() {
        // הקוד הקיים ליצירת הלוח
        imageButton4.setImageResource(arr[indexOfNextCard].getImageResource());
        imageButton4.setTag(arr[indexOfNextCard]);
        OnTheBord[0] = arr[indexOfNextCard];
        arr[indexOfNextCard].SetIndex(0);
        indexOfNextCard++;

        imageButton5.setImageResource(arr[indexOfNextCard].getImageResource());
        imageButton5.setTag(arr[indexOfNextCard]);
        OnTheBord[1] = arr[indexOfNextCard];
        arr[indexOfNextCard].SetIndex(1);
        indexOfNextCard++;

        imageButton6.setImageResource(arr[indexOfNextCard].getImageResource());
        imageButton6.setTag(arr[indexOfNextCard]);
        OnTheBord[2] = arr[indexOfNextCard];
        arr[indexOfNextCard].SetIndex(2);
        indexOfNextCard++;

        imageButton7.setImageResource(arr[indexOfNextCard].getImageResource());
        imageButton7.setTag(arr[indexOfNextCard]);
        OnTheBord[3] = arr[indexOfNextCard];
        arr[indexOfNextCard].SetIndex(3);
        indexOfNextCard++;



        // הגדרת הקלפים ביד
        setupHandCards();
    }

    private void setupHandCards() {
        // וודא שיש מספיק קלפים
        if (indexOfNextCard + 2 >= arr.length) {
            Log.e("Game", "Not enough cards for hand setup");
            return;
        }

        imageButton1.setImageResource(arr[indexOfNextCard].getImageResource());
        imageButton1.setTag(arr[indexOfNextCard]);
        imageButton1.setVisibility(View.VISIBLE);
        onTheHand[0] = indexOfNextCard;
        indexOfNextCard++;

        imageButton2.setImageResource(arr[indexOfNextCard].getImageResource());
        imageButton2.setTag(arr[indexOfNextCard]);
        imageButton2.setVisibility(View.VISIBLE);
        onTheHand[1] = indexOfNextCard;
        indexOfNextCard++;

        imageButton3.setImageResource(arr[indexOfNextCard].getImageResource());
        imageButton3.setTag(arr[indexOfNextCard]);
        imageButton3.setVisibility(View.VISIBLE);
        onTheHand[2] = indexOfNextCard;
        indexOfNextCard++;
    }

    private void saveBoardToFirebase() {
        DatabaseReference boardRef = gameRef.child("board");

        // שמירת הקלפים על הלוח
        for (int i = 0; i < 4; i++) {
            boardRef.child("position" + i).setValue(OnTheBord[i]);
        }

        // שמירת המערך המעורבב
        DatabaseReference deckRef = gameRef.child("deck");
        for (int i = 0; i < arr.length; i++) {
            deckRef.child(String.valueOf(i)).setValue(arr[i]);
        }

        // שמירת indexOfNextCard
        gameRef.child("nextCardIndex").setValue(indexOfNextCard);

        // שמירת מספר הסיבוב הנוכחי
        gameRef.child("currentRound").setValue(currentRound);
    }

    private void loadBoardFromFirebase() {
        DatabaseReference boardRef = gameRef.child("board");

        boardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // טעינת הלוח
                for (int i = 0; i < 4; i++) {
                    DataSnapshot cardSnapshot = snapshot.child("position" + i);
                    Card card = cardSnapshot.getValue(Card.class);
                    if (card != null) {
                        OnTheBord[i] = card;
                        ImageButton button = getBoardButton(i);
                        button.setImageResource(card.getImageResource());
                        button.setTag(card);
                    }
                }

                // טעינת המערך המעורבב
                loadDeckFromFirebase();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Game.this, "שגיאה בטעינת הלוח", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDeckFromFirebase() {
        DatabaseReference deckRef = gameRef.child("deck");

        deckRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // טעינת המערך המעורבב
                for (int i = 0; i < arr.length; i++) {
                    DataSnapshot cardSnapshot = snapshot.child(String.valueOf(i));
                    Card card = cardSnapshot.getValue(Card.class);
                    if (card != null) {
                        arr[i] = card;
                    }
                }

                // טעינת indexOfNextCard
                gameRef.child("nextCardIndex").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Integer nextIndex = snapshot.getValue(Integer.class);
                        if (nextIndex != null) {
                            indexOfNextCard = nextIndex;
                        }

                        // הגדרת הקלפים ביד
                        setupHandCards();

                        // הפעלת המאזינים
                        setupCardListeners();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void setupCardListeners() {
        setCardClickListener1(imageButton1, true);
        setCardClickListener1(imageButton2, true);
        setCardClickListener1(imageButton3, true);
        setCardClickListener1(imageButton4, false);
        setCardClickListener1(imageButton5, false);
        setCardClickListener1(imageButton6, false);
        setCardClickListener1(imageButton7, false);
        setCardClickListener1(imageButton8, false);
        setCardClickListener1(imageButton9, false);
        setCardClickListener1(imageButton10, false);
        setCardClickListener1(imageButton11, false);

        setthrowButtonClickListener();
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

    private void startTurnTimer() {
        // עצירת טיימר קודם אם קיים
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                timerText.setText(String.valueOf(secondsLeft));
            }

            @Override
            public void onFinish() {
                Toast.makeText(Game.this, "נגמר הזמן! תור עובר לשחקן הבא", Toast.LENGTH_SHORT).show();
                endTurn();
            }
        }.start();
    }


    private void setCardClickListener1(ImageButton button, boolean isHandCard)
    {
        button.setOnClickListener(v -> {
            if (isHandCard) {
                // בחירת קלף מהיד
                if (handClickedButton == null) {
                    handClickedButton = (ImageButton) v;
                    Log.d("Game", "Selected card from hand: " + handClickedButton.getTag());
                } else {
                    handClickedButton = (ImageButton) v;
                    Toast.makeText(this, "You changed your hand card.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // בחירת קלף מהלוח
                if (handClickedButton != null && secondClickedButton == null) {
                    secondClickedButton = (ImageButton) v;
                    Log.d("Game", "Selected first board card: " + secondClickedButton.getTag());
                } else if (handClickedButton != null && secondClickedButton != null && thirdClickedButton == null) {
                    thirdClickedButton = (ImageButton) v;
                    Log.d("Game", "Selected second board card: " + thirdClickedButton.getTag());

                    // בדיקה של התאמה (שניים או שלושה קלפים)
                    checkMatch();
                }
            }
        });
    }

    public void startTurnListener()
    {

        gameRef.child("turn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentTurn = snapshot.getValue(String.class);
                if (currentTurn == null) return;
                SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
                String playerId = prefs.getString("playerId", null);  // ערך ברירת מחדל אם לא קיים

                if (currentTurn.equals(playerId)) {
                    enablePlayerControls();  // תורך לשחק
                } else {
                    disablePlayerControls(); // תחכה
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    public void endTurn() {
        // בדיקה שהטיימר פועל לפני עצירה
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
        String playerId = prefs.getString("playerId", null);

        if (playerId != null) {
            // שמירה של מי ביצע את התור האחרון
            prefs.edit().putString("lastTurn", playerId).apply();

            String nextPlayer = playerId.equals("player1") ? "player2" : "player1";
            gameRef.child("turn").setValue(nextPlayer);
        }
    }

    private void enablePlayerControls() {
            throwButton.setEnabled(true);
            imageButton1.setEnabled(true);
            imageButton2.setEnabled(true);
            imageButton3.setEnabled(true);
            imageButton4.setEnabled(true);
            imageButton5.setEnabled(true);
            imageButton6.setEnabled(true);
            imageButton7.setEnabled(true);
            imageButton8.setEnabled(true);
            imageButton9.setEnabled(true);
            imageButton10.setEnabled(true);
            imageButton11.setEnabled(true);

        startTurnTimer();


        Log.d("Game", "Player controls enabled");

    }

    private void disablePlayerControls() {
        // דוגמה לנטרול כפתורי קלפים ביד
        throwButton.setEnabled(false);
        imageButton1.setEnabled(false);
        imageButton2.setEnabled(false);
        imageButton3.setEnabled(false);
        imageButton4.setEnabled(false);
        imageButton5.setEnabled(false);
        imageButton6.setEnabled(false);
        imageButton7.setEnabled(false);
        imageButton8.setEnabled(false);
        imageButton9.setEnabled(false);
        imageButton10.setEnabled(false);
        imageButton11.setEnabled(false);

        Log.d("Game", "Player controls disabled");
    }

    private void setupScoreListener() {
        gameRef.child("roundScores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer roundNumber = snapshot.child("roundNumber").getValue(Integer.class);
                Integer player1Score = snapshot.child("player1RoundScore").getValue(Integer.class);
                Integer player2Score = snapshot.child("player2RoundScore").getValue(Integer.class);

                if (roundNumber != null && player1Score != null && player2Score != null) {
                    // עדכון לוח הניקוד לכל השחקנים באותו זמן
                    TextView textView5 = findViewById(R.id.textView5);
                    textView5.setText(" " + player1Score + "    :    " + player2Score + " ");

                    // עדכון הניקוד הכולל
                    player1TotalScore += player1Score;
                    player2TotalScore += player2Score;

                    Log.d("Game", "Score updated for round " + roundNumber +
                            " - Player1: " + player1Score + ", Player2: " + player2Score);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }




















    private void checkMatch() {
        if (handClickedButton != null && secondClickedButton != null) {
            Card handCard = (Card) handClickedButton.getTag();
            Card boardCard1 = (Card) secondClickedButton.getTag();

            if (handCard != null && boardCard1 != null) {
                if (handCard.getNumber() == boardCard1.getNumber()) {
                    SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
                    String currentPlayerId = prefs.getString("playerId", "");

                    lastPlayerToTakeCard = currentPlayerId;

                    if (currentPlayerId.equals("player1")) {
                        player1RoundCards.add(handCard);
                        player1RoundCards.add(boardCard1);
                    } else {
                        player2RoundCards.add(handCard);
                        player2RoundCards.add(boardCard1);
                    }

                    // סמן שצריך לחדש את הקלפים ביד אחרי האנימציות
                    shouldRefillHand = true;
                    pendingAnimations = 2; // יש לנו 2 אנימציות

                    animateCardDisappear(handClickedButton, 0);
                    animateCardDisappear(secondClickedButton, 200);
                    updateBoardArray(boardCard1);
                    updateBoardInFirebase();
                    resetSelection();
                    endTurn();
                    return;
                }
            }
        }

        if (handClickedButton != null && secondClickedButton != null && thirdClickedButton != null) {
            checkTripleMatch((Card) handClickedButton.getTag());
        }
    }

    private void checkTripleMatch(Card handCard) {
        if (handCard != null && secondClickedButton != null && thirdClickedButton != null) {
            Card boardCard1 = (Card) secondClickedButton.getTag();
            Card boardCard2 = (Card) thirdClickedButton.getTag();

            if (boardCard1 != null && boardCard2 != null) {
                if (boardCard1.getNumber() + boardCard2.getNumber() == handCard.getNumber()) {
                    SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
                    String currentPlayerId = prefs.getString("playerId", "");

                    lastPlayerToTakeCard = currentPlayerId;

                    if (currentPlayerId.equals("player1")) {
                        player1RoundCards.add(handCard);
                        player1RoundCards.add(boardCard1);
                        player1RoundCards.add(boardCard2);
                    } else {
                        player2RoundCards.add(handCard);
                        player2RoundCards.add(boardCard1);
                        player2RoundCards.add(boardCard2);
                    }

                    // סמן שצריך לחדש את הקלפים ביד אחרי האנימציות
                    shouldRefillHand = true;
                    pendingAnimations = 3; // יש לנו 3 אנימציות

                    animateCardDisappear(handClickedButton, 0);
                    animateCardDisappear(secondClickedButton, 200);
                    animateCardDisappear(thirdClickedButton, 400);
                    updateBoardArray(boardCard1, boardCard2);
                    updateBoardInFirebase();
                    resetSelection();
                    endTurn();
                } else {
                    Toast.makeText(this, "No match found!", Toast.LENGTH_SHORT).show();
                    resetSelection();
                }
            }
        }
    }

    private void resetSelection() {
        handClickedButton = null;
        secondClickedButton = null;
        thirdClickedButton = null;
    }



    private void animateCardDisappear(ImageButton button, int delay) {
        Animation fadeUpAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_out);
        fadeUpAnimation.setStartOffset(delay);
        fadeUpAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                button.setVisibility(View.INVISIBLE);

                // הקטן את מונה האנימציות
                pendingAnimations--;

                // אם כל האנימציות הסתיימו וצריך לחדש את הקלפים ביד
                if (pendingAnimations == 0 && shouldRefillHand) {
                    shouldRefillHand = false;

                    // בדיקה אם יש מספיק קלפים לפני חידוש היד
                    int remainingCards = arr.length - indexOfNextCard;
                    if (remainingCards < 3) {
                        Log.d("Game", "Not enough cards after animation. Ending round...");
                        calculateRoundResults();
                    } else {
                        checkAndRefillHand();
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        button.startAnimation(fadeUpAnimation);
    }

    private void updateBoardArray(Card... cardsToRemove) {
        for (Card cardToRemove : cardsToRemove) {
            for (int i = 0; i < OnTheBord.length; i++) {
                if (OnTheBord[i] == cardToRemove) { // השוואת הפניות במקום equals
                    OnTheBord[i] = null;
                    break;
                }
            }
        }
    }





    private void setthrowButtonClickListener() {
        throwButton.setOnClickListener(v -> {
            if (handClickedButton == null) {
                Toast.makeText(this, "לא נבחר קלף מהיד!", Toast.LENGTH_SHORT).show();
                return;
            }

            Card selectedCard = (Card) handClickedButton.getTag();
            if (selectedCard == null) {
                Toast.makeText(this, "הקלף הנבחר לא תקין!", Toast.LENGTH_SHORT).show();
                return;
            }

            // מציאת מקום פנוי בלוח
            boolean cardPlaced = false;
            for (int i = 0; i < OnTheBord.length; i++) {
                if (OnTheBord[i] == null) {
                    OnTheBord[i] = selectedCard;

                    ImageButton boardButton = getBoardButton(i);
                    if (boardButton != null) {
                        boardButton.setImageResource(selectedCard.getImageResource());
                        boardButton.setTag(selectedCard);
                        boardButton.setVisibility(View.VISIBLE);
                    }

                    // הסתר את הקלף מהיד
                    handClickedButton.setVisibility(View.INVISIBLE);
                    handClickedButton.setTag(null);
                    handClickedButton = null;

                    Log.d("Game", "Card thrown to board at position: " + i);

                    updateBoardInFirebase();

                    // בדיקה אם צריך למלא קלף בודד או לסיים את הסיבוב
                    checkSingleCardRefill();
                    endTurn();

                    cardPlaced = true;
                    break;
                }
            }

            if (!cardPlaced) {
                Toast.makeText(this, "אין מקום פנוי בלוח!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkSingleCardRefill() {
        // בדיקה כמה קלפים נשארו
        int remainingCards = arr.length - indexOfNextCard;

        if (remainingCards <= 0) {
            // אם הקלפים נגמרו, בדוק אם כל השחקנים צריכים לסיים
            checkIfRoundShouldEnd();
            return;
        }

        // מציאת איזה קלף נזרק (הקלף הראשון שלא נראה)
        if (imageButton1.getVisibility() == View.INVISIBLE) {
            imageButton1.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton1.setTag(arr[indexOfNextCard]);
            onTheHand[0] = indexOfNextCard;
            indexOfNextCard++;
            imageButton1.setVisibility(View.VISIBLE);
        } else if (imageButton2.getVisibility() == View.INVISIBLE) {
            imageButton2.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton2.setTag(arr[indexOfNextCard]);
            onTheHand[1] = indexOfNextCard;
            indexOfNextCard++;
            imageButton2.setVisibility(View.VISIBLE);
        } else if (imageButton3.getVisibility() == View.INVISIBLE) {
            imageButton3.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton3.setTag(arr[indexOfNextCard]);
            onTheHand[2] = indexOfNextCard;
            indexOfNextCard++;
            imageButton3.setVisibility(View.VISIBLE);
        }

        updateBoardInFirebase();
    }

    private void checkIfRoundShouldEnd() {
        // בדיקה אם נגמרו הקלפים ולכולם אין יד מלאה
        int remainingCards = arr.length - indexOfNextCard;

        if (remainingCards <= 0) {
            // הקלפים נגמרו - סיים את הסיבוב
            Log.d("Game", "Deck is empty. Ending round...");
            calculateRoundResults();
        }
    }

    private void updateBoardInFirebase() {
        DatabaseReference boardRef = gameRef.child("board");

        // עדכון הלוח ב-Firebase
        for (int i = 0; i < OnTheBord.length; i++) {
            if (OnTheBord[i] != null) {
                boardRef.child("position" + i).setValue(OnTheBord[i]);
            } else {
                boardRef.child("position" + i).removeValue();
            }
        }

        // עדכון indexOfNextCard ו deck
        gameRef.child("nextCardIndex").setValue(indexOfNextCard);

        // עדכון המערך המעורבב אם השתנה
        DatabaseReference deckRef = gameRef.child("deck");
        for (int i = 0; i < arr.length; i++) {
            deckRef.child(String.valueOf(i)).setValue(arr[i]);
        }
    }





    private void checkAndRefillHand() {
        boolean handCard1Empty = imageButton1.getVisibility() == View.INVISIBLE;
        boolean handCard2Empty = imageButton2.getVisibility() == View.INVISIBLE;
        boolean handCard3Empty = imageButton3.getVisibility() == View.INVISIBLE;

        // בדיקה אם כל הקלפים ביד נגמרו
        boolean allInvisible = handCard1Empty && handCard2Empty && handCard3Empty;

        if (allInvisible) {
            // בדיקה כמה קלפים נשארו בקופה
            int remainingCards = arr.length - indexOfNextCard;
            Log.d("Game", "Cards remaining in deck: " + remainingCards);

            if (remainingCards < 3) {
                // אם נשארו פחות מ-3 קלפים, סיים את הסיבוב
                Log.d("Game", "Not enough cards for full hand. Ending round...");
                calculateRoundResults();
                return;
            }

            // אם יש מספיק קלפים, מלא את היד
            Log.d("Game", "Refilling hand with 3 cards...");

            imageButton1.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton1.setTag(arr[indexOfNextCard]);
            onTheHand[0] = indexOfNextCard;
            indexOfNextCard++;
            imageButton1.setVisibility(View.VISIBLE);

            imageButton2.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton2.setTag(arr[indexOfNextCard]);
            onTheHand[1] = indexOfNextCard;
            indexOfNextCard++;
            imageButton2.setVisibility(View.VISIBLE);

            imageButton3.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton3.setTag(arr[indexOfNextCard]);
            onTheHand[2] = indexOfNextCard;
            indexOfNextCard++;
            imageButton3.setVisibility(View.VISIBLE);

            // עדכון ה-Firebase
            updateBoardInFirebase();

        } else {
            // אם לא כל הקלפים ביד נגמרו, בדוק אם צריך למלא קלפים בודדים
            refillIndividualCards();
        }
    }

    private void refillIndividualCards() {
        int remainingCards = arr.length - indexOfNextCard;

        // מלא רק קלפים שנגמרו, אבל רק אם יש מספיק קלפים בקופה
        if (imageButton1.getVisibility() == View.INVISIBLE && remainingCards > 0) {
            imageButton1.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton1.setTag(arr[indexOfNextCard]);
            onTheHand[0] = indexOfNextCard;
            indexOfNextCard++;
            imageButton1.setVisibility(View.VISIBLE);
            remainingCards--;
        }

        if (imageButton2.getVisibility() == View.INVISIBLE && remainingCards > 0) {
            imageButton2.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton2.setTag(arr[indexOfNextCard]);
            onTheHand[1] = indexOfNextCard;
            indexOfNextCard++;
            imageButton2.setVisibility(View.VISIBLE);
            remainingCards--;
        }

        if (imageButton3.getVisibility() == View.INVISIBLE && remainingCards > 0) {
            imageButton3.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton3.setTag(arr[indexOfNextCard]);
            onTheHand[2] = indexOfNextCard;
            indexOfNextCard++;
            imageButton3.setVisibility(View.VISIBLE);
            remainingCards--;
        }

        // אם אחרי המילוי עדיין יש קלפים ריקים ביד, זה אומר שהקלפים נגמרו
        boolean stillHasEmptyCards = imageButton1.getVisibility() == View.INVISIBLE ||
                imageButton2.getVisibility() == View.INVISIBLE ||
                imageButton3.getVisibility() == View.INVISIBLE;

        if (stillHasEmptyCards && indexOfNextCard >= arr.length) {
            Log.d("Game", "Deck is empty and hand is not full. Ending round...");
            calculateRoundResults();
        } else if (remainingCards > 0) {
            // עדכון ה-Firebase רק אם עדכנו משהו
            updateBoardInFirebase();
        }
    }



    private void calculateRoundResults() {
        SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
        String currentPlayerId = prefs.getString("playerId", "UNKNOWN");
        Log.d("Game", "=== ROUND END ===");
        Log.d("Game", "Current player ID: " + currentPlayerId);
        Log.d("Game", "Index: " + indexOfNextCard + ", Array length: " + arr.length);

        // רק שחקן 1 מחשב ושומר את התוצאות
        if (!currentPlayerId.equals("player1")) {
            Log.d("Game", "Player2 - waiting for results from Firebase");
            return;
        }

        // הוספת קלפים נותרים על הלוח לשחקן האחרון שלקח קלף
        if (lastPlayerToTakeCard != null) {
            Log.d("Game", "Adding remaining board cards to: " + lastPlayerToTakeCard);
            for (int i = 0; i < OnTheBord.length; i++) {
                if (OnTheBord[i] != null) {
                    Log.d("Game", "Adding card to " + lastPlayerToTakeCard + ": " + OnTheBord[i].getShape() + OnTheBord[i].getNumber());
                    if (lastPlayerToTakeCard.equals("player1")) {
                        player1RoundCards.add(OnTheBord[i]);
                    } else {
                        player2RoundCards.add(OnTheBord[i]);
                    }
                }
            }
        }

        // חישוב הנקודות
        int player1TotalCards = player1RoundCards.size();
        int player1Diamonds = 0;
        int player1Sevens = 0;

        for (Card card : player1RoundCards) {
            if (Objects.equals(card.getShape(), "D")) {
                player1Diamonds++;
            }
            if (card.getNumber() == 7) {
                player1Sevens++;
            }
        }

        int player2TotalCards = player2RoundCards.size();
        int player2Diamonds = 0;
        int player2Sevens = 0;

        for (Card card : player2RoundCards) {
            if (Objects.equals(card.getShape(), "D")) {
                player2Diamonds++;
            }
            if (card.getNumber() == 7) {
                player2Sevens++;
            }
        }

        int player1RoundScore = 0;
        int player2RoundScore = 0;

        // חישוב נקודות לפי הכללים
        if (player1TotalCards > player2TotalCards) {
            player1RoundScore++;
        } else if (player2TotalCards > player1TotalCards) {
            player2RoundScore++;
        }

        if (player1Diamonds > player2Diamonds) {
            player1RoundScore++;
        } else if (player2Diamonds > player1Diamonds) {
            player2RoundScore++;
        }

        if (player1Sevens > player2Sevens) {
            player1RoundScore++;
        } else if (player2Sevens > player1Sevens) {
            player2RoundScore++;
        }

        Log.d("Game", "Player1: " + player1TotalCards + " cards, " + player1Diamonds + " diamonds, " + player1Sevens + " sevens");
        Log.d("Game", "Player2: " + player2TotalCards + " cards, " + player2Diamonds + " diamonds, " + player2Sevens + " sevens");
        Log.d("Game", "Scores - Player1: " + player1RoundScore + ", Player2: " + player2RoundScore);

        // שמירת התוצאות ב-Firebase
        DatabaseReference scoresRef = gameRef.child("roundScores");
        scoresRef.child("roundNumber").setValue(currentRound);
        scoresRef.child("player1RoundScore").setValue(player1RoundScore);
        scoresRef.child("player2RoundScore").setValue(player2RoundScore);

        // ניקוי מיידי של כל הקלפים לפני ההמתנה
        clearAllCardsImmediately();

        // עיכוב של 5 שניות לפני התחלת סיבוב חדש
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startNewRound();
            }
        }, 5000);
    }

    private void clearAllCardsImmediately() {
        // ניקוי קלפי היד מיד
        clearHandCards();

        // ניקוי קלפי הלוח מיד
        clearBoardCompletely();

        Log.d("Game", "All cards cleared immediately before new round delay");
    }

    private void startNewRound() {
        SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
        String playerId = prefs.getString("playerId", null);

        Log.d("Game", "=== NEW ROUND LOGIC ===");
        Log.d("Game", "Player trying to start round: " + playerId);
        Log.d("Game", "Current round before increment: " + currentRound);

        // רק שחקן 1 יוצר סיבוב חדש
        if (playerId.equals("player1")) {
            currentRound++;
            Log.d("Game", "Starting round: " + currentRound);

            // איפוס נתונים לסיבוב החדש
            resetRoundData();

            // יצירת קופה חדשה
            refillDeck();

            // ערבוב הקלפים מחדש
            shuffleDeck();

            // איפוס מונה הקלפים
            indexOfNextCard = 0;

            // ניקוי הלוח הישן (מסיר קלפים מהמסך וגם מהמערך)
            clearBoardCompletely();

            // יצירת לוח חדש
            createNewBoard();

            // הגדרת קלפי היד החדשים
            setupHandCards();

            // שמירת הלוח החדש ל-Firebase
            saveBoardToFirebase();

            // עדכון מספר הסיבוב ב-Firebase
            gameRef.child("currentRound").setValue(currentRound);

            // שחקן 1 מתחיל את הסיבוב החדש
            gameRef.child("turn").setValue("player1");

            Log.d("Game", "New round " + currentRound + " started by player1");
        } else {
            // שחקן 2 מחכה לעדכון מ-Firebase
            Log.d("Game", "Player2 waiting for new round from Firebase");
        }
    }

    private void resetRoundData() {
        // איפוס השחקן האחרון שלקח קלף
        lastPlayerToTakeCard = null;

        // איפוס הקלפים שנאספו
        player1RoundCards.clear();
        player2RoundCards.clear();

        // איפוס משתני בחירה
        handClickedButton = null;
        secondClickedButton = null;
        thirdClickedButton = null;

        // איפוס משתני אנימציה
        pendingAnimations = 0;
        shouldRefillHand = false;

        // ניקוי הקלפים ביד מיד ללא אנימציה
        clearHandCards();

        Log.d("Game", "Round data reset completed");
    }

    private void clearHandCards() {
        // הסתרת קלפי היד מיד ללא אנימציה
        imageButton1.setVisibility(View.INVISIBLE);
        imageButton1.setTag(null);

        imageButton2.setVisibility(View.INVISIBLE);
        imageButton2.setTag(null);

        imageButton3.setVisibility(View.INVISIBLE);
        imageButton3.setTag(null);

        // איפוס מערך הקלפים ביד
        onTheHand[0] = -1;
        onTheHand[1] = -1;
        onTheHand[2] = -1;

        Log.d("Game", "Hand cards cleared immediately");
    }


    private void refillDeck() {
        // מילוי הקופה מחדש עם כל הקלפים
        arr = new Card[]{
                card3H, card4H, card5H, card6H, card7H, card8H, card9H, card10H, card_a_D,
                card2D, card3D, card4D, card5D, card6D, card7D, card8D, card9D, card10D,
                card_a_S, card2S, card3S, card4S, card5S, card6S, card7S, card8S, card9S, card10S,
                card_a_C, card2C, card3C, card4C, card5C, card6C, card7C, card8C, card9C, card10C
        };

        Log.d("Game", "Deck refilled with " + arr.length + " cards");
    }

    private void shuffleDeck() {
        // ערבוב הקלפים
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Card temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
        Log.d("Game", "Deck shuffled");
    }

    private void clearBoardCompletely() {
        // ניקוי המערך של הלוח
        for (int i = 0; i < OnTheBord.length; i++) {
            OnTheBord[i] = null;
        }

        // הסתרת כל כפתורי הלוח במסך מיד ללא אנימציה
        imageButton4.setVisibility(View.INVISIBLE);
        imageButton4.setTag(null);

        imageButton5.setVisibility(View.INVISIBLE);
        imageButton5.setTag(null);

        imageButton6.setVisibility(View.INVISIBLE);
        imageButton6.setTag(null);

        imageButton7.setVisibility(View.INVISIBLE);
        imageButton7.setTag(null);

        imageButton8.setVisibility(View.INVISIBLE);
        imageButton8.setTag(null);

        imageButton9.setVisibility(View.INVISIBLE);
        imageButton9.setTag(null);

        imageButton10.setVisibility(View.INVISIBLE);
        imageButton10.setTag(null);

        imageButton11.setVisibility(View.INVISIBLE);
        imageButton11.setTag(null);

        Log.d("Game", "Board cleared completely and immediately");
    }


    private void createNewBoard() {
        // הצבת 4 קלפים חדשים על הלוח
        if (indexOfNextCard + 3 < arr.length) {
            // קלף ראשון
            imageButton4.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton4.setTag(arr[indexOfNextCard]);
            imageButton4.setVisibility(View.VISIBLE);
            OnTheBord[0] = arr[indexOfNextCard];
            arr[indexOfNextCard].SetIndex(0);
            indexOfNextCard++;

            // קלף שני
            imageButton5.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton5.setTag(arr[indexOfNextCard]);
            imageButton5.setVisibility(View.VISIBLE);
            OnTheBord[1] = arr[indexOfNextCard];
            arr[indexOfNextCard].SetIndex(1);
            indexOfNextCard++;

            // קלף שלישי
            imageButton6.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton6.setTag(arr[indexOfNextCard]);
            imageButton6.setVisibility(View.VISIBLE);
            OnTheBord[2] = arr[indexOfNextCard];
            arr[indexOfNextCard].SetIndex(2);
            indexOfNextCard++;

            // קלף רביעי
            imageButton7.setImageResource(arr[indexOfNextCard].getImageResource());
            imageButton7.setTag(arr[indexOfNextCard]);
            imageButton7.setVisibility(View.VISIBLE);
            OnTheBord[3] = arr[indexOfNextCard];
            arr[indexOfNextCard].SetIndex(3);
            indexOfNextCard++;

            Log.d("Game", "New board created with 4 cards, next index: " + indexOfNextCard);
        } else {
            Log.e("Game", "Not enough cards to create new board!");
        }
    }





    // פונקציה שמחזירה את הכפתור המתאים בלוח לפי אינדקס
    private ImageButton getBoardButton(int index) {
        switch (index) {
            case 0: return imageButton4;
            case 1: return imageButton5;
            case 2: return imageButton6;
            case 3: return imageButton7;
            case 4: return imageButton8;
            case 5: return imageButton9;
            case 6: return imageButton10;
            case 7: return imageButton11;
            default: return null;
        }
    }

}