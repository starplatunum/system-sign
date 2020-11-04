package com.example.signsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.signsystem.bean.Professor;
import com.example.signsystem.bean.Student;
import com.example.signsystem.ui.StudentActivity;
import com.example.signsystem.ui.TeacherActivity;
import com.example.signsystem.utils.SPUtils;
import com.wega.library.loadingDialog.LoadingDialog;

import java.util.List;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIvTopLogo;
    private EditText mUname;
    private ImageView mIgvClear;
    private EditText mPword;
    private CheckBox mToggleShowPwd;
    private TextView mTvSelectedArea;
    private LinearLayout mLlSelectArea;
    private Button mSubmit;
    int yourChoice = 0;
    private LoadingDialog loadingDialog;

    public void showLodinDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);

        }
        loadingDialog.loading();
    }

    public void hideLodingDialog() {
        loadingDialog.cancel();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mIvTopLogo = (ImageView) findViewById(R.id.iv_top_logo);
        mUname = (EditText) findViewById(R.id.uname);
        mIgvClear = (ImageView) findViewById(R.id.igvClear);
        mPword = (EditText) findViewById(R.id.pword);
        mToggleShowPwd = (CheckBox) findViewById(R.id.toggle_show_pwd);
        mTvSelectedArea = (TextView) findViewById(R.id.tv_selected_area);
        mTvSelectedArea.setOnClickListener(this);
        mLlSelectArea = (LinearLayout) findViewById(R.id.ll_select_area);
        mSubmit = (Button) findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);
        mToggleShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mPword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mPword.setSelection(mPword.getText().toString().length());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_selected_area:
                final String[] items = {"学生", "教师"};
                AlertDialog.Builder singleChoiceDialog =
                        new AlertDialog.Builder(LoginActivity.this);
                singleChoiceDialog.setTitle("选择角色类型");
                singleChoiceDialog.setSingleChoiceItems(items, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                yourChoice = which;
                            }
                        });
                singleChoiceDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTvSelectedArea.setText(items[yourChoice]);

                            }
                        });
                singleChoiceDialog.show();
                break;
            case R.id.submit:
                if (yourChoice == 0) {
                    studentLogin();
                }
                if (yourChoice == 1) {
                    teacherLogin();
                }
                break;

        }
    }

    public void studentLogin() {
        showLodinDialog();
        String bql = "select * from Student where stuNum = ? and pwd = ?";
        new BmobQuery<Student>().doSQLQuery(bql, new SQLQueryListener<Student>() {

            @Override
            public void done(BmobQueryResult<Student> result, BmobException e) {
                hideLodingDialog();
                if (e == null) {
                    List<Student> list = (List<Student>) result.getResults();
                    if (list != null && list.size() > 0) {
                        SPUtils.put(LoginActivity.this, "login", true);
                        SPUtils.put(LoginActivity.this, "userType", "1");
                        SPUtils.setObject("user", list.get(0), LoginActivity.this);
                        startActivity(new Intent(LoginActivity.this, StudentActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名或密码失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }, mUname.getText().toString(), mPword.getText().toString());
    }

    public void teacherLogin() {
        showLodinDialog();
        String bql = "select * from Professor where pNum = ? and pwd = ?";
        new BmobQuery<Professor>().doSQLQuery(bql, new SQLQueryListener<Professor>() {

            @Override
            public void done(BmobQueryResult<Professor> result, BmobException e) {
                hideLodingDialog();
                if (e == null) {
                    List<Professor> list = (List<Professor>) result.getResults();
                    if (list != null && list.size() > 0) {
                        SPUtils.put(LoginActivity.this, "login", true);
                        SPUtils.setObject("teacher", list.get(0), LoginActivity.this);
                        startActivity(new Intent(LoginActivity.this, TeacherActivity.class));
                        SPUtils.put(LoginActivity.this, "userType", "2");
                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this, "用户名或密码失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }, mUname.getText().toString(), mPword.getText().toString());
    }



}