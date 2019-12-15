package com.example.ecar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.Util.StatusBarUtil;

import androidx.annotation.Nullable;

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener{

    private Button btn_refuse;
    private Button btn_accept;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        StatusBarUtil.setStatusBarLayoutStyle(this,true);
        btn_accept = findViewById(R.id.btn_accept);
        btn_refuse = findViewById(R.id.btn_refuse);
        btn_refuse.setOnClickListener(this);
        btn_accept.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_accept:
                startActivity(new Intent(this,ExecuteActivity.class));
                finish();
                break;
            case R.id.btn_refuse:
                startActivity(new Intent(this,OrderRefuseActivity.class));
                break;
        }
    }
}
