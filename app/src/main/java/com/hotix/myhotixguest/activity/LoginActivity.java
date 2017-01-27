package com.hotix.myhotixguest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.hotix.myhotixguest.R;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn = (Button) findViewById(R.id.connect);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //   ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "", "Loading. Please wait...", true);
                new MaterialDialog.Builder(LoginActivity.this)
                        .content("Loading. Please wait...")
                        .progress(true, 0)
                        .cancelable(true)
                        .typeface("Roboto.ttf", "Roboto-Light.ttf")
                        .theme(Theme.LIGHT)
                        .progressIndeterminateStyle(false)
                        .show();
            }
        });
    }

}

