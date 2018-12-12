package com.example.android.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {


    private Thread thread;

    private int matches = 0;

    private MediaPlayer mediaPlayer;
    //private int gameMusicID;
    //private int gameMusicStream;

    private int matchSoundID;

    private int difficulty;

    private int score = 0;

    private boolean isSecondSingleClick = false;

    public ImageView iv;
    public ImageView iv1;
    public ImageView iv2;
    private ImageView gameBoard[][];

    private SoundPool soundPool;

    private TextView tvScore;

    private final String SETTINGS_KEY = "settings";
    private final String DIFFICULTY_KEY = "difficulty";

    private final int COLOR_TAG = R.string.color_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(SETTINGS_KEY, 0);
        difficulty = settings.getInt(DIFFICULTY_KEY, 0);

        setContentView(R.layout.activity_game);

        tvScore = findViewById(R.id.tv_game_score);

        //Initialize and begin playing music
        mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //Initialize sound effects
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        matchSoundID = soundPool.load(this, R.raw.correct, 1);


        thread = new Thread(matchThread);

        switch (difficulty) {
            case 0:
                buildGameBoard(2);
                break;
            case 1:
                buildGameBoard(4);
                break;
            case 2:
                buildGameBoard(6);
                break;
        }

    }

    private void buildGameBoard(int boardSize) {
        int[] colorCount;

        Random rng;

        rng = new Random();

        colorCount = new int[18];
        //colorCount = new int[(boardSize * boardSize) / 2];
        gameBoard = new ImageView[6][6];
        //gameBoard = new ImageView[boardSize][boardSize];

        //Loop to generate board
        for (int x = 0; x < 6; x++) {

            //Sets color for each square in a row
            for (int y = 0; y < 6; y++) {

                int currColor;
                int id;

                String imageViewId;

                //Gets corresponding ImageView
                imageViewId = "iv_square_" + x + y;

                //Looks up ID for ImageView
                id = getResources().getIdentifier(imageViewId, "id", getPackageName());
                gameBoard[x][y] = findViewById(id);

                if (x >= boardSize || y >= boardSize) {
                    gameBoard[x][y].setVisibility(View.GONE);
                } else {
                    //Loops through colors until it finds one that has not been used twice
                    do {
                        //Randomly chooses color from array to make square
                        currColor = rng.nextInt((boardSize * boardSize) / 2);
                        //currColor = rng.nextInt((boardSize * boardSize) / 2);

                    } while (colorCount[currColor] >= 2);

                    //When color is chosen, increment count
                    colorCount[currColor]++;

                    //Assigns color to square
                    gameBoard[x][y].setTag(COLOR_TAG, currColor);
                }
            }
        }

        //layout.setColumnCount(boardSize);
    }


    public void onSquareClick(View view) {

        //Typecasts view to ImageView so we can use setImageResource
        iv = (ImageView) view;

        switch ((int) view.getTag(COLOR_TAG)) {
            case 0:
                iv.setImageResource(R.drawable.ic_aqua_square);
                break;
            case 1:
                iv.setImageResource(R.drawable.ic_bronze_square);
                break;
            case 2:
                iv.setImageResource(R.drawable.ic_brown_square);
                break;
            case 3:
                iv.setImageResource(R.drawable.ic_chocloate_square);
                break;
            case 4:
                iv.setImageResource(R.drawable.ic_emerald_square);
                break;
            case 5:
                iv.setImageResource(R.drawable.ic_ice_square);
                break;
            case 6:
                iv.setImageResource(R.drawable.ic_magenta_square);
                break;
            case 7:
                iv.setImageResource(R.drawable.ic_mango_square);
                break;
            case 8:
                iv.setImageResource(R.drawable.ic_mint_square);
                break;
            case 9:
                iv.setImageResource(R.drawable.ic_neon_pink_square);
                break;
            case 10:
                iv.setImageResource(R.drawable.ic_orange_square);
                break;
            case 11:
                iv.setImageResource(R.drawable.ic_pea_green_square);
                break;
            case 12:
                iv.setImageResource(R.drawable.ic_peach_square);
                break;
            case 13:
                iv.setImageResource(R.drawable.ic_pink_square);
                break;
            case 14:
                iv.setImageResource(R.drawable.ic_red_square);
                break;
            case 15:
                iv.setImageResource(R.drawable.ic_teal_square);
                break;
            case 16:
                iv.setImageResource(R.drawable.ic_white_square);
                break;
            case 17:
                iv.setImageResource(R.drawable.ic_yellow_square);
                break;
        }

        if (!isSecondSingleClick) {
            iv1 = iv;
            isSecondSingleClick = true;
        } else {
            iv2 = iv;
            if (iv1 != iv2) {
                isSecondSingleClick = false;
                thread.run();
            }

        }


    }

    private Runnable matchThread = new Runnable() {
        @Override
        public void run() {
            try {

                Thread.sleep(10);

                Message msg = matchHandler.obtainMessage();
                Bundle bundle = new Bundle();

                if ((int) iv1.getTag(COLOR_TAG) == (int) iv2.getTag(COLOR_TAG)) {

                    bundle.putString("Decision", "correct");
                    msg.setData(bundle);
                    matchHandler.sendMessage(msg);

                } else {

                    bundle.putString("Decision", "incorrect");
                    msg.setData(bundle);
                    matchHandler.sendMessage(msg);

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };

    public Handler matchHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            String decision = bundle.getString("Decision");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }

            if (decision.equals("correct")) {
                soundPool.play(matchSoundID, 1.0f, 1.0f, 0, 0, 1.0f);

                iv2.setVisibility(View.GONE);
                iv1.setVisibility(View.GONE);
                matches++;

                switch (difficulty){
                    case 0:
                        score += 100;
                        if(matches >= 2){
                            activateVictory();
                        }
                        break;
                    case 1:
                        score += 200;
                        if(matches >= 8){
                            activateVictory();
                        }
                        break;
                    case 2:
                        score += 300;
                        if(matches >= 18){
                            activateVictory();
                        }
                        break;
                }

                tvScore.setText("Score: " + score);

            } else {

                iv1.setImageResource(R.drawable.ic_gray_square);
                System.out.println("Set iv1 to gray");

                iv2.setImageResource(R.drawable.ic_gray_square);
                System.out.println("SET IV2 to GRAY");
            }
        }
    };


    private void activateVictory(){
        mediaPlayer.stop();
        Intent launchVictory = new Intent(this, VictoryActivity.class);
        launchVictory.putExtra("score", score);
        startActivity(launchVictory);
    }
}
