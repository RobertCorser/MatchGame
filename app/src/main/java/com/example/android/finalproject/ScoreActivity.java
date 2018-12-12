package com.example.android.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private TextView currHighScore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int highScore = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_check);

        SharedPreferences scoreSheet = getSharedPreferences("scoreSheet", 0);
        highScore = scoreSheet.getInt("highScore", 0);

        currHighScore = findViewById(R.id.tv_curr_high_score);
        currHighScore.setText(String.valueOf(highScore));
    }

    public void onBtnMainMenuClickedScore(View view){
        Intent backToMainMenu = new Intent(this, MainActivity.class);
        startActivity(backToMainMenu);
    }
}
