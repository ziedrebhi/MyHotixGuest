package com.hotix.myhotixguest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hotix.myhotixguest.R;

public class SplashScreen extends AppCompatActivity {
    protected Animation fadeIn;
    protected ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        logo = (ImageView) findViewById(R.id.logoSplash);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);
        logo.setVisibility(View.VISIBLE);

        Thread loading = new Thread() {
            public void run() {
                try {
                    logo.startAnimation(fadeIn);
                    sleep(3000);
                    Intent main = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(main);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finish();

                }
            }
        };

        loading.start();
    }


}
