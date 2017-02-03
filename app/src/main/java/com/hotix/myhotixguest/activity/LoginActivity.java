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
                if (isConnected()) {
                    Log.i("HotixDev", "  Connected");
                    if (isEmpty()) {
                        Log.i("HotixDev", "Not empty");
                        new HttpRequestTask().execute();
                    }
                } else {
                    Log.i("HotixDev", "  Disconnected");
                    ShowDialogMaterialConnection();
                }
            }
        });
    }


    public String getURL() {
        return UserInfoModel.getInstance().getURL() + "Authentifier";
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

    public boolean isEmpty() {

        if ((login.getText().toString().isEmpty()) && (password.getText().toString().isEmpty())) {
            login.setError("Obligatoire");
            password.setError("Obligatoire");
            Log.i("HotixDev", "Both Empty");
            return false;
        } else if (password.getText().toString().isEmpty()) {
            password.setError("Obligatoire");
            Log.i("HotixDev", "password Empty");
            return false;
        } else if (login.getText().toString().isEmpty()) {
            Log.i("HotixDev", "login Empty");
            login.setError("Obligatoire");
            return false;
        }
        return true;
    }

    public void ShowDialogMaterial(boolean isOk) {
        msgConnecting = new MaterialDialog.Builder(LoginActivity.this);
        if (isOk) {
            msgConnecting.content(getResources().getString(R.string.laoding))
                    .progress(true, 0)
                    .cancelable(false)
                    .typeface("Roboto-Light.ttf", "Roboto.ttf")
                    .theme(Theme.LIGHT)
                    .progressIndeterminateStyle(false)
                    .autoDismiss(false);
            dialog = msgConnecting.build();
        } else {
            msgConnecting.content(getResources().getString(R.string.laoding_error))
                    .typeface("Roboto-Light.ttf", "Roboto.ttf")
                    .theme(Theme.LIGHT)
                    .positiveText(getResources().getString(R.string.ressayer));
            dialog = msgConnecting.build();
            dialog.show();
        }
    }

    public void ShowDialogMaterialConnection() {
        MaterialDialog.Builder msgConnecting = new MaterialDialog.Builder(LoginActivity.this);

        msgConnecting.content(getResources().getString(R.string.laoding_error))
                .typeface("Roboto-Light.ttf", "Roboto.ttf")
                .theme(Theme.LIGHT)
                .positiveText(getResources().getString(R.string.ressayer));
        MaterialDialog dialog = msgConnecting.build();
        dialog.show();

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
                Log.i("HotixDev", String.valueOf(isConnected.isStatus()));
                Log.i("HotixDev", String.valueOf(isConnected.getData().size()));
                return isConnected;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(LoginModel greeting) {
            if (isConnected.isStatus() && (isConnected.getData().size() != 0)) {
                dialog.dismiss();
                UserInfoModel.getInstance().setRoom(chambre);
                UserInfoModel.getInstance().setUsers(isConnected);
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            } else {
                dialog.dismiss();
                ShowDialogMaterial(false);
            }
            ShowDialogMaterial(true);
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

