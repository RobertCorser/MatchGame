package com.example.android.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class VictoryActivity extends AppCompatActivity {

    SoundPool soundPool;

    private int highScore;
    private int score;

    private Intent fromGameActivity;

    private MediaPlayer mediaPlayer;

    private TextView tvScore;
    private TextView isHighScore;

    private final String SCORE_SHEET_KEY = "scoreSheet";
    private final String HIGH_SCORE_KEY = "highScore";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        SharedPreferences scoreSheet = getSharedPreferences(SCORE_SHEET_KEY, 0);

        mediaPlayer = MediaPlayer.create(this, R.raw.winning);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        isHighScore = findViewById(R.id.is_high_score);
        tvScore = findViewById(R.id.tv_victory_score);

        fromGameActivity = getIntent();
        score = fromGameActivity.getIntExtra("score", 0);
        tvScore.setText(String.valueOf(score));

        highScore = scoreSheet.getInt(HIGH_SCORE_KEY, 0);
        if(highScore >= score){
            isHighScore.setVisibility(View.GONE);
        }else {
            SharedPreferences.Editor editor;

            editor = scoreSheet.edit();
            editor.putInt(HIGH_SCORE_KEY, score);
            editor.commit();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        this.overridePendingTransition(R.anim.anim_leftright_enter, R.anim.anim_leftright_exit);
    }

    public void onBtnBackToMainMenu(View view){
        Intent backToMainMenu = new Intent(this, MainActivity.class);
        startActivity(backToMainMenu);
    }

}
