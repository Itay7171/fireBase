package com.example.afinal;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
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

public class Game extends AppCompatActivity {



    ImageButton imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6,imageButton7,imageButton8,imageButton9,imageButton10,imageButton11 ;
    boolean isBorderAdded = false;
    private ImageButton handClickedButton = null;
    private ImageButton secondClickedButton = null;
    private ImageButton selectedCardButton = null;
    private ImageButton thirdClickedButton = null; // הוספת המשתנה החסר
    private DatabaseReference gameRef;
    Wifi_Reciver WifiModeChangeReciver = new Wifi_Reciver();


    private Button throwButton;
    LinkedList<Card> player1RoundCards = new LinkedList<>();
    LinkedList<Card> player2 = new LinkedList<Card>();

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


    int indexOfNextCard = 0;
    Card[] arr = {card_a_H,card2H,card3H,card4H,card5H,card6H,card7H,card8H,card9H,card10H,card_a_D,card2D,card3D,card4D,card5D,card6D,card7D,card8D,card9D,card10D,card_a_S,card2S,card3S,card4S,card5S,card6S,card7S,card8S,card9S,card10S};
    Random rnd = new Random();

    Card[] use_card = new Card[9];
    Card[] OnTheBord = new Card[8];
    Random random = new Random();
    int[] onTheHand = new int[3];

    private TextView timerText;
    private CountDownTimer countDownTimer;
    private int currentPlayer = 1; // נניח שחקן 1 מתחיל

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

        startTurnTimer();

        DatabaseReference gameRef = FirebaseDatabase.getInstance().getReference("games").child("game123");
        gameRef.child("turn").setValue("player1");  // שחקן 1 מתחיל


//        gameRef.child("turn").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String currentTurn = snapshot.getValue(String.class);
//                if (currentTurn == null) return;
//
//                if (currentTurn.equals(myPlayerId)) {
//                    startGame();  // תורך לשחק
//                } else {
//                    disablePlayerControls(); // תחכה
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) { }
//        });




        //Card clickedCard = (Card) imageButton4.getTag();
//        imageButton1.setOnClickListener(new View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v) {
//
//                for(int i=0;i< OnTheBord.length;i++)
//                {
//                    if(OnTheBord[i].getNumber()==OnTheBord[onTheHand[0]].getNumber())
//                    {
//
//                        Animation moveAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_card);
//                        imageButton1.startAnimation(moveAnimation);
//
//                        // כאשר האנימציה מסתיימת, הסתר את הכרטיס
//                        moveAnimation.setAnimationListener(new Animation.AnimationListener() {
//                            @Override
//                            public void onAnimationStart(Animation animation) {}
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
//                                imageButton1.setVisibility(View.INVISIBLE);
//
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) {}
//                        });
//
//                    }
//
//                }
//                for(int i=0;i< OnTheBord.length;i++)
//                {
//                    for (int j = i + 1; j < OnTheBord.length; j++)
//                    {
//                        if (OnTheBord[i].getNumber() + OnTheBord[j].getNumber() ==arr[onTheHand[0]].getNumber() )
//                        {
//                            Animation moveAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_card);
//                            imageButton1.startAnimation(moveAnimation);
//
//                            // כאשר האנימציה מסתיימת, הסתר את הכרטיס
//                            moveAnimation.setAnimationListener(new Animation.AnimationListener() {
//                                @Override
//                                public void onAnimationStart(Animation animation) {}
//
//                                @Override
//                                public void onAnimationEnd(Animation animation) {
//                                    imageButton1.setVisibility(View.INVISIBLE);
//                                }
//
//                                @Override
//                                public void onAnimationRepeat(Animation animation) {}
//                            });
//                        }
//                    }
//                }
//
//
//            }
//
//        });
//
//
//
//
//
//        imageButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int i=0;i< OnTheBord.length;i++)
//                {
//                    if(OnTheBord[i].getNumber()==arr[onTheHand[0]].getNumber())
//                    {
//                        imageButton2.setVisibility(View.INVISIBLE);
//                    }
//
//                }
//                for(int i=0;i< OnTheBord.length;i++)
//                {
//                    for (int j = i + 1; j < OnTheBord.length; j++)
//                    {
//                        if (OnTheBord[i].getNumber() + OnTheBord[j].getNumber() ==arr[onTheHand[0]].getNumber() )
//                        {
//                            imageButton2.setVisibility(View.INVISIBLE);
//                        }
//                    }
//                }
//
//
//            }
//        });
//
//        imageButton3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int i=0;i< OnTheBord.length;i++)
//                {
//                    if(OnTheBord[i].getNumber()==arr[onTheHand[0]].getNumber())
//                    {
//                        imageButton3.setVisibility(View.INVISIBLE);
//                    }
//
//                }
//                for(int i=0;i< OnTheBord.length;i++)
//                {
//                    for (int j = i + 1; j < OnTheBord.length; j++)
//                    {
//                        if (OnTheBord[i].getNumber() + OnTheBord[j].getNumber() ==arr[onTheHand[0]].getNumber() )
//                        {
//                            imageButton3.setVisibility(View.INVISIBLE);
//                        }
//                    }
//                }
//
//
//            }
//        });



