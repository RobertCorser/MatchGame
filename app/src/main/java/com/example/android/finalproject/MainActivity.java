package com.example.android.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.overridePendingTransition(R.anim.anim_leftright_enter, R.anim.anim_leftright_exit);
    }

    public void onBtnMainMenuClicked(View view){

        switch (view.getId()){
            case R.id.btn_main_start_game:
                activateGame();
                break;
            case R.id.btn_main_settings:
                activateSettings();
                break;
            case R.id.btn_main_high_score:
                activateHighScore();
                break;
        }
    }

    private void activateGame(){
        Intent launchGame;

        launchGame = new Intent(this, GameActivity.class);
        startActivity(launchGame);

    }

    private void activateSettings(){
        Intent launchSettings;

        launchSettings = new Intent(this, SettingsActivity.class);
        startActivity(launchSettings);
    }

    private void activateHighScore(){
        Intent launchHighScore;

        launchHighScore = new Intent(this, ScoreActivity.class);
        startActivity(launchHighScore);
    }
}
