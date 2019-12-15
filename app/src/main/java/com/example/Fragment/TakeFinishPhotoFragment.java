package com.example.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.ecar.R;
import com.example.ecar.SignNameActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.app.Activity.RESULT_OK;

public class TakeFinishPhotoFragment extends TakePhotoFragment implements View.OnClickListener{
    private List<String> photoList = new ArrayList<>();//每一个拍照界面只有照片是不一样的
    private String taskName;//传递给签名说明自己
    private FragmentConfig config =new FragmentConfig(false,"添加救援完成照片",false,new RescuedFragment());//初始化top

    @Override
    public FragmentConfig getConfig() {
        return config;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//阻止输入法把按钮顶上去
        taskName = getResources().getString(R.string.task3);
        View view = inflater.inflate(R.layout.fragment_take_finish_photo,container,false);
        super.initView(view);
        super.setTextHint("请填写说明");
        initRV(photoList);
        return view;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: "+ requestCode + " " + resultCode + " " + data);
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    //将拍摄后的照片直接加入rv
//                  Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(getImageUri()));
                    picSave();
                }
                break;
            case SIGN_NAME:
                if(resultCode == RESULT_OK){
                    nextFragment();
                }
                break;
            case SHOW_PIC:
                if(resultCode == RESULT_OK){
                    if(data != null){
                        photoList = (List<String>) data.getSerializableExtra("photoList");
                        initRV(photoList);
                    }
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_submit:
                Intent intent = new Intent(getActivity(), SignNameActivity.class);
                intent.putExtra("taskName",taskName);
                startActivityForResult(intent,SIGN_NAME);
                break;
            default:
        }
    }


}
