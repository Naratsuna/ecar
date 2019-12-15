package com.example.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecar.ExecuteActivity;
import com.example.ecar.R;
import com.example.ecar.SignNameActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.app.Activity.RESULT_OK;

public class PaymentFragment extends BaseFragment implements View.OnClickListener{
    private String taskName;
    private TextView tv_money;//支付金额
    private Button btn_finish;
    private Button btn_to_zfb;
    private Button btn_to_wx;
    private View view_zfb;//根据这个来切换界面
    private View view_wx;//根据这个来切换界面
    private FragmentConfig config = new FragmentConfig(false,"费用支付",false,new RescuingFragment());//初始化top

    @Override
    public FragmentConfig getConfig() {
        return config;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_pay,container,false);
        taskName = getResources().getString(R.string.task2);//传递给签名说明自己;
        tv_money = view.findViewById(R.id.pay_money);
        btn_finish = view.findViewById(R.id.btn_pay_finish);
        btn_to_wx = view.findViewById(R.id.btn_to_wx);
        btn_to_zfb = view.findViewById(R.id.btn_to_zfb);
        view_zfb = view.findViewById(R.id.view_zfb);
        view_wx = view.findViewById(R.id.view_wx);
        btn_finish.setOnClickListener(this);
        btn_to_zfb.setOnClickListener(this);
        btn_to_wx.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_to_zfb:
            case R.id.btn_to_wx:
                toggleView();
                break;
            case R.id.btn_pay_finish:
                Intent intent = new Intent(getActivity(), SignNameActivity.class);
                intent.putExtra("taskName",taskName);
                startActivityForResult(intent,SIGN_NAME);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case SIGN_NAME:
                if(resultCode == RESULT_OK){
                    nextFragment();
                }
                break;
            default:
        }
    }

    private void toggleView(){
        if(view_zfb.getVisibility() == View.VISIBLE){
            view_zfb.setVisibility(View.INVISIBLE);
            btn_to_wx.setVisibility(View.INVISIBLE);
            view_wx.setVisibility(View.VISIBLE);
            btn_to_zfb.setVisibility(View.VISIBLE);
        }else{
            view_zfb.setVisibility(View.VISIBLE);
            btn_to_wx.setVisibility(View.VISIBLE);
            view_wx.setVisibility(View.INVISIBLE);
            btn_to_zfb.setVisibility(View.INVISIBLE);
        }
    }
}
