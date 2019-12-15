package com.example.ecar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.Adapter.StepButtonAdapter;
import com.example.Entity.StepButton;
import com.example.Fragment.BaseFragment;
import com.example.Fragment.MapFragment;
import com.example.Util.StatusBarUtil;
import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExecuteActivity extends BaseActivity {

    private static final String TAG = "ExecuteActivity";
    private final static int STEPSNUM = 8;//一共有几个流程
    private int currentNum = 1;//当前位置
    private int lastNum = 1;//记录最远走到哪里
    private BaseFragment mfragment;//当前fragment

    private List<StepButton> mStepButtons  = new ArrayList<>();//初始化rv
    private List<BaseFragment.FragmentConfig> configs = new ArrayList<>();//初始化top
    private List<Fragment> fragments = new ArrayList<>();//记录，用于按钮点击时的切换
    private StepButtonAdapter adapter;
    private int position;

    private RecyclerView rv;//步骤的rv
    private ImageButton btn_back;
    private TextView btn_skip;
    private TextView tv_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute);
        StatusBarUtil.setStatusBarLayoutStyle(this, true);
        initView();
        mfragment = new MapFragment();
        configs.add(mfragment.getConfig());//当前碎片配置
        fragments.add(mfragment);//当前碎片
        fragments.add(mfragment.getConfig().getNextFragment());//下一碎片
        replaceFragment(mfragment);
    }

    /*
     *    处理下一个碎片载入的事件
     */
    public void nextFragment(){
        //Log.d(TAG, "nextFragment: " + mfragment);
        mfragment = (BaseFragment)fragments.get(currentNum);//从0开始的，获取到下一碎片
        fragments.add(mfragment.getConfig().getNextFragment());//第二次开始只需要加入下一碎片
        //Log.d(TAG, "nextFragment: " + mfragment + currentNum + mfragment.getConfig());
        currentNum += 1;
        lastNum += 1;
        initTop();
        initSteps(currentNum);
        replaceFragment(mfragment);

    }

    private void initTop(){
        configs.add(mfragment.getConfig());//当前下一碎片配置
        BaseFragment.FragmentConfig config = configs.get(currentNum-1);//配置
        tv_title.setText(config.getTitle());
        btn_back.setVisibility(config.isHasBack());
        btn_skip.setVisibility(config.isHasSkip());
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout_execute,fragment);
        //transaction.add(R.id.fragment_layout_execute,new TollsFragment(),"TollsFragment");//在碎片活动管理器里添加tag标签
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initView(){
        rv = findViewById(R.id.gotask_rv);
        btn_back = findViewById(R.id.btn_back);
        tv_title = findViewById(R.id.textview_title);
        btn_skip = findViewById(R.id.btn_skip);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment();//2
            }
        });
        initSteps(currentNum);
    }

    /**
     * 初始化按钮，通过设置不透明度设置样式，之后的步骤变化通过动态设置透明度实现
     */
    private void initSteps(int position){
        mStepButtons.clear();
        StepButton stepButton;
        for(int i = 1; i <= position; i++){
            stepButton = new StepButton(String.valueOf(i),true,true);
            mStepButtons.add(stepButton);
        }
        for(int j = position + 1; j <= STEPSNUM; j++){
            stepButton = new StepButton(String.valueOf(j),false,false);
            if(j <= lastNum){
                stepButton.setEnable(true);
            }
            mStepButtons.add(stepButton);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);
        adapter = new StepButtonAdapter(mStepButtons);
        adapter.setOnButtonClickListener(onButtonClickListener);
        rv.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if(currentNum != 1){
            super.onBackPressed();
            Log.e(TAG, "onBackPressed: 按下了返回键");
            if(currentNum-1 >=1){
                currentNum -= 1;
            }
        }else{
            finish();
        }
        initTop();
        initSteps(currentNum);
    }

    StepButtonAdapter.onButtonClickListener onButtonClickListener = new StepButtonAdapter.onButtonClickListener() {
        @Override
        public void clickListener(int position) {
            currentNum = position + 1;
            mfragment = (BaseFragment) fragments.get(position);
            initSteps(position+1);//0开始
            initTop();
            replaceFragment(mfragment);//0开始
            Log.d(TAG, "clickListener: "+mfragment);
        }
    };
}
