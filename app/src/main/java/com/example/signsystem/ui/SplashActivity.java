package com.example.signsystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.signsystem.LoginActivity;
import com.example.signsystem.R;
import com.example.signsystem.utils.SPUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean login = (boolean) SPUtils.get(SplashActivity.this, "login", false);
                String type = (String) SPUtils.get(SplashActivity.this, "userType", "");

                if (login) {
                    switch (type){
                        case "1"://学生
                            startActivity(new Intent(SplashActivity.this, StudentActivity.class));
                            break;
                        case "2"://老师
                            startActivity(new Intent(SplashActivity.this, TeacherActivity.class));
                            break;

                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }
        }, 2500);
    }
}