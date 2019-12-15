package com.example.ecar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Util.StatusBarUtil;

import androidx.annotation.Nullable;

public class OrderRefuseActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_back;
    private Button btn_submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_refuse);
        StatusBarUtil.setStatusBarLayoutStyle(this,true);
        btn_submit = findViewById(R.id.btn_submit);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(this,"已提交",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