//        setCardClickListener(imageButton1);
//        setCardClickListener(imageButton2);
//        setCardClickListener(imageButton3);
//        setCardClickListener(imageButton4);
//        setCardClickListener(imageButton5);
//        setCardClickListener(imageButton6);
//        setCardClickListener(imageButton7);
//        setCardClickListener(imageButton8);
//        setCardClickListener(imageButton9);
//        setCardClickListener(imageButton10);
//        setCardClickListener(imageButton11);
//
//
//
//// הוספת מאזין לכל קלף ביד
//        setCardClickListenerForHand(imageButton1);
//        setCardClickListenerForHand(imageButton2);
//        setCardClickListenerForHand(imageButton3);







    }

    public void startGame(){



        // Shuffles the deck
        for (int i = arr.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Card a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }


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

        imageButton8.setVisibility(View.INVISIBLE);



        imageButton9.setVisibility(View.INVISIBLE);


        imageButton10.setVisibility(View.INVISIBLE);


        imageButton11.setVisibility(View.INVISIBLE);



        imageButton1.setImageResource(arr[indexOfNextCard].getImageResource());
        imageButton1.setTag(arr[indexOfNextCard]);
        onTheHand[0] = indexOfNextCard;
        indexOfNextCard++;

        imageButton2.setImageResource(arr[indexOfNextCard].getImageResource());
        imageButton2.setTag(arr[indexOfNextCard]);
        onTheHand[1] = indexOfNextCard;
        indexOfNextCard++;

        imageButton3.setImageResource(arr[indexOfNextCard].getImageResource());
        imageButton3.setTag(arr[indexOfNextCard]);
        onTheHand[2] = indexOfNextCard;
        indexOfNextCard++;


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


        // מאזין לכפתור "לזרוק"
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
        countDownTimer = new CountDownTimer(60000, 1000) { // 60 שניות

            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                timerText.setText(String.valueOf(secondsLeft));
            }

            @Override
            public void onFinish() {
                // כשהטיימר נגמר — מעבירים תור
                Toast.makeText(Game.this, "נגמר הזמן! תור עובר לשחקן הבא", Toast.LENGTH_SHORT).show();
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
        SharedPreferences prefs = getSharedPreferences("MyGamePrefs", MODE_PRIVATE);
        String playerId = prefs.getString("playerId", null);  // ערך ברירת מחדל אם לא קיים
        String nextPlayer = playerId.equals("player1") ? "player2" : "player1";
        gameRef.child("turn").setValue(nextPlayer);
        startTurnListener();///לבדוק אם זה לא יעבוד שם לשים את זה אחרי הקריאה לפעולה
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



        Log.d("Game", "Player controls enabled");

        endTurn();
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

















//    private void setCardClickListener(ImageButton button) {
//        button.setOnClickListener(v -> {
//            // שמור את הכפתור שנלחץ
//            if (firstClickedButton == null) {
//                firstClickedButton = (ImageButton) v;
//            } else if (secondClickedButton == null) {
//                secondClickedButton = (ImageButton) v;
//
//                // כאשר שני קלפים נבחרו, בדוק אם הם זהים
//                checkMatch();
//            }
//        });
//    }




    private void checkMatch() {
        if (handClickedButton != null && secondClickedButton != null) {
            // קבלת הקלפים מהתגיות
            Card handCard = (Card) handClickedButton.getTag();
            Card boardCard1 = (Card) secondClickedButton.getTag();

            if (handCard != null && boardCard1 != null) {
                // בדיקה אם שני הקלפים תואמים
                if (handCard.getNumber() == boardCard1.getNumber()) {
                    // התאמה נמצאה - הסתרת הקלפים
                    player1RoundCards.add(handCard);
                    player1RoundCards.add(boardCard1);
                    handClickedButton.setVisibility(View.INVISIBLE);
                    secondClickedButton.setVisibility(View.INVISIBLE);

                    // הפיכת הקלף שנלקח מהשולחן ל-null
                    for (int i = 0; i < OnTheBord.length; i++) {
                        if (OnTheBord[i] == boardCard1) {
                            OnTheBord[i] = null;
                            break;
                        }
                    }



                    Log.d("Game", "Pair match found: " + handCard.getNumber() + " = " + boardCard1.getNumber());

                    // איפוס המשתנים
                    resetSelection();
                    checkAndRefillHand();
                    return;  // אם נמצא התאמה, יוצאים מהפונקציה
                }
            }
        }

        // אם אין התאמה, ננסה התאמה לשלושה קלפים
        if (handClickedButton != null && secondClickedButton != null && thirdClickedButton != null) {
            checkTripleMatch((Card) handClickedButton.getTag());
        }
    }


    private void checkTripleMatch(Card handCard) {
        if (handCard != null && secondClickedButton != null && thirdClickedButton != null) {
            // קבלת הקלפים מהתגיות
            Card boardCard1 = (Card) secondClickedButton.getTag();
            Card boardCard2 = (Card) thirdClickedButton.getTag();

            if (boardCard1 != null && boardCard2 != null) {
                // בדיקה אם סכום שני הקלפים מהלוח שווה לקלף שביד
                if (boardCard1.getNumber() + boardCard2.getNumber() == handCard.getNumber()) {
                    // התאמה נמצאה - הסתרת הקלפים
                    player1RoundCards.add(handCard);
                    player1RoundCards.add(boardCard1);
                    player1RoundCards.add(boardCard2);
                    handClickedButton.setVisibility(View.INVISIBLE);
                    secondClickedButton.setVisibility(View.INVISIBLE);
                    thirdClickedButton.setVisibility(View.INVISIBLE);

                    // הפיכת הקלף שנלקח מהשולחן ל-null
                    for (int i = 0; i < OnTheBord.length; i++) {
                        if (OnTheBord[i] == boardCard1) {
                            OnTheBord[i] = null;
                            break;
                        }
                    }

                    // הפיכת הקלף שנלקח מהשולחן ל-null
                    for (int i = 0; i < OnTheBord.length; i++) {
                        if (OnTheBord[i] == boardCard2) {
                            OnTheBord[i] = null;
                            break;
                        }
                    }


                    Log.d("Game", "Triple match found: " + boardCard1.getNumber() + " + " + boardCard2.getNumber() + " = " + handCard.getNumber());
                } else {
                    Toast.makeText(this, "No match found!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        checkAndRefillHand();
        // איפוס המשתנים
        resetSelection();
    }

    private void resetSelection() {
        handClickedButton = null;
        secondClickedButton = null;
        thirdClickedButton = null;
    }






//    private void checkTripleMatch() {
//        if (selectedCardButton != null && handClickedButton != null && secondClickedButton != null) {
//            Card handCard = (Card) selectedCardButton.getTag();
//            Card boardCard1 = (Card) handClickedButton.getTag();
//            Card boardCard2 = (Card) secondClickedButton.getTag();
//
//            if (handCard != null && boardCard1 != null && boardCard2 != null) {
//                if (handCard.getNumber() == (boardCard1.getNumber() + boardCard2.getNumber())) {
//                    // התאמה בין שלושה קלפים – מחיקה
//                    selectedCardButton.setVisibility(View.INVISIBLE);
//                    handClickedButton.setVisibility(View.INVISIBLE);
//                    secondClickedButton.setVisibility(View.INVISIBLE);
//
//                    Log.d("Game", "Triple match found and removed!");
//                    }
//
//                else{
//                        Toast.makeText(this, "The cards don't match", Toast.LENGTH_SHORT).show();
//                        // קלפים לא תואמים – ניתן להוסיף אפקט או אנימציה
//                        // איפוס המשתנים
//
//                }
//
//              selectedCardButton = null;
//              handClickedButton = null;
//              secondClickedButton = null;
//            }
//        }
//    }






//    private void setCardClickListenerForHand(ImageButton button) {
//        button.setOnClickListener(v -> {
//            selectedCardButton = (ImageButton) v;
//            Log.d("Game", "Selected card from hand: " + selectedCardButton.getTag());
//        });
//    }


    private void setthrowButtonClickListener() {
        throwButton.setOnClickListener(v -> {
            if (handClickedButton == null) {
                Log.e("Game", "No card selected from hand!");
                return;
            }

            Card selectedCard = (Card) handClickedButton.getTag();
            if (selectedCard == null) {
                Log.e("Game", "Selected card is null!");
                return;
            }

            // מציאת מקום פנוי בלוח
            for (int i = 0; i < OnTheBord.length; i++) {
                if (OnTheBord[i] == null) {

                    // ניקוי הקלף מהיד
                    handClickedButton.setVisibility(View.INVISIBLE); // מחיקת הקלף מהיד
                    handClickedButton.setTag(null);
                    handClickedButton = null; // איפוס המשתנה

                    // עדכון הכפתור המתאים בלוח
                    ImageButton boardButton = getBoardButton(i);
                    boardButton.setImageResource(selectedCard.getImageResource());
                    boardButton.setTag(selectedCard);
                    boardButton.setVisibility(View.VISIBLE);

                    // שמירה במערך
                    OnTheBord[i] = selectedCard;



                    Log.d("Game", "Card placed on board at position: " + i);
                    checkAndRefillHand();
                    return;
                }

            }

            Log.e("Game", "No empty slot on the board!");
        });
    }





    private void checkAndRefillHand() {
        boolean allInvisible = imageButton1.getVisibility() == View.INVISIBLE &&
                imageButton2.getVisibility() == View.INVISIBLE &&
                imageButton3.getVisibility() == View.INVISIBLE;

        if (allInvisible) {

            if (arr.length == 0) {
                analyzePlayer1Round(player1RoundCards);
            }
            else
            {
                Log.d("Game", "All hand cards are used. Refilling...");
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
            }

        }
    }

    private void analyzePlayer1Round(LinkedList<Card> roundCards)
    {
        int sevensCount1 = 0;
        int diamondsCount1 = 0;
        int totalCards1 = roundCards.size();
        int score1 = 0;

        for (Card card : roundCards)
        {
            if (card.getNumber() == 7)
            {
                sevensCount1++;
            }
            if (Objects.equals(card.getShape(), "D"))
            {
                diamondsCount1++;
            }
        }

        score1 = sevensCount1 + diamondsCount1;

        TextView textView5 = findViewById(R.id.textView5);
        textView5.setText(score1);



        Log.d("RoundStats", "Player 1 collected " + totalCards1 + " cards.");
        Log.d("RoundStats", "Number of 7s: " + sevensCount1);
        Log.d("RoundStats", "Number of Diamonds: " + diamondsCount1);


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







    private GradientDrawable createOutline(String color, int strokeWidth) {
        GradientDrawable outline = new GradientDrawable();
        outline.setColor(0x00000000); // Transparent background
        outline.setStroke(strokeWidth, android.graphics.Color.parseColor(color)); // Outline color and width
        outline.setCornerRadius(8f); // Optional: Rounded corners
        return outline;
    }
}