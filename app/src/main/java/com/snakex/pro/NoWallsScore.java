package com.snakex.pro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class NoWallsScore extends AppCompatActivity {

    private TextView scoreTextView;
    private TextView highScoreTextView;
    private ImageView playAgainImageView;
    private ImageView mainMenuImageView;

    private TextView gameOverTitleLeftTextView;
    private TextView gameOverTitleRightTextView;
    private TextView gameOverTitleMiddleTextView;

    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_no_walls_score);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }


        initTitle();
        initScore();
        initHighScore();
        initPlayAgain();
        initMainMenu();

    }

    private void initScore(){
        scoreTextView = (TextView) findViewById(R.id.player_score);
        animation = AnimationUtils.loadAnimation(this,R.anim.anim_for_classic_button);
        animation.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
                int playersScore = preferences.getInt(GameSettings.PLAYER_SCORE,0);
                scoreTextView.setText("Score: " + String.valueOf(playersScore));
                scoreTextView.setTextColor(Color.WHITE);
                scoreTextView.setGravity(Gravity.CENTER);
                scoreTextView.setBackgroundResource(R.mipmap.menu_options);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        scoreTextView.startAnimation(animation);
    }

    private void initHighScore(){
        highScoreTextView = (TextView) findViewById(R.id.mode_high_score);
        animation = AnimationUtils.loadAnimation(this,R.anim.anim_for_no_button);
        animation.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setHighScore();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        highScoreTextView.startAnimation(animation);
    }

    private void setHighScore(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int highScore = preferences.getInt(GameSettings.HIGH_SCORE_NOWALLS,0);
        int lastScore = preferences.getInt(GameSettings.PLAYER_SCORE,0);
        if(lastScore>highScore){
            editor.putInt(GameSettings.HIGH_SCORE_NOWALLS,lastScore);
            editor.commit();
            highScore = lastScore;
        }

        highScoreTextView.setText("High: " + String.valueOf(highScore));
        highScoreTextView.setTextColor(Color.WHITE);
        highScoreTextView.setGravity(Gravity.CENTER);
        highScoreTextView.setBackgroundResource(R.mipmap.menu_options);
    }

    private void initPlayAgain(){
        playAgainImageView = (ImageView) findViewById(R.id.play_again);
        animation = AnimationUtils.loadAnimation(this,R.anim.anim_for_settings_button);
        animation.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                playAgainImageView.setImageResource(R.drawable.replay);
                playAgainImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentClassic = new Intent(NoWallsScore.this,NoWallsSnake.class);
                        intentClassic.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intentClassic);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        playAgainImageView.startAnimation(animation);
    }

    private void initMainMenu(){
        mainMenuImageView = (ImageView) findViewById(R.id.goto_main_menu);
        animation = AnimationUtils.loadAnimation(NoWallsScore.this,R.anim.anim_for_bomb_button);
        animation.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mainMenuImageView.setImageResource(R.drawable.menu);
                mainMenuImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        scoreTextView.setBackgroundResource(R.mipmap.menu_options);
                        scoreTextView.setText("");
                        scoreTextView.setTextColor(Color.BLACK);
                        highScoreTextView.setBackgroundResource(R.mipmap.menu_options);
                        highScoreTextView.setText("");
                        highScoreTextView.setTextColor(Color.BLACK);
                        playAgainImageView.setBackgroundResource(R.mipmap.menu_options);
                        mainMenuImageView.setBackgroundResource(R.mipmap.menu_options);

                        //reverse animation
                        Animation animationScore = AnimationUtils.loadAnimation(NoWallsScore.this,R.anim.reverse_for_classic_button);
                        animationScore.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);

                        Animation animationHighScore = AnimationUtils.loadAnimation(NoWallsScore.this,R.anim.reverse_for_no_button);
                        animationHighScore.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);

                        Animation animationPlayAgain = AnimationUtils.loadAnimation(NoWallsScore.this,R.anim.reverse_for_settings_button);
                        animationPlayAgain.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);

                        Animation animationMainMenu = AnimationUtils.loadAnimation(NoWallsScore.this,R.anim.reverse_for_bomb_button);
                        animationMainMenu.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);

                        //title animations
                        Animation animationTitleLeft = AnimationUtils.loadAnimation(NoWallsScore.this,R.anim.anim_for_title_left);
                        animationTitleLeft.setDuration(GameSettings.ANIMATION_SHOW_TITLE_DURATION);

                        Animation animationTitleRight = AnimationUtils.loadAnimation(NoWallsScore.this,R.anim.anim_for_title_right);
                        animationTitleRight.setDuration(GameSettings.ANIMATION_SHOW_TITLE_DURATION);

                        Animation animationTitleMiddle = AnimationUtils.loadAnimation(NoWallsScore.this,R.anim.anim_for_title_middle);
                        animationTitleMiddle.setDuration(GameSettings.ANIMATION_SHOW_TITLE_DURATION);



                        scoreTextView.startAnimation(animationScore);
                        highScoreTextView.startAnimation(animationHighScore);
                        playAgainImageView.startAnimation(animationPlayAgain);
                        mainMenuImageView.startAnimation(animationMainMenu);
                        gameOverTitleLeftTextView.startAnimation(animationTitleLeft);
                        gameOverTitleRightTextView.startAnimation(animationTitleRight);
                        gameOverTitleMiddleTextView.startAnimation(animationTitleMiddle);

                        Handler myHandler  = new Handler();
                        myHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intentMain = new Intent(NoWallsScore.this, MainMenuActivity.class);
                                intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentMain);
                            }
                        },GameSettings.START_NEW_ACTIVITY_DURATION);

                    }
                });


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mainMenuImageView.startAnimation(animation);
    }

    private void initTitle(){
        gameOverTitleLeftTextView = (TextView) findViewById(R.id.gameover_left);
        gameOverTitleRightTextView = (TextView) findViewById(R.id.gameover_right);
        gameOverTitleMiddleTextView = (TextView) findViewById(R.id.gameover_middle);

        Animation animationTitleLeft = AnimationUtils.loadAnimation(NoWallsScore.this,R.anim.back_anim_for_title_left);
        animationTitleLeft.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);
        animationTitleLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        gameOverTitleLeftTextView.startAnimation(animationTitleLeft);

        Animation animationTitleRight = AnimationUtils.loadAnimation(NoWallsScore.this,R.anim.back_anim_for_title_right);
        animationTitleRight.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);
        animationTitleRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        gameOverTitleRightTextView.startAnimation(animationTitleRight);

        Animation animationTitleMiddle = AnimationUtils.loadAnimation(NoWallsScore.this,R.anim.back_anim_for_title_middle);
        animationTitleMiddle.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);
        animationTitleMiddle.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        gameOverTitleMiddleTextView.startAnimation(animationTitleMiddle);
    }

    @Override
    public void onBackPressed() {
    }












}
