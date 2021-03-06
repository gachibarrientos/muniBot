package com.bradleybossard.androidprogramabdemo.Actividades;

/**
 * Created by Gachi on 26/4/2016.
 */
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import com.bradleybossard.androidprogramabdemo.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        showBasicSplash();
        }

    private void showBasicSplash() {

        Thread thread = new Thread() {

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                        startActivity(new Intent(getBaseContext(),
                                LoginActivity.class));
                        finish();
                    }
                }
            }
        };

        // start thread
        thread.start();

    }

}