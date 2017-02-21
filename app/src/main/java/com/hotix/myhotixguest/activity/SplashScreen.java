package com.hotix.myhotixguest.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.entities.UserInfoModel;
import com.hotix.myhotixguest.other.UpdateChecker;

public class SplashScreen extends AppCompatActivity {
    protected Animation fadeIn;
    protected ImageView logo;
    UpdateChecker checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        logo = (ImageView) findViewById(R.id.logoSplash);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);
        logo.setVisibility(View.VISIBLE);
        UserInfoModel.getInstance().setURL("http://192.168.0.196/HNGAPI/api/myhotixguest/");
        UserInfoModel.getInstance().setSERVER("http://192.168.0.196");
        checker = new UpdateChecker(this, false);
        if (!CheckVersionApp()) {
            android.support.v7.app.AlertDialog.Builder alertDialogBuilder2 = new android.support.v7.app.AlertDialog.Builder(this);
            Log.i("AUTO UPDATE", "OUTDATE : GET LAST VERSION");
            PackageManager manager = this.getPackageManager();
            alertDialogBuilder2.setTitle(getResources().getText(
                    R.string.app_name));
            alertDialogBuilder2.setMessage(getResources().getText(
                    R.string.last_version_no));
            alertDialogBuilder2.setIcon(R.mipmap.logo);

            alertDialogBuilder2.setCancelable(false);
            alertDialogBuilder2.setPositiveButton(getResources().getText(
                    R.string.update),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //ResetFields();
                            DownloadAndInstallLastVersion();
                        }
                    });


            alertDialogBuilder2.show();
        } else {
            Thread loading = new Thread() {
                public void run() {
                    try {
                        logo.startAnimation(fadeIn);
                        sleep(3000);
                        Intent main = new Intent(SplashScreen.this, LoginActivity.class);
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

    public boolean CheckVersionApp() {

        boolean lastVersion = true;
        if (isConnected()) {
            String serveur = UserInfoModel.getInstance().getSERVER();
            if (!serveur.equals("")) {
                checker.checkForUpdateByVersionCode(serveur + "/Android/MyHotixGuest.txt");

                if (checker.isUpdateAvailable()) {
                    Log.i("Update", "True");
                    lastVersion = false;

                }
            }
        } else {
            ShowDialogMaterialConnection();
        }
        return lastVersion;
    }

    public void ShowDialogMaterialConnection() {
        MaterialDialog.Builder msgConnecting = new MaterialDialog.Builder(SplashScreen.this);

        msgConnecting.content(getResources().getString(R.string.laoding_error))

                .theme(Theme.LIGHT)
                .positiveText(getResources().getString(R.string.ressayer));
        MaterialDialog dialog = msgConnecting.build();
        dialog.show();

    }

    public void DownloadAndInstallLastVersion() {
        String serveur = UserInfoModel.getInstance().getSERVER();
        if (!serveur.equals("")) {
            checker.downloadAndInstall(serveur + "/Android/MyHotixGuest.apk");
        }
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

}
