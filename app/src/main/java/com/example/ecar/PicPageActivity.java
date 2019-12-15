package com.example.ecar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.Adapter.PicPageAdapter;
import com.example.Util.StatusBarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

public class PicPageActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG="PicPageActivity";
    private ViewPager2 viewPager2;
    private PicPageAdapter adapter;
    private List<String> photoList;
    private int total;
    private int current;
    private TextView count;//图片计数
    private TextView btn_delete;
    private ImageButton btn_back;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_view);
        StatusBarUtil.setStatusColor(this,false,true,R.color.white);
        photoList = getIntent().getStringArrayListExtra("photoList");
        total = getIntent().getIntExtra("total",0);//该方法中的 defaultValue 表示name对应的putExtra中没有传入有效的int类型值,就将defaultValue的值作为默认值传入
        current = getIntent().getIntExtra("current",0);
        initView();
    }

    private void initView(){
        count = findViewById(R.id.tv_count);
        btn_delete = findViewById(R.id.btn_delete);
        btn_back = findViewById(R.id.btn_back);
        viewPager2 = findViewById(R.id.viewpager2);
        btn_delete.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        //adapter = new PicPageAdapter(path);
        adapter = new PicPageAdapter(photoList);
        viewPager2.setAdapter(adapter);
        viewPager2.setCurrentItem(current,false);//设置当前显示页，禁用载入时平滑滚动
        refresh(current,total);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                current = position;//当前图片的位置改变，从0开始
                refresh(current,total);
            }
        });
    }

    private void refresh(int current,int total){
        String s = current+1 + "/" + total;//当前照片
        count.setText(s);
    }

    public static Intent getIntent(Context context, List<String> photoList,int total,int current){
        Intent intent = new Intent(context,PicPageActivity.class);
        //intent.putStringArrayListExtra("data", (ArrayList<String>) path);
        intent.putStringArrayListExtra("photoList", (ArrayList<String>) photoList);//传递图片数据
        intent.putExtra("total",total);//图片总数
        intent.putExtra("current",current);//当前点击的位置
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delete:
                adapter.removeItem(current);
                if(total - 1 <= 0){
                    setResult();
                    finish();
                }else {
                    total -= 1;
                    refresh(current,total);
                }
                break;
            case R.id.btn_back:
                    setResult();
                    finish();
                break;
            default:
        }
    }

    @Override
    public void onBackPressed() {
        setResult();
        super.onBackPressed();
    }

    private void setResult(){
        Intent intent = new Intent();
        photoList = adapter.getPhotoList();
        intent.putStringArrayListExtra("photoList", (ArrayList<String>) photoList);
        setResult(RESULT_OK,intent);
    }
}
