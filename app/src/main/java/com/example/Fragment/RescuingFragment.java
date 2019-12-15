package com.example.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ecar.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RescuingFragment extends BaseFragment implements View.OnClickListener{
    private Button btn_rescue_finish;
    private FragmentConfig config = new FragmentConfig(false,"正在救援",false,new TakeFinishPhotoFragment());//初始化top

    @Override
    public FragmentConfig getConfig() {
        return config;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rescuing,container,false);
        btn_rescue_finish = view.findViewById(R.id.btn_rescue_finish);
        btn_rescue_finish.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rescue_finish:
                nextFragment();
                break;
        }
    }
}
