package com.example.signsystem.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signsystem.LoginActivity;
import com.example.signsystem.R;
import com.example.signsystem.bean.Student;
import com.example.signsystem.utils.DoubleClickHelper;
import com.example.signsystem.utils.SPUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class StudentActivity extends AppCompatActivity {

    private ImageView ivQrcode;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        initView();
    }


    public void initView() {
        Student student = SPUtils.getObject("user", Student.class, this);
        tvName = findViewById(R.id.tv_stu_name);
        tvName.setText("姓名：" + student.getName() + ",学号：" + student.getStuNum());
        ivQrcode = findViewById(R.id.iv_qrcode);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode("stuNum" + student.getStuNum(), 200);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivQrcode.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                SPUtils.put(StudentActivity.this, "login", false);
                finish();
                startActivity(new Intent(StudentActivity.this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            moveTaskToBack(false);
            System.exit(0);
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
    }


}