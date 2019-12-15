package com.example.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecar.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RescuedFragment extends BaseFragment implements View.OnClickListener{
    private Button btn_task_finish;
    private FragmentConfig config = new FragmentConfig(false,"救援完成",false,null);//初始化top

    @Override
    public FragmentConfig getConfig() {
        return config;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rescued,container,false);
        btn_task_finish = view.findViewById(R.id.btn_task_finish);
        btn_task_finish.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_task_finish:
                Toast.makeText(getActivity(),"已完成任务！辛苦了！",Toast.LENGTH_SHORT).show();
                getActivity().finish();
                break;
        }
    }
}
