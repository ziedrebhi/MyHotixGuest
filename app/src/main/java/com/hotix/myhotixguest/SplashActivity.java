package com.hotix.myhotixguest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends Activity {
    protected Animation fadeIn;
    protected ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        img1 = (ImageView) findViewById(R.id.imageView);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);
        img1.setVisibility(View.VISIBLE);

        Thread loading = new Thread() {
            public void run() {
                try {
                    img1.startAnimation(fadeIn);
                    sleep(5000);
                   /* Intent main = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(main);
                    finish();*/
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
