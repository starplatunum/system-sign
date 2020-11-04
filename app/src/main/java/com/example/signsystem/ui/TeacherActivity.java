package com.example.signsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.signsystem.LoginActivity;
import com.example.signsystem.R;
import com.example.signsystem.bean.Professor;
import com.example.signsystem.utils.DoubleClickHelper;
import com.example.signsystem.utils.SPUtils;

public class TeacherActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        initView();
    }

    private void initView() {
        TextView tvName = findViewById(R.id.tv_name);
        Professor teacher = SPUtils.getObject("teacher", Professor.class, TeacherActivity.this);
        if (teacher!=null&&teacher.getName()!=null){
            tvName.setText("教师："+teacher.getName());

        }
        findViewById(R.id.sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherActivity.this, ScanActivity.class));
            }
        });

    }

    //这里是在登录界面label上右上角添加三个点，里面可添加其他功能
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//这里是调用menu文件夹中的main.xml，在登陆界面label右上角的三角里显示其他功能
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                SPUtils.put(TeacherActivity.this, "login", false);
                startActivity(new Intent(TeacherActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            // 移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            System.exit(0);
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
    }


}