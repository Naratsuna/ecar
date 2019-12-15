package com.example.ecar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Util.SignatureView;
import com.example.Util.StatusBarUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//implements cancelAdapt 不适配今日头条方案避免影响
public class SignNameActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG="SignNameActivity";

    private TextView mTipView1,mTipView2;
    private SignatureView mSignView;
    private Button btn_redo;
    private Button btn_right;
    private Button btn_exit;
    private boolean haveSign = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusColor(this,true,true,R.color.white);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_sign);
        initView();
        changeTitle();
    }

    private void initView( ) {
        mTipView1=findViewById(R.id.tv_sign_tip1);
        mTipView2=findViewById(R.id.tv_sign_tip2);
        mSignView = findViewById(R.id.sv_sign_view);
        btn_redo=findViewById(R.id.btn_redo);
        btn_right=findViewById(R.id.btn_right);
        btn_exit=findViewById(R.id.btn_exit);

        btn_redo.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        btn_exit.setOnClickListener(this);

        //开始签名的时候隐藏提示
        mSignView.setOnMyMoveListener(new SignatureView.OnMyMoveListener() {
            @Override
            public void onMyMove() {
                haveSign = true;
                mTipView1.setVisibility(View.GONE);
                mTipView2.setVisibility(View.GONE);
                // Log.i("wtt", haveSign+"");
            }
        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_redo:
                clearClick();
                break;
            case R.id.btn_right:
                sureClick();
                String path = "";
                Intent intent = new Intent();
                intent.putExtra("图片路径名",path);
                setResult(RESULT_OK,intent);
                Log.d(TAG, "onClick: ");
                finish();
                break;
            case R.id.btn_exit:
                finish();
                break;
        }
    }



    /**
     * 清除签字
     */
    private void clearClick() {
        mSignView.clear();
        haveSign = false;
        mTipView1.setVisibility(View.GONE);
        mTipView2.setVisibility(View.GONE);
        //Toast.makeText(getActivity(), "重置成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 保存签名图片并展示
     */
    private void sureClick() {
        if (!haveSign) {
            Toast.makeText(SignNameActivity.this, "没有输入任何内容", Toast.LENGTH_SHORT).show();
            return;
        }
        //  保存签名图片
        Bitmap imageBitmap = mSignView.getCachebBitmap();
        String path = saveFile(imageBitmap);
        //Log.i(TAG, "sureClick:" + path);
        Toast.makeText(SignNameActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        clearClick();
    }

    /**
     * 创建手写签名文件
     *
     * @return
     */
    private String saveFile(Bitmap bitmap) {

        ByteArrayOutputStream baos = null;
        String _path = null;
        try {
            String name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            name = "signName" + name + ".jpg";
            File file = new File(getExternalFilesDir("").getAbsolutePath());
            if(!file.exists()){
                file.mkdir();
            }
            Log.d(TAG, "saveFile: "+file.getPath());
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] photoBytes = baos.toByteArray();
            if (photoBytes != null) {
                new FileOutputStream(new File(file.getPath(),name)).write(photoBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return _path;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void changeTitle(){
        Intent intent = getIntent();
        String taskName = intent.getStringExtra("taskName");
        if(taskName.equals(getResources().getString(R.string.task1))){
            mTipView1.setText(getResources().getString(R.string.tips1));
        }else if(taskName.equals(getResources().getString(R.string.task2))){
            mTipView1.setText(getResources().getString(R.string.tips2));
        }else if(taskName.equals(getResources().getString(R.string.task3))){
            mTipView1.setText(getResources().getString(R.string.tips3));
        }
    }
}