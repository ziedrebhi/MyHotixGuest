package com.hotix.myhotixguest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.hotix.myhotixguest.R;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public final String NAMESPACE = "http://tempuri.org/";
    public final String SOAP_ACTION = "http://tempuri.org/Authentifier";
    public final String METHOD_NAME = "Authentifier";
    Boolean connected = false;
    EditText login, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        Button btn = (Button) findViewById(R.id.connect);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.motdp);

        MaterialDialog.Builder msgConnecting = new MaterialDialog.Builder(LoginActivity.this);
        msgConnecting.content("Loading. Please wait...")
                .progress(true, 0)
                .cancelable(true)
                .typeface("Roboto-Light.ttf", "Roboto.ttf")
                .theme(Theme.LIGHT)
                .progressIndeterminateStyle(false)
                .autoDismiss(false)
                .build();

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });
    }

}

