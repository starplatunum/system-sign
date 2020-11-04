package com.example.signsystem.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.signsystem.R;
import com.example.signsystem.bean.Signin;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public final class ScanActivity extends AppCompatActivity implements QRCodeView.Delegate, View.OnClickListener, OnPermission {
    private static final String TAG = ScanActivity.class.getSimpleName();
    public static final int SELECT_PHOTO = 2;

    ZXingView mZXingView;
    ImageView mOpenCloseLight;
    private boolean openLight = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        initView();
    }


    protected void initView() {
        mZXingView = findViewById(R.id.zxingview);
        mOpenCloseLight = findViewById(R.id.iv_open_close_light);
        mOpenCloseLight.setOnClickListener(this);
        mZXingView.setDelegate(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermission();
        mZXingView.startCamera();

        mZXingView.startSpotAndShowRect();
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_open_close_light:
                if (openLight) {
                    mZXingView.closeFlashlight();
                    openLight = false;
                } else {
                    mZXingView.openFlashlight();
                    openLight = true;
                }
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        vibrate();
        sign(result);
    }

    public void sign(String url) {

        if (TextUtils.isEmpty(url) || !url.startsWith("stuNum")) {
            Toast.makeText(ScanActivity.this, "请扫描正确的学生二维码", Toast.LENGTH_SHORT).show();
            try {
                Thread.sleep(1500);
                mZXingView.startSpot();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        Signin signin = new Signin();
        signin.setClassNum("1");
        signin.setSigned("1");
        signin.setSingTime(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date()));
        signin.setStuNum(url.substring(6, url.length()));
        signin.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ScanActivity.this, url.substring(6, url.length()) + "签到成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mZXingView.startSpot();

                    }
                },2500);

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ScanActivity.this, "请求失败请重试", Toast.LENGTH_SHORT).show();

                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mZXingView.startSpot();

                        }
                    },2500);

                }
            }
        });
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    private void requestPermission() {
        XXPermissions.with(this)
                .permission(Permission.CAMERA)
                .request(this);
    }


    @Override
    public void hasPermission(List<String> granted, boolean isAll) {

    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}