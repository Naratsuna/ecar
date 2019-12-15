package com.example.ecar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.Fragment.HistoryFragment;
import com.example.Fragment.HomeFragment;
import com.example.Fragment.MeFragment;
import com.example.Util.StatusBarUtil;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setStatusBarLayoutStyle(this, true);
        replaceFragment(new HomeFragment());

        //处理底部TAB
        RadioGroup radioGroup = findViewById(R.id.radiogroup_bottom);
        RadioButton home = findViewById(R.id.home_rb);
        RadioButton navi = findViewById(R.id.navi_rb);
        RadioButton me = findViewById(R.id.me_rb);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent intent = new Intent();
                switch (checkedId){
                    case R.id.home_rb:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.navi_rb:
                        replaceFragment(new HistoryFragment());
                        break;
                    case R.id.me_rb:
                        replaceFragment(new MeFragment());
                        break;
                    default:
                }
                if(intent.resolveActivity(MainActivity.this.getPackageManager()) != null){
                   startActivity(intent);
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout,fragment);
        transaction.commit();
    }

}


