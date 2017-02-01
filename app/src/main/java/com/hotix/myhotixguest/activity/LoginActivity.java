package com.hotix.myhotixguest.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.entities.LoginModel;
import com.hotix.myhotixguest.entities.UserInfoModel;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText login, password;
    MaterialDialog.Builder msgConnecting;
    MaterialDialog dialog;

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

        ShowDialogMaterial(true);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main);
                finish();
                */
                new HttpRequestTask().execute();

            }
        });
    }


    public String getURL() {
        return "http://41.228.14.111/HNGAPI/api/myhotixguest/Authentifier";
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void ShowDialogMaterial(boolean isOk) {
        msgConnecting = new MaterialDialog.Builder(LoginActivity.this);
        if (isOk) {
            msgConnecting.content(getResources().getString(R.string.laoding))
                    .progress(true, 0)
                    .cancelable(true)
                    .typeface("Roboto-Light.ttf", "Roboto.ttf")
                    .theme(Theme.LIGHT)
                    .progressIndeterminateStyle(false)
                    .autoDismiss(false);
            dialog = msgConnecting.build();
        } else {
            msgConnecting.content(getResources().getString(R.string.laoding_error))
                    .typeface("Roboto-Light.ttf", "Roboto.ttf")
                    .theme(Theme.LIGHT)
                    .positiveText("Ok");
            dialog = msgConnecting.build();
            dialog.show();
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, LoginModel> {
        LoginModel isConnected = null;
        String chambre = "";
        String identite = "";

        @Override
        protected LoginModel doInBackground(Void... params) {
            try {
                final String url = getURL() + "?chambre=" + chambre + "&identite=" + identite;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                isConnected = restTemplate.getForObject(url, LoginModel.class);
                Log.i("HttpRequestTask", isConnected.toString());
                return isConnected;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(LoginModel greeting) {
            if (isConnected.getStatus()) {
                dialog.dismiss();
                UserInfoModel.getInstance().setRoom(chambre);
                UserInfoModel.getInstance().setName(isConnected.getData());
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            } else {
                dialog.dismiss();
                ShowDialogMaterial(false);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            chambre = login.getText().toString();
            identite = password.getText().toString();
            dialog.show();
        }
    }
}

