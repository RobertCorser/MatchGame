package com.example.android.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences settings;
    private SharedPreferences scoreSheet;
    private Spinner difficulty;
    private final String SETTINGS_KEY = "settings";
    private final String DIFFICULTY_KEY = "difficulty";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        difficulty = findViewById(R.id.spinner_difficulty);

        settings = getSharedPreferences(SETTINGS_KEY, 0);
        scoreSheet = getSharedPreferences("scoreSheet",0);

        difficulty.setSelection(settings.getInt(DIFFICULTY_KEY, 0));

    }

    public void onBtnSettingsClicked(View view){

        switch (view.getId()){
            case R.id.btn_settings_reset_score_list:
                resetScoreList();
                break;
            case R.id.btn_settings_save:
                saveSettings();
                break;
            case R.id.btn_settings_back:
                activateMainMenu();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.overridePendingTransition(R.anim.anim_rightleft_enter, R.anim.anim_rightleft_exit);
    }

    private void saveSettings(){

        SharedPreferences.Editor editor;

        editor = settings.edit();
        editor.putInt(DIFFICULTY_KEY, difficulty.getSelectedItemPosition());
        editor.commit();

        Toast.makeText(this, "Saved settings!", Toast.LENGTH_LONG).show();
    }

    private void resetScoreList(){

    }

    private void activateMainMenu(){
        Intent launchMainMenu;

        launchMainMenu = new Intent(this, MainActivity.class);
        startActivity(launchMainMenu);
    }

    public void onBtnSubmitClick(View view){

        new ConfirmScoreResetDialogFragment().show(getSupportFragmentManager(), null);

    }

    public void onConfirmSubmitClick(View view){

        SharedPreferences.Editor editor;

        editor = scoreSheet.edit();
        editor.putInt("highScore", 0);
        editor.commit();
    }


}
